package com.barbearia.losbrabos.services.impl;

import com.barbearia.losbrabos.exceptions.InvalidTokenException;
import com.barbearia.losbrabos.infra.security.TokenService;
import com.barbearia.losbrabos.services.interfaces.ITokenValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenInvalidationServiceImpl implements ITokenValidationService {
    @Autowired
    private TokenService tokenService;

    @Override
    public String validateToken(String token) throws InvalidTokenException {
        String email = tokenService.ValidateToken(token);
        if (email == null || email.isEmpty()) {
            throw new InvalidTokenException();
        }
        return email;
    }
}
