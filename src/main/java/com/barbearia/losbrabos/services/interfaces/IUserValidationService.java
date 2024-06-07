package com.barbearia.losbrabos.services.interfaces;

public interface IUserValidationService {
    void validateUserWithEmail(Long userId, String email);
    void validateOldPasswordWithPassword(String oldPassword, String currentPassword, String newPassword);
    void validatePasswordWithPasswordConfirmation(String password, String passwordConfirmation);
}
