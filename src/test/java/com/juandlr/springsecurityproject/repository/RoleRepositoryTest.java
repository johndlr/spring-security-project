package com.juandlr.springsecurityproject.repository;

import com.juandlr.springsecurityproject.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("/application.yml")
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository underTest;

    @Test
    void shouldReturnRoleWhenFindByRoleName(){
        Role userRole = new Role(1L, "ROLE_USER");
        Role adminRole = new Role(2L, "ROLE_ADMIN");
        Optional<Role> optionalForUserRole = underTest.findByRoleName("ROLE_USER");
        Optional<Role> optionalForAdminRole = underTest.findByRoleName("ROLE_ADMIN");
        assertThat(optionalForUserRole).isPresent();
        assertThat(optionalForAdminRole).isPresent();
        assertThat(optionalForUserRole.get().getRoleName()).isEqualTo(userRole.getRoleName());
        assertThat(optionalForAdminRole.get().getRoleName()).isEqualTo(adminRole.getRoleName());
    }
}
