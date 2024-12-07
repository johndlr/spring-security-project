package com.juandlr.springsecurityproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juandlr.springsecurityproject.constants.HttpConstants;
import com.juandlr.springsecurityproject.dto.ResponseDto;
import com.juandlr.springsecurityproject.dto.UserDto;
import com.juandlr.springsecurityproject.service.ApplicationUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@TestPropertySource("/application.yml")
@AutoConfigureMockMvc
public class MainControllerTest {

    @MockBean
    private ApplicationUserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto("martinADD", "Martin", "Addams", "maddams@example.com");

    }

    @Test
    @WithMockUser(username = "martinADD", roles = {"USER"})
    void shouldReturnStatusOkAndUserDtoWhenFetchUser() throws Exception {
        String userName = "martinADD";
        when(userService.fetchUserInformation(userName)).thenReturn(userDto);
        mockMvc.perform(get("/api/user").param("userName", userName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value(userName))
                .andExpect(jsonPath("$.name").value("Martin"))
                .andExpect(jsonPath("$.lastName").value("Addams"))
                .andExpect(jsonPath("$.email").value("maddams@example.com"));
    }

    @Test
    @WithMockUser(username = "johndlr", roles = {"ADMIN"})
    void shouldReturnStatusOkAndMessageWhenDeleteUser() throws Exception {
        String userName = "martinADD";
        doNothing().when(userService).deleteUser(userName);
        mockMvc.perform(delete("/api/user").param("userName", userName))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ResponseDto(HttpConstants.STATUS_200, HttpConstants.MESSAGE_200_DELETE))));
    }

    @Test
    @WithMockUser(username = "johndlr", roles = {"ADMIN"})
    void shouldReturnStatusOkAndMessageWhenUpdateUser() throws Exception {
        doNothing().when(userService).updateUser(userDto);
        mockMvc.perform(put("/api/user").contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ResponseDto(HttpConstants.STATUS_200, HttpConstants.MESSAGE_200_UPDATE))));
    }


}
