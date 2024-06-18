package com.barbearia.losbrabos.services.impl;

import com.barbearia.losbrabos.domain.notification.Notification;
import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.repositories.NotificationRepository;
import com.barbearia.losbrabos.services.interfaces.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements INotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public void insert(User user, String message) {
        Notification notification = new Notification();
        notification.setRecipient(user);
        notification.setContent(message);
        notification.setRead(false);
        notification.setCreateAt(new Date());

        save(notification);
    }

    @Override
    public void save(Notification notification) {
        notificationRepository.save(notification);
    }
}
