package com.barbearia.losbrabos.services.impl;

import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.exceptions.UserNotFoundException;
import com.barbearia.losbrabos.infra.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SendForgotPasswordEmailServiceImpl {

    private final UserServiceImpl userService;
    private final TokenService tokenService;
    private final JavaMailSender mailSender;

    public void sendEmail(String email) {
        User userMail = userService.findByLogin(email.toLowerCase());
        if (userMail == null)
            throw new UserNotFoundException();

        String token = tokenService.generateToken(userMail);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail.getLogin());
        mailMessage.setSubject("Recuperação de senha");
        mailMessage.setText("Olá " + userMail.getName() + ",\n\n"
                + "Você solicitou a recuperação de senha. Por favor, clique no link a seguir para redefinir sua senha:\n\n"
                + "http://localhost:5173/reset-password?token=" + token + "\n\n"
                + "Se você não solicitou esta recuperação de senha, por favor, ignore este e-mail.\n\n"
                + "Atenciosamente,\nLos Brabos Barber");

        mailSender.send(mailMessage);
    }
}
