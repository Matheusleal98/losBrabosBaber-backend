package com.barbearia.losbrabos.controllers;

import com.barbearia.losbrabos.services.SendForgotPasswordEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ForgotPasswordController {

    @Autowired
    private SendForgotPasswordEmailService sendForgotPasswordEmailService;

    @PostMapping("/forgot-password")
    public ResponseEntity sendEmail(@RequestBody String email) {
        sendForgotPasswordEmailService.sendEmail(email);
        return ResponseEntity.ok().build();
    }
}