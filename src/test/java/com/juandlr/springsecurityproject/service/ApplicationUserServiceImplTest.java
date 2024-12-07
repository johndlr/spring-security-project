package com.juandlr.springsecurityproject.service;

import com.juandlr.springsecurityproject.dto.LoginRequestDto;
import com.juandlr.springsecurityproject.dto.SignUpRequestDto;
import com.juandlr.springsecurityproject.dto.UserDto;
import com.juandlr.springsecurityproject.entity.ApplicationUser;
import com.juandlr.springsecurityproject.entity.Role;
import com.juandlr.springsecurityproject.exception.UserAlreadyExistsException;
import com.juandlr.springsecurityproject.exception.UserNotFoundException;
import com.juandlr.springsecurityproject.repository.ApplicationUserRepository;
import com.juandlr.springsecurityproject.service.implementation.ApplicationUserServiceImpl;
import com.juandlr.springsecurityproject.service.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationUserServiceImplTest {

    @Mock
    private ApplicationUserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private ApplicationUserServiceImpl underTest;

    private ApplicationUser user;

    private Optional<ApplicationUser> userOptional;

    private Role role;

    private SignUpRequestDto signUpRequestDto;

    private LoginRequestDto loginRequestDto;

    private Authentication authentication, authenticatedResponse ;

    @BeforeEach
    void setUp() {
        role = new Role(1L, "ROLE_USER");
        user = new ApplicationUser(10L, "martinADD", "Martin", "Addams", "maddams@example.com", "password12345", role);
        userOptional = Optional.of(user);
        signUpRequestDto = new SignUpRequestDto("martinADD","Martin", "Addams", "addams45@example.com", "martin@123456");
        loginRequestDto = new LoginRequestDto("martinADD", "martin@123456");
        authentication = UsernamePasswordAuthenticationToken.unauthenticated(
                loginRequestDto.userName(),
                loginRequestDto.password()
        );
        authenticatedResponse = new UsernamePasswordAuthenticationToken(
                loginRequestDto.userName(),
                loginRequestDto.password(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Test
    void shouldReturnUserDtoWhenfetchUserInformation(){
        when(userRepository.findByUserName(user.getUserName())).thenReturn(userOptional);
        UserDto userDto = underTest.fetchUserInformation(user.getUserName());
        assertThat(userDto).isNotNull();
        assertThat(userDto.getUserName()).isEqualTo(user.getUserName());
        assertThat(userDto.getName()).isEqualTo(user.getName());
        assertThat(userDto.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void shouldDeleteUserWhenUserExists(){
        when(userRepository.findByUserName(user.getUserName())).thenReturn(userOptional);
        underTest.deleteUser(user.getUserName());
        verify(userRepository).delete(user);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserDoesNotExistInDeleteUser() {
        String unknownUserName = "Unknown";
        when(userRepository.findByUserName(unknownUserName)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> underTest.deleteUser(unknownUserName));
        verify(userRepository).findByUserName(unknownUserName);
        verify(userRepository, never()).delete(any());
    }

    @Test
    void shouldUpdateUserWhenUserExists(){
        UserDto userDto = new UserDto("martinADD", "Martin", "Addams", "addams45@example.com");
        when(userRepository.findByUserName(userDto.getUserName())).thenReturn(userOptional);
        underTest.updateUser(userDto);
        verify(userRepository).findByUserName(userDto.getUserName());
        ArgumentCaptor<ApplicationUser> userCaptor = ArgumentCaptor.forClass(ApplicationUser.class);
        verify(userRepository).save(userCaptor.capture());
        ApplicationUser updatedUser = userCaptor.getValue();
        assertThat(updatedUser.getEmail()).isEqualTo("addams45@example.com");
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserDoesNotExistInUpdateUser() {
        UserDto userDto = new UserDto("Unknown", "Unknown", "Unknown", "Unknown@example.com");
        when(userRepository.findByUserName(userDto.getUserName())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> underTest.updateUser(userDto), "Expected updateUser to throw, but it didn't.");
        verify(userRepository).findByUserName(userDto.getUserName());
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldSignUpUser(){
        when(userRepository.findByUserName(signUpRequestDto.userName())).thenReturn(Optional.empty());
        when(roleService.generateDefaultRoleName("ROLE_USER")).thenReturn(role);
        when(passwordEncoder.encode(signUpRequestDto.password())).thenReturn("{bcrypt}$2a$10$oz1yp4FCF/9JvnDRR7Be2uyjN5EsSUxdrBpmSBIipAcvu57HVohby");
        underTest.signUpUser(signUpRequestDto);
        verify(userRepository).findByUserName(signUpRequestDto.userName());
        verify(roleService).generateDefaultRoleName("ROLE_USER");
        ArgumentCaptor<ApplicationUser> userCaptor = ArgumentCaptor.forClass(ApplicationUser.class);
        verify(userRepository).save(userCaptor.capture());
        ApplicationUser savedUser = userCaptor.getValue();
        assertThat(savedUser.getUserName()).isEqualTo(signUpRequestDto.userName());
        assertThat(savedUser.getName()).isEqualTo(signUpRequestDto.name());
        assertThat(savedUser.getLastName()).isEqualTo(signUpRequestDto.lastName());
        assertThat(savedUser.getRole()).isEqualTo(role);
        assertThat(savedUser.getRole().getRoleName()).isEqualTo("ROLE_USER");
        assertThat(savedUser.getRole().getRoleId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowUserAlreadyExistsExceptionWhenUserExistInSignUpUser(){
        when(userRepository.findByUserName(signUpRequestDto.userName())).thenReturn(Optional.of(user));
        assertThrows(UserAlreadyExistsException.class, () -> underTest.signUpUser(signUpRequestDto));
        verify(userRepository).findByUserName(signUpRequestDto.userName());
        verify(passwordEncoder, never()).encode(signUpRequestDto.password());
    }

    @Test
    void shouldReturnJwtWhenLoginIsSuccessful(){
        when(authenticationManager.authenticate(authentication)).thenReturn(authenticatedResponse);
        when(jwtService.jwtTokenGenerator(authenticatedResponse)).thenReturn("mockJwtToken");
        String jwtToken = underTest.loginUser(loginRequestDto);
        verify(authenticationManager).authenticate(authentication);
        verify(jwtService).jwtTokenGenerator(authenticatedResponse);
        assertThat("mockJwtToken").isEqualTo(jwtToken);
    }

    @Test
    void shouldThrowBadCredentialsExceptionWhenLoginFails() {
        when(authenticationManager.authenticate(any(Authentication.class))).thenThrow(new BadCredentialsException("Invalid credentials"));
        assertThrows(BadCredentialsException.class, () -> underTest.loginUser(loginRequestDto));
        verify(authenticationManager).authenticate(any(Authentication.class));
        verify(jwtService, never()).jwtTokenGenerator(any(Authentication.class));
    }





}
