package com.projetoFinalWeb.projetoWeb.dto;

import lombok.Data;

@Data
public class UserDTO {

    private String login;
    private String password;

    public UserDTO(String user, String password) {
    }
}
