package com.barbearia.losbrabos.controllers;

import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/providers")
    public ResponseEntity<List<User>> findAllProviders() {
        return (ResponseEntity<List<User>>) userService.findAllProviders();
    }
}
