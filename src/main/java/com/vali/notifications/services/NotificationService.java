package com.vali.notifications.services;

import com.vali.notifications.dtos.NotificationDTO;
import com.vali.notifications.entities.Notification;

import java.util.List;

public interface NotificationService {

    Notification putNotificationInDatabase(NotificationDTO notificationDTO);

    List<Notification> getLastNotifications(Integer userId, Integer numberOfNotifications);

    boolean updateNotificationOfUser(Integer userId, Integer notificationId, NotificationDTO notificationDTO);

    void updateNotificationsAfterReading(Notification notification);

    List<Notification> getAllNotifications();
}
