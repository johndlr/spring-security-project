package com.juandlr.springsecurityproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juandlr.springsecurityproject.dto.UserDto;
import com.juandlr.springsecurityproject.service.ApplicationUserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApplicationUserService applicationUserService;

    private ObjectMapper objectMapper;

    private String validUsername;

    private UserDto userDto;

    @BeforeEach
    public void setUp(){
        validUsername = "tonystark";
        objectMapper = new ObjectMapper();
        userDto = new UserDto("tonystark", "Tony", "Stark", "tony@example.com");
    }

    @Test
    public void testFetchContact() throws Exception {

        when(applicationUserService.fetchUserInformation(any(String.class))).thenReturn(userDto);
        mockMvc.perform(get("/api/user").with(user("tonystark").password("pass").roles("USER")).param("userName",validUsername))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userName").value("tonystark"))
                .andExpect(jsonPath("$.name").value("Tony"))
                .andExpect(jsonPath("$.lastName").value("Stark"))
                .andExpect(jsonPath("$.email").value("tony@example.com"));
    }

}
