package com.projetoFinalWeb.projetoWeb.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetoFinalWeb.projetoWeb.controller.AuthController;
import com.projetoFinalWeb.projetoWeb.dto.RegisterDTO;
import com.projetoFinalWeb.projetoWeb.dto.UserDTO;
import com.projetoFinalWeb.projetoWeb.model.User;
import com.projetoFinalWeb.projetoWeb.securityConfig.SecurityConfiguration;
import com.projetoFinalWeb.projetoWeb.service.TokenService;
import com.projetoFinalWeb.projetoWeb.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(SecurityConfiguration.class)
class AuthControllerIntegTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void loginShouldReturnTokenWhenAuthenticated() throws Exception {
        // Mock da autenticação
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken("valid_user", "valid_password");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authToken);

        // Mock do TokenService
        when(tokenService.generatedToken(any())).thenReturn("mocked_token");

        // Corpo da requisição
        UserDTO userDTO = new UserDTO("valid_user", "valid_password");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked_token"));
    }

    @Test
    void registerShouldReturnOkWhenUserIsValid() throws Exception {
        // Mock do UserService
        when(userService.findByLogin("new_user")).thenReturn(null);

        RegisterDTO registerDTO = new RegisterDTO("new_user", "password123");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario registrado"));
    }

    @Test
    void registerShouldReturnBadRequestWhenUserExists() throws Exception {
        when(userService.findByLogin("existing_user")).thenReturn(mock(UserDetails.class));

        RegisterDTO registerDTO = new RegisterDTO("existing_user", "password123");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isBadRequest());
    }
}
