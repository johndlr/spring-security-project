package com.juandlr.springsecurityproject.repository;

import com.juandlr.springsecurityproject.entity.ApplicationUser;
import com.juandlr.springsecurityproject.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ApplicationUserRepositoryTest {

    @Autowired
    ApplicationUserRepository underTest;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void shouldReturnUserWhenfindByUserName(){
        String userName = "Space Cadet";
        String password = passwordEncoder.encode("martin@123456");
        Role role = new Role(1L, "ROLE_USER");
        ApplicationUser user = new ApplicationUser(10L, userName, "Martin", "Addams", "maddams@example.com", password, role);
        underTest.save(user);
        Optional<ApplicationUser> userByUserName = underTest.findByUserName(userName);
        assertThat(userByUserName).isPresent();
    }
}
