package com.barbearia.losbrabos.domain.user;

public record ResetPasswordRequestDTO(String token, String password, String password_confirmation){}
