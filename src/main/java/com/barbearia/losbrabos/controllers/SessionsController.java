package com.barbearia.losbrabos.controllers;

import com.barbearia.losbrabos.domain.user.AuthenticationDTO;
import com.barbearia.losbrabos.domain.user.LoginResponseDTO;
import com.barbearia.losbrabos.services.impl.AuthenticateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SessionsController {
    private final AuthenticateServiceImpl authenticateService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated AuthenticationDTO data) {
        LoginResponseDTO loginResponseDTO = this.authenticateService.login(data);
        return ResponseEntity.ok(loginResponseDTO);
    }
}
