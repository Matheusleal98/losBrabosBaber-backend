package com.barbearia.losbrabos.services.impl;

import com.barbearia.losbrabos.domain.user.ProfileDTO;
import com.barbearia.losbrabos.domain.user.ProfileResponseDTO;
import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.exceptions.InvalidDateException;
import com.barbearia.losbrabos.exceptions.InvalidPasswordException;
import com.barbearia.losbrabos.exceptions.UserFoundException;
import com.barbearia.losbrabos.exceptions.UserNotFoundException;
import com.barbearia.losbrabos.infra.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateProfileServiceImpl {
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public ProfileResponseDTO update(ProfileDTO data) {
       User userUpdate = userService.findById(data.id())
               .orElseThrow(UserNotFoundException::new);

       checkEmailExists(data.email(), userUpdate.getId());

       userUpdate.setName(data.name());
       userUpdate.setLogin(data.email());

       validatePasswordWithOldPassword(data.password(), data.old_password());

       if (data.password() != null && data.old_password() != null){
           isValidPassword(data.password(), data.old_password());

           String newPassword = tokenService.generateToken(userUpdate);
           userUpdate.setPassword(newPassword);
       }

       userService.save(userUpdate);
       return new ProfileResponseDTO(userUpdate.getId(), userUpdate.getName(), userUpdate.getLogin());
    }

    private void checkEmailExists(String email, Long userId) {
        User emailExists = userService.findByLogin(email);
        if (emailExists != null && !emailExists.getId().equals(userId)) {
            throw new UserFoundException("E-mail já em uso.");
        }
    }

    private void validatePasswordWithOldPassword(String password, String oldPassword) {
        if (password != null && oldPassword.isEmpty()) {
            throw new InvalidDateException("A senha antiga deverá ser informada para atualização da senha.");
        }
    }

    private void isValidPassword(String password, String oldPassword) {
        boolean isOldPasswordCorrect = passwordEncoder.matches(oldPassword, password);
        if (!isOldPasswordCorrect) {
            throw new InvalidPasswordException("Senha antiga não confere");
        }
    }
}
