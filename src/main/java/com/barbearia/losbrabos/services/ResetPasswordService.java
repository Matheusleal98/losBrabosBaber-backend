package com.barbearia.losbrabos.services;

import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.exceptions.InvalidTokenException;
import com.barbearia.losbrabos.exceptions.UserNotFoundException;
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

    public void resetPassword(String token, String password, String login){
        if (tokenService.ValidateToken(token).isEmpty())
            throw new InvalidTokenException();

        User user = (User) userRepository.findByLogin(login);
        if (user.equals(null)) throw new UserNotFoundException();

        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }
}
