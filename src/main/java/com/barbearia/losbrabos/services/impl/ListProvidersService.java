package com.barbearia.losbrabos.services.impl;

import com.barbearia.losbrabos.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListProvidersService {

    private final UserServiceImpl userService;

    public List<User> listProviders() {
        List<User> users = userService.findAllProviders();
        return users;
    }
}
