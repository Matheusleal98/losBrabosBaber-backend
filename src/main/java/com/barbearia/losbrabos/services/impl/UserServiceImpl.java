package com.barbearia.losbrabos.services.impl;

import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.domain.user.UserRole;
import com.barbearia.losbrabos.repositories.UserRepository;
import com.barbearia.losbrabos.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

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
    public User insert(String name, String email, String password) {
        User newUser = new User();
        newUser.setName(name);
        newUser.setLogin(email);
        newUser.setPassword(password);
        newUser.setRole(UserRole.USER);

        save(newUser);
        return newUser;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
