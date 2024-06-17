package com.barbearia.losbrabos.services.interfaces;

public interface IResetPasswordService {
    void resetPassword(String token, String password, String password_confirmation);
}
