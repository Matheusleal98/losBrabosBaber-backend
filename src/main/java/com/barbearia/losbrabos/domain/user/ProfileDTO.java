package com.barbearia.losbrabos.domain.user;

public record ProfileDTO(Long id, String email, String name, String old_password, String password, String password_confirmation) {
}
