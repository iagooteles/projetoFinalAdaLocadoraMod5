package com.projetoFinalWeb.projetoWeb.service;

import com.projetoFinalWeb.projetoWeb.dto.RegisterDTO;
import com.projetoFinalWeb.projetoWeb.model.User;
import com.projetoFinalWeb.projetoWeb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails findByLogin(String login) {
        return this.userRepository.findByLogin(login);
    }

    public void salvar(RegisterDTO registerDTO) {
        String passwordEncrypted = new BCryptPasswordEncoder().encode(registerDTO.getPassword());
        User user = new User(registerDTO.getLogin(), passwordEncrypted, registerDTO.getRole());
        this.userRepository.save(user);
    }
}
