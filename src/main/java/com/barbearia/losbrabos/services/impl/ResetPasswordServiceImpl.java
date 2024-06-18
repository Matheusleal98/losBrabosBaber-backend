package com.barbearia.losbrabos.services.impl;

import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.exceptions.UserNotFoundException;
import com.barbearia.losbrabos.infra.security.TokenService;
import com.barbearia.losbrabos.services.interfaces.IResetPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ResetPasswordServiceImpl implements IResetPasswordService {

    private final TokenService tokenService;
    private final UserServiceImpl userService;

    @Override
    public void resetPassword(String token, String password) {
        String email = tokenService.ValidateToken(token);
        User user = userService.findByLogin(email);
        if (user == null) {
            throw new UserNotFoundException();
        }

        String tokenReset = tokenService.generateToken(user);
        user.setPassword(tokenReset);
        userService.save(user);
    }
}
