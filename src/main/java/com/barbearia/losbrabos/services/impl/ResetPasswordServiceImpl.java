package com.barbearia.losbrabos.services.impl;

import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.exceptions.UserNotFoundException;
import com.barbearia.losbrabos.infra.security.TokenService;
import com.barbearia.losbrabos.services.interfaces.IResetPasswordService;
import com.barbearia.losbrabos.services.validations.UserValidationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ResetPasswordServiceImpl implements IResetPasswordService {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserValidationServiceImpl userValidationService;

    @Override
    public void resetPassword(String token, String password, String password_confirmation) {
        String email = tokenService.ValidateToken(token);
        User user = userService.findByLogin(email);
        if (user == null) {
            throw new UserNotFoundException();
        }

        userValidationService.validatePasswordWithPasswordConfirmation(password, password_confirmation);

        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encryptedPassword);
        userService.save(user);
    }
}
