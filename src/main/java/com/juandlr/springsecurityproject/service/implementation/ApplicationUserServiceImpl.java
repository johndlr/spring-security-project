package com.juandlr.springsecurityproject.service.implementation;

import com.juandlr.springsecurityproject.dto.LoginRequestDto;
import com.juandlr.springsecurityproject.dto.SignUpRequestDto;
import com.juandlr.springsecurityproject.dto.UserDto;
import com.juandlr.springsecurityproject.entity.ApplicationUser;
import com.juandlr.springsecurityproject.exception.UserAlreadyExistsException;
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
    public void signUpUser(SignUpRequestDto signUpRequestDto) {
        Optional<ApplicationUser> optionalApplicationUser = userRepository.findByUserName(signUpRequestDto.userName());
        if (optionalApplicationUser.isPresent()){
            throw new UserAlreadyExistsException("User with the given username " + signUpRequestDto.userName() + " already exists");
        }
        String hashedPassword = passwordEncoder.encode(signUpRequestDto.password());
        ApplicationUser userToPersist= SignUpMapper.mapToApplicationUser(new ApplicationUser(), signUpRequestDto, hashedPassword);
        userToPersist.setRole(roleService.generateDefaultRoleName("ROLE_USER"));
        userRepository.save(userToPersist);
    }

    @Override
    public String loginUser(LoginRequestDto loginRequestDto) {
        Authentication authentication = UsernamePasswordAuthenticationToken
                .unauthenticated(loginRequestDto.userName(), loginRequestDto.password());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);
        if (!(authenticationResponse.isAuthenticated())){
            throw new BadCredentialsException("Invalid credentials");
        }
        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
        return jwtService.jwtTokenGenerator(authenticationResponse);
    }

    @Override
    public UserDto fetchUserInformation(String userName) {
        ApplicationUser userFromDB = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("The user with the given username: " + userName + " does not exists"));
        return UserMapper.mapToUserDto(new UserDto(), userFromDB);
    }

    @Override
    public Set<String> generateCodes() {
        Set<String> setOfCodes = new HashSet<>();
        int i = 0;
        try {
            while (i < 5){
                String organizationCode = "Aur" + UUID.randomUUID().toString().substring(0,3).toUpperCase();
                setOfCodes.add(organizationCode);
                i++;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return setOfCodes;
    }

    @Override
    public void deleteUser(String userName) {
        ApplicationUser userFromDb = userRepository.findByUserName(userName)
               .orElseThrow(()-> new UserNotFoundException("User with the given userName does not exists"));
        userRepository.delete(userFromDb);
    }

    @Override
    public void updateUser(UserDto userDto) {
        ApplicationUser userFromDb = userRepository.findByUserName(userDto.getUserName())
                .orElseThrow(()-> new UserNotFoundException("User with the given username does not exists"));
        ApplicationUser updatedUser = UserMapper.mapToUser(userDto, userFromDb);
        userRepository.save(updatedUser);
    }


}
