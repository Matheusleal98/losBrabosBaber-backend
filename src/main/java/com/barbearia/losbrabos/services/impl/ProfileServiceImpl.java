package com.barbearia.losbrabos.services.impl;

import com.barbearia.losbrabos.domain.user.ProfileDTO;
import com.barbearia.losbrabos.domain.user.ProfileResponseDTO;
import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.exceptions.UserNotFoundException;
import com.barbearia.losbrabos.services.interfaces.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ProfileServiceImpl implements IProfileService {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserValidationServiceImpl userValidationService;

    @Override
    public ProfileResponseDTO update(ProfileDTO data) {
       User userUpdate = userService.findById(data.id())
               .orElseThrow(UserNotFoundException::new);

       validateEmail(userUpdate, data.email());
       userUpdate.setLogin(data.email());
       userUpdate.setName(data.name());

       if (data.password() != null) {
           validateAndSetNewPassword(userUpdate, data);
       }

       userService.save(userUpdate);
       return new ProfileResponseDTO(userUpdate.getId(), userUpdate.getName(), userUpdate.getLogin());
    }

    private void validateEmail(User user, String newEmail){
        if (!user.getLogin().equals(newEmail)) {
            userValidationService.validateUserWithEmail(user.getId(), newEmail);
        }
    }

    private void validateAndSetNewPassword(User user, ProfileDTO data) {
        userValidationService.validateOldPasswordWithPassword(data.old_password(), user.getPassword(), data.password());
        userValidationService.validatePasswordWithPasswordConfirmation(data.password(), data.password_confirmation());

        String newPassword = passwordEncoder.encode(data.password());
        user.setPassword(newPassword);
    }
}
