package com.barbearia.losbrabos.services.impl;

import com.barbearia.losbrabos.domain.user.CreateUserDTO;
import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.exceptions.UserFoundException;
import com.barbearia.losbrabos.infra.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateUserServiceImpl {

    private final UserServiceImpl userService;
    private final TokenService tokenService;

    public User create(CreateUserDTO data) {
        User checkUserExists = userService.findByLogin(data.email());

        if (checkUserExists != null) {
            throw new UserFoundException("Endereço de e-mail já utilizado.");
        }

        String hashedPassword = new BCryptPasswordEncoder().encode(data.password());
        User createdUser = userService.insert(data.name(), data.email(), hashedPassword);
        return createdUser;
    }
}
