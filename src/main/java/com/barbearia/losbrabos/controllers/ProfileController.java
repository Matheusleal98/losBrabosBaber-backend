package com.barbearia.losbrabos.controllers;

import com.barbearia.losbrabos.domain.user.ProfileDTO;
import com.barbearia.losbrabos.domain.user.ProfileResponseDTO;
import com.barbearia.losbrabos.services.impl.UpdateProfileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final UpdateProfileServiceImpl updateProfileService;

    @PutMapping("/profile")
    public ResponseEntity<ProfileResponseDTO> update(@RequestBody ProfileDTO data) {
        ProfileResponseDTO user = updateProfileService.update(data);
        return ResponseEntity.ok(user);
    }

}
