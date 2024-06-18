package com.barbearia.losbrabos.controllers;

import com.barbearia.losbrabos.domain.user.ForgotRequestDTO;
import com.barbearia.losbrabos.services.impl.SendForgotPasswordEmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ForgotPasswordController {

    private final SendForgotPasswordEmailServiceImpl sendForgotPasswordEmailService;

    @PostMapping("/password/forgot")
    public ResponseEntity sendEmail(@RequestBody ForgotRequestDTO dto) {
        sendForgotPasswordEmailService.sendEmail(dto.email());
        return ResponseEntity.ok().build();
    }
}