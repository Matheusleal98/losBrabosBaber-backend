package com.barbearia.losbrabos.controllers;

import com.barbearia.losbrabos.domain.user.AuthenticationDTO;
import com.barbearia.losbrabos.domain.user.LoginResponseDTO;
import com.barbearia.losbrabos.domain.user.RegisterDTO;
import com.barbearia.losbrabos.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated AuthenticationDTO data) {
        LoginResponseDTO loginResponseDTO = this.userService.login(data);
        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO data) {
        this.userService.register(data);
        return ResponseEntity.ok().build();
    }
}
