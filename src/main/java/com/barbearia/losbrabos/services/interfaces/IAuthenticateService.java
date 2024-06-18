package com.barbearia.losbrabos.services.interfaces;

import com.barbearia.losbrabos.domain.user.AuthenticationDTO;
import com.barbearia.losbrabos.domain.user.LoginResponseDTO;
import com.barbearia.losbrabos.domain.user.RegisterDTO;
import org.springframework.http.ResponseEntity;

public interface IAuthenticateService {
    LoginResponseDTO login(AuthenticationDTO data);
}
