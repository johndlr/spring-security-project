package com.juandlr.springsecurityproject.service;

import com.juandlr.springsecurityproject.entity.ApplicationUser;
import com.juandlr.springsecurityproject.entity.Role;
import com.juandlr.springsecurityproject.repository.RoleRepository;
import com.juandlr.springsecurityproject.service.implementation.RoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl underTest;

    @Test
    void shouldReturnRoleWhenCallGenerateDefaultRoleName(){
        Role testRole = new Role(1L, "ROLE_USER");
        Optional<Role> roleOptional = Optional.of(testRole);
        when(roleRepository.findByRoleName("ROLE_USER")).thenReturn(roleOptional);
        Role responseRole = underTest.generateDefaultRoleName("ROLE_USER");
        assertThat(responseRole).isNotNull();
        assertThat(responseRole.getRoleId()).isEqualTo(1L);
        assertThat(responseRole.getRoleId()).isEqualTo(testRole.getRoleId());
        assertThat(responseRole.getRoleName()).isEqualTo(testRole.getRoleName());

    }

    @Test
    void shouldThrowRoleNameNotFoundExceptionWhenGenerateDefaultRoleNameFail(){
        Role testRole = new Role(1L, "ROLE_USER");
        Optional<Role> roleOptional = Optional.of(testRole);
        when(roleRepository.findByRoleName("ROLE_USER")).thenReturn(roleOptional);
        Role responseRole = underTest.generateDefaultRoleName("ROLE_USER");
        assertThat(responseRole).isNotNull();
        assertThat(responseRole.getRoleId()).isEqualTo(1L);
        assertThat(responseRole.getRoleId()).isEqualTo(testRole.getRoleId());
        assertThat(responseRole.getRoleName()).isEqualTo(testRole.getRoleName());

    }


}
