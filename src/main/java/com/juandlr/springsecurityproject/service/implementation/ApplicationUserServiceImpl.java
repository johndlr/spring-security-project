package com.juandlr.springsecurityproject.service.implementation;

import com.juandlr.springsecurityproject.dto.SignUpRequestDto;
import com.juandlr.springsecurityproject.entity.ApplicationUser;
import com.juandlr.springsecurityproject.exception.UserAlreadyExistsException;
import com.juandlr.springsecurityproject.mapper.SingUpMapper;
import com.juandlr.springsecurityproject.repository.ApplicationUserRepository;
import com.juandlr.springsecurityproject.service.ApplicationUserService;
import com.juandlr.springsecurityproject.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ApplicationUserServiceImpl implements ApplicationUserService {

    private final ApplicationUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    @Override
    public void singUpUser(SignUpRequestDto signUpRequestDto) {
        String hashedPassword = passwordEncoder.encode(signUpRequestDto.password());
        Optional<ApplicationUser> optionalApplicationUser = userRepository.findByUserName(signUpRequestDto.userName());
        if (optionalApplicationUser.isPresent()){
            throw new UserAlreadyExistsException("User with the given username " + signUpRequestDto.userName() + " already exists");
        }
        ApplicationUser userToPersist= SingUpMapper.mapToApplicationUser(new ApplicationUser(), signUpRequestDto, hashedPassword);
        userToPersist.setRole(roleService.generateDefaultRoleName("ROLE_USER"));
        userRepository.save(userToPersist);
    }
}
