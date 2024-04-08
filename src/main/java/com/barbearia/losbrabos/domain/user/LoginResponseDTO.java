package com.barbearia.losbrabos.domain.user;

public record LoginResponseDTO(String token, Long id, String name, String email) {
}
