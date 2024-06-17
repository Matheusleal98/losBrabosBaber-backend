package com.barbearia.losbrabos.services.interfaces;

import com.barbearia.losbrabos.exceptions.InvalidTokenException;

public interface ITokenValidationService {
    String validateToken(String token) throws InvalidTokenException;
}
