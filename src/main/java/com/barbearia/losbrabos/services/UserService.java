package com.barbearia.losbrabos.services;

import com.barbearia.losbrabos.domain.user.*;
import com.barbearia.losbrabos.exceptions.InvalidPasswordException;
import com.barbearia.losbrabos.exceptions.UserFoundException;
import com.barbearia.losbrabos.exceptions.UserNotFoundException;
import com.barbearia.losbrabos.infra.security.TokenService;
import com.barbearia.losbrabos.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;


    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    public List<User> findAllProviders(Long id) {
        List<User> provider = userRepository.findAll();

        return provider;
    }

    public User findByLogin(String login) {
        User user = (User) userRepository.findByLogin(login);
        if (user.equals(null)) throw new UserNotFoundException();
        return user;
    }

    public LoginResponseDTO login(AuthenticationDTO data) {
        User user = (User) userRepository.findByLogin(data.email().toLowerCase());
        if (user == null)  throw new UserNotFoundException();

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return new LoginResponseDTO(token, user.getId(), user.getName(), user.getLogin());

    }

    public void register(RegisterDTO data) {
        if (this.userRepository.findByLogin(data.email().toLowerCase()) != null)
            throw new UserFoundException();
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        String email = data.email().toLowerCase();
        User newUser = new User(data.name(), email, encryptedPassword, UserRole.USER);
        this.userRepository.save(newUser);
    }

    public ProfileResponseDTO update(ProfileDTO data) {
        User userUpdate = userRepository.findById(data.id()).orElseThrow(() -> new UserNotFoundException());
        User existingUserWithEmail = (User) userRepository.findByLogin(data.email());
        if (existingUserWithEmail != null && !existingUserWithEmail.getId().equals(data.id())) throw new UserFoundException("Já possui usuário com esse E-mail.");

        userUpdate.setLogin(data.email());
        userUpdate.setName(data.name());

        validateOldPasswordWithPassword(data.old_password(), userUpdate.getPassword());
        validatePasswordWithPasswordConfirmation(data.password(), data.password_confirmation());

        String newPassoword = new BCryptPasswordEncoder().encode(data.password());
        userUpdate.setPassword(newPassoword);
        userRepository.save(userUpdate);
        return new ProfileResponseDTO(userUpdate.getId(), userUpdate.getName(), userUpdate.getLogin());
    }

    public void validatePasswordWithPasswordConfirmation(String password, String passwordConfirmation) {
        if (password != null || password.isEmpty() && passwordConfirmation != null){
            if (!password.equals(passwordConfirmation)) throw new InvalidPasswordException("Senhas não correspondentes.");
        }
    }

    public void validateOldPasswordWithPassword(String oldPassword, String password) {
        if (oldPassword != null && password != null){
            boolean isOldPasswordCorrect = new BCryptPasswordEncoder().matches(oldPassword, password);
            if (!isOldPasswordCorrect) throw new InvalidPasswordException("Senha nova não corresponde com a confirmação");
        } else if (!password.equals(null) && oldPassword.equals(null))
            throw new InvalidPasswordException("A senha antiga deve ser informada para atualizar a senha.");
    }
}
