package com.vali.notifications.daos;

import com.vali.notifications.entities.Notification;

import java.util.List;

public interface NotificationDAO {

    Notification insertNotificationInDatabase(Notification notification);

    List<Notification> retrieveLastNotifications(Integer userId, Integer numberOfNotifications);

    boolean updateNotificationOfUser(Integer userId, Integer notificationId, Notification notification);

    List<Notification> retrieveAllNotifications();

    Notification retrieveNotificationByIdAndUser(Integer userId, Integer notificationId);
}
