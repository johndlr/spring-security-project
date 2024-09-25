package com.juandlr.springsecurityproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juandlr.springsecurityproject.constants.HttpConstants;
import com.juandlr.springsecurityproject.dto.LoginRequestDto;
import com.juandlr.springsecurityproject.dto.LoginResponseDto;
import com.juandlr.springsecurityproject.dto.SignUpRequestDto;
import com.juandlr.springsecurityproject.service.ApplicationUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApplicationUserService applicationUserService;

    private ObjectMapper objectMapper;

    private SignUpRequestDto signUpRequestDto;
    private SignUpRequestDto signUpRequestDtoForTestValidations;
    private LoginRequestDto loginRequestDto;
    private LoginResponseDto loginResponseDto;

    @BeforeEach
    public void setUp(){
        signUpRequestDto = new SignUpRequestDto("dopodopo","John", "Doe", "johh@example.com", "12345678");
        signUpRequestDtoForTestValidations = new SignUpRequestDto("dopo","John", "Doe", "johh@example.com", "12345678");
        loginResponseDto = new LoginResponseDto("jwt-test");
        loginRequestDto = new LoginRequestDto("dopodopo", "12345678");
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testSignUpUser() throws Exception{
        doNothing().when(applicationUserService).singUpUser(any(SignUpRequestDto.class));
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(HttpConstants.STATUS_201))
                .andExpect(jsonPath("$.statusMsg").value(HttpConstants.MESSAGE_201));
    }

    @Test
    public void testSignupUserErrorUserNameValidation() throws Exception{
        doNothing().when(applicationUserService).singUpUser(any(SignUpRequestDto.class));
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequestDtoForTestValidations)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLoginUser() throws Exception{
        when(applicationUserService.loginUser(any(LoginRequestDto.class))).thenReturn("jwt-test");
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").value("jwt-test"));

    }




}
