package com.barbearia.losbrabos.services.interfaces;

import com.barbearia.losbrabos.domain.user.AuthenticationDTO;
import com.barbearia.losbrabos.domain.user.LoginResponseDTO;

public interface IAuthenticateService {
    LoginResponseDTO login(AuthenticationDTO data);
}
