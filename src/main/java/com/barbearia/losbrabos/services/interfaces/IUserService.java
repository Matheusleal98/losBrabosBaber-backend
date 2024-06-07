package com.barbearia.losbrabos.services.interfaces;

import com.barbearia.losbrabos.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> findById(Long id);
    List<User> findAllProviders();
    User findByLogin(String login);
    void save(User user);
}
