package com.barbearia.losbrabos.services.impl;

import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.exceptions.InvalidPasswordException;
import com.barbearia.losbrabos.exceptions.UserFoundException;
import com.barbearia.losbrabos.services.interfaces.IUserValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserValidationServiceImpl implements IUserValidationService {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void validateUserWithEmail(Long userId, String email) {
        User existingUserWithEmail = userService.findByLogin(email);
        if (existingUserWithEmail != null && !existingUserWithEmail.getId().equals(userId)) {
            throw new UserFoundException("Já possui usuário com esse E-mail");
        }
    }

    @Override
    public void validateOldPasswordWithPassword(String oldPassword, String currentPassword, String newPassword) {
        if (oldPassword != null && currentPassword != null) {
            boolean isOldPasswordCorrect = passwordEncoder.matches(oldPassword, currentPassword);
            if (isOldPasswordCorrect) {
                throw new InvalidPasswordException("A senha antiga está incorreta");
            }
        } else if (newPassword != null && oldPassword == null) {
            throw new InvalidPasswordException("A senha antiga deve ser informada para atualizar a senha");
        }
    }

    @Override
    public void validatePasswordWithPasswordConfirmation(String password, String passwordConfirmation) {
        if (password != null && passwordConfirmation != null) {
            if (!password.equals(passwordConfirmation)) {
                throw new InvalidPasswordException("Senhas não correspondentes");
            }
        }
    }
}
