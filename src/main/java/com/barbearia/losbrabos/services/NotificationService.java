package com.barbearia.losbrabos.services;

import com.barbearia.losbrabos.domain.notification.Notification;
import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void createNotification(User recipient, String content) {
        Notification notification = new Notification();
        notification.setRecipient(recipient);
        notification.setContent(content);
        notification.setRead(false);
        notification.setCreateAt(new Date());

        notificationRepository.save(notification);
    }
}
