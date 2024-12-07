package com.juandlr.springsecurityproject.service.implementation;

import com.juandlr.springsecurityproject.dto.LoginRequestDto;
import com.juandlr.springsecurityproject.dto.SignUpRequestDto;
import com.juandlr.springsecurityproject.dto.UpdatePasswordRequestDto;
import com.juandlr.springsecurityproject.dto.UserDto;
import com.juandlr.springsecurityproject.entity.ApplicationUser;
import com.juandlr.springsecurityproject.exception.UserAlreadyExistsException;
import com.juandlr.springsecurityproject.exception.UserNameIsUniqueException;
import com.juandlr.springsecurityproject.exception.UserNotFoundException;
import com.juandlr.springsecurityproject.mapper.SignUpMapper;
import com.juandlr.springsecurityproject.mapper.UserMapper;
import com.juandlr.springsecurityproject.repository.ApplicationUserRepository;
import com.juandlr.springsecurityproject.service.ApplicationUserService;
import com.juandlr.springsecurityproject.service.RoleService;
import com.juandlr.springsecurityproject.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationUserServiceImpl implements ApplicationUserService {

    private final ApplicationUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    @Transactional
    public void signUpUser(SignUpRequestDto signUpRequestDto) {
        validateUserUniqueness(signUpRequestDto.email(), signUpRequestDto.userName());
        String hashedPassword = passwordEncoder.encode(signUpRequestDto.password());
        ApplicationUser userToPersist = SignUpMapper.mapToApplicationUser(new ApplicationUser(), signUpRequestDto, hashedPassword);
        userToPersist.setRole(roleService.generateDefaultRoleName("ROLE_USER"));
        userRepository.save(userToPersist);
    }

    private void validateUserUniqueness(String email, String userName) {
        if (userRepository.existsByUserName(userName)) {
            throw new UserAlreadyExistsException("The username must be unique and a user with the given username already exists, please verify your information, thank you.");
        }

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("The email must be unique and a user with the given email already exists, please verify your information, thank you.");
        }
    }

    @Override
    public String loginUser(LoginRequestDto loginRequestDto) {
        Authentication authenticationResponse = authenticateUser(loginRequestDto.userName(), loginRequestDto.password());
        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
        return jwtService.jwtTokenGenerator(authenticationResponse);
    }

    private Authentication authenticateUser(String userName, String password) {
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(userName, password);
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);
        if (!authenticationResponse.isAuthenticated()) {
            throw new BadCredentialsException("Invalid credentials");
        }
        return authenticationResponse;
    }

    @Override
    public UserDto fetchUserInformation(String userName) {
        ApplicationUser userFromDB = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("The user with username: " + userName + " does not exist, please verify your information, thank you."));
        return UserMapper.mapToUserDto(new UserDto(), userFromDB);
    }

    @Override
    public void deleteUser(String userName) {
        ApplicationUser userFromDb = userRepository.findByUserName(userName)
               .orElseThrow(()-> new UserNotFoundException("The user with username: " + userName + " does not exist, please verify your information, thank you."));
        userRepository.delete(userFromDb);
    }

    @Override
    public void updateUser(UserDto userDto) {
        ApplicationUser userFromDb = userRepository.findByUserName(userDto.getUserName())
                .orElseThrow(()-> new UserNameIsUniqueException("The username must be unique and cannot be changed. The remaining fields can be updated. Thank you."));
        ApplicationUser updatedUser = UserMapper.mapToUser(userDto, userFromDb);
        userRepository.save(updatedUser);
    }

    @Override
    public void updatePassword(UpdatePasswordRequestDto updatePasswordRequestDto) {
        ApplicationUser userFromDb = userRepository.findByUserName(updatePasswordRequestDto.getUserName())
                .orElseThrow(() -> new UserNameIsUniqueException("The username must be unique and cannot be changed. Only the password can be changed, please try again, thank you."));
        userFromDb.setPassword(passwordEncoder.encode(updatePasswordRequestDto.getNewPassword()));
        userRepository.save(userFromDb);
    }
}
