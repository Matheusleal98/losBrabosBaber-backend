package com.barbearia.losbrabos.services.impl;

import com.barbearia.losbrabos.domain.user.RegisterDTO;
import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.domain.user.UserRole;
import com.barbearia.losbrabos.exceptions.UserFoundException;
import com.barbearia.losbrabos.services.interfaces.IRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegisterServiceImpl implements IRegisterService {

    private final UserServiceImpl userService;

    @Override
    public void register(RegisterDTO data) {
        String email = data.email().toLowerCase();
        if (userService.findByLogin(email) != null) throw new UserFoundException();
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), email, encryptedPassword, UserRole.USER);
        userService.save(newUser);
    }
}
