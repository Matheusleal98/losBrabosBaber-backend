package com.barbearia.losbrabos.services.interfaces;

import com.barbearia.losbrabos.domain.notification.Notification;
import com.barbearia.losbrabos.domain.user.User;

public interface INotificationService {
    void insert(User user, String message);
    void save(Notification notification);
}
