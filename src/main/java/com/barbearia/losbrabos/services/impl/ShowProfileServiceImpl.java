package com.barbearia.losbrabos.services.impl;

import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShowProfileServiceImpl {

    private final UserServiceImpl userService;

    public User showProfile(Long id) {
        User checkUser = userService.findById(id)
                .orElseThrow(UserNotFoundException::new);

        return checkUser;
    }
}
