package com.barbearia.losbrabos.services.impl;

import com.barbearia.losbrabos.domain.user.AuthenticationDTO;
import com.barbearia.losbrabos.domain.user.LoginResponseDTO;
import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.exceptions.UserNotFoundException;
import com.barbearia.losbrabos.infra.security.TokenService;
import com.barbearia.losbrabos.services.interfaces.IAuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AuthenticateServiceImpl implements IAuthenticateService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserServiceImpl userService;

    @Override
    public LoginResponseDTO login(AuthenticationDTO data) {
        User userLogin = userService.findByLogin(data.email().toLowerCase());
        if (userLogin == null) throw new UserNotFoundException();

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return new LoginResponseDTO(token, userLogin.getId(), userLogin.getName(), userLogin.getLogin());
    }
}
