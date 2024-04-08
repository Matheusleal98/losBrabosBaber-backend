package com.barbearia.losbrabos.controllers;

import com.barbearia.losbrabos.domain.user.ProfileDTO;
import com.barbearia.losbrabos.domain.user.ProfileResponseDTO;
import com.barbearia.losbrabos.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    @Autowired
    private UserService userService;

    @PutMapping("/profile")
    public ResponseEntity<ProfileResponseDTO> update(@RequestBody ProfileDTO data) {
        ProfileResponseDTO user = userService.update(data);
        return ResponseEntity.ok(user);
    }

}
