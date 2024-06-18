package com.barbearia.losbrabos.controllers;

import com.barbearia.losbrabos.domain.user.ProfileDTO;
import com.barbearia.losbrabos.domain.user.ProfileResponseDTO;
import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.services.impl.ShowProfileServiceImpl;
import com.barbearia.losbrabos.services.impl.UpdateProfileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final UpdateProfileServiceImpl updateProfileService;
    private final ShowProfileServiceImpl showProfileService;

    @GetMapping()
    public ResponseEntity<User> show(@RequestParam Long id){
        User showProfileUser = showProfileService.showProfile(id);
        return ResponseEntity.ok(showProfileUser);
    }

    @PutMapping("/profile")
    public ResponseEntity<ProfileResponseDTO> update(@RequestBody ProfileDTO data) {
        ProfileResponseDTO user = updateProfileService.update(data);
        return ResponseEntity.ok(user);
    }

}
