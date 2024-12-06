package com.projetoFinalWeb.projetoWeb.dto;

import com.projetoFinalWeb.projetoWeb.model.UserRole;
import lombok.Data;

@Data
public class RegisterDTO {

    private String login;
    private String password;
    private UserRole role;

    public RegisterDTO(String newUser, String password) {
        this.login = newUser;
        this.password = password;
    }
}
