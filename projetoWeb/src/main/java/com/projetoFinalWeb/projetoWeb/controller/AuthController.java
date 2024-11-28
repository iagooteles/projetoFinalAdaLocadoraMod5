package com.projetoFinalWeb.projetoWeb.controller;

import com.projetoFinalWeb.projetoWeb.dto.LoginResponseDTO;
import com.projetoFinalWeb.projetoWeb.dto.RegisterDTO;
import com.projetoFinalWeb.projetoWeb.dto.UserDTO;
import com.projetoFinalWeb.projetoWeb.exception.TokenInvalidException;
import com.projetoFinalWeb.projetoWeb.service.TokenService;
import com.projetoFinalWeb.projetoWeb.service.UserService;
import com.projetoFinalWeb.projetoWeb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserDTO userDTO) throws TokenInvalidException {
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDTO.getLogin(), userDTO.getPassword());
        var authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        String token = this.tokenService.generatedToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO registerDTO) {
        if(Objects.nonNull(this.userService.findByLogin(registerDTO.getLogin()))) {
            return ResponseEntity.badRequest().build();
        }

        this.userService.salvar(registerDTO);
        return ResponseEntity.ok("Usuario registrado");
    }

}
