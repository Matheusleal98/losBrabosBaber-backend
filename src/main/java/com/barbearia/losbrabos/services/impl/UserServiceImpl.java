package com.barbearia.losbrabos.services.impl;

import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.repositories.UserRepository;
import com.barbearia.losbrabos.services.interfaces.IUserService;
import com.barbearia.losbrabos.services.validations.UserValidationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserValidationServiceImpl userValidationService;

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAllProviders() {
        return userRepository.findAll();
    }

    @Override
    public User findByLogin(String login) {
        User user = (User) userRepository.findByLogin(login);
        return user;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
