package com.barbearia.losbrabos.services;

import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.exceptions.UserNotFoundException;
import com.barbearia.losbrabos.infra.security.TokenService;
import com.barbearia.losbrabos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendForgotPasswordEmailService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String email) {
        User user = (User) userRepository.findByLogin(email.toLowerCase());
        if (user == null) throw new UserNotFoundException();

        String token = tokenService.generateToken(user);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getLogin());
        mailMessage.setSubject("Recuperação de senha");
        mailMessage.setText("Olá " + user.getName() + ",\n\n"
                + "Você solicitou a recuperação de senha. Por favor, clique no link a seguir para redefinir sua senha:\n\n"
                + "http://localhost:8080/reset-password?token=" + token + "\n\n"
                + "Se você não solicitou esta recuperação de senha, por favor, ignore este e-mail.\n\n"
                + "Atenciosamente,\nLos Brabos Barber");

        mailSender.send(mailMessage);
    }
}
