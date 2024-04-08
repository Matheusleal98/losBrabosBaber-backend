package com.barbearia.losbrabos.services;

import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.exceptions.InvalidTokenException;
import com.barbearia.losbrabos.infra.security.TokenService;
import com.barbearia.losbrabos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordService {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public void resetPassword(String token, String password, String password_confirmation){
        if (tokenService.ValidateToken(token).isEmpty())
            throw new InvalidTokenException();

        String email = tokenService.ValidateToken(token);
        User userResetPassword = userService.findByLogin(email);

        userService.validatePasswordWithPasswordConfirmation(password, password_confirmation);

        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        userResetPassword.setPassword(encryptedPassword);
        userRepository.save(userResetPassword);
    }
}
