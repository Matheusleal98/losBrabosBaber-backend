package com.barbearia.losbrabos.controllers;

import com.barbearia.losbrabos.domain.user.CreateUserDTO;
import com.barbearia.losbrabos.services.impl.CreateUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UsersController {

    private final CreateUserServiceImpl createUserService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated CreateUserDTO data) {
        this.createUserService.create(data);
        return ResponseEntity.ok().build();
    }

}
