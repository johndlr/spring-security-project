package com.juandlr.springsecurityproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juandlr.springsecurityproject.constants.HttpConstants;
import com.juandlr.springsecurityproject.dto.*;
import com.juandlr.springsecurityproject.service.ApplicationUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("/application.yml")
@AutoConfigureMockMvc
public class AuthControllerTest {

    @MockBean
    private ApplicationUserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    private SignUpRequestDto signUpRequestDto;

    private LoginRequestDto loginRequestDto;

    @BeforeEach
    void setUp() {
        signUpRequestDto = new SignUpRequestDto("contoso21", "David", "Contoso", "d_contoso@example.com", "azure1234");
        loginRequestDto = new LoginRequestDto("contoso21", "azure1234");
    }

    @Test
    void shouldReturnResponseDtoWhenSignUpUser() throws Exception {
        doNothing().when(userService).signUpUser(signUpRequestDto);
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(new ResponseDto(HttpConstants.STATUS_201, HttpConstants.MESSAGE_201))));
    }

    @Test
    void shouldReturnLoginResponseDtoWhenUserLogin() throws Exception {
        when(userService.loginUser(loginRequestDto)).thenReturn("mockJWT");
        mockMvc.perform(post("/api/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new LoginResponseDto("mockJWT"))));
    }

}
