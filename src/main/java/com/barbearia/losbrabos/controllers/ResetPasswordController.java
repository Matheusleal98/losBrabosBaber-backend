package com.barbearia.losbrabos.controllers;

import com.barbearia.losbrabos.domain.user.ResetPasswordRequestDTO;
import com.barbearia.losbrabos.services.ResetPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ResetPasswordController {

    @Autowired
    private ResetPasswordService resetPasswordService;

    @PostMapping("/password/reset")
    public ResponseEntity resetPassword(@RequestBody ResetPasswordRequestDTO data) {
        resetPasswordService.resetPassword(data.token(), data.password(), data.password_confirmation());
        return ResponseEntity.ok().build();
    }
}
