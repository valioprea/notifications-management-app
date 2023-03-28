package com.vali.notifications.services;

import com.vali.notifications.daos.NotificationDAO;
import com.vali.notifications.dtos.NotificationDTO;
import com.vali.notifications.entities.Notification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationDAO notificationDAO;

    /**
     * Using this little trick to save me from .setThing(thingDTO.getThing())
     */
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public Notification putNotificationInDatabase(NotificationDTO notificationDTO) {
        Notification notification = modelMapper.map(notificationDTO, Notification.class);
        notification.setNotificationId(null);
        notification.setHasBeenRead(false);
        return notificationDAO.insertNotificationInDatabase(notification);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationDAO.retrieveAllNotifications();
    }

    @Override
    public List<Notification> getLastNotifications(Integer userId, Integer numberOfNotifications) {

        //preventing NullPointerExceptions
        numberOfNotifications = numberOfNotifications == null ? 100 : numberOfNotifications;

        //preventing Denial Of Service attacks
        numberOfNotifications = numberOfNotifications > 100 ? 100 : numberOfNotifications;


        List<Notification> results = notificationDAO.retrieveLastNotifications(userId, numberOfNotifications);

        //TODO: this could be done on a separate thread, to send the response to the client faster
        //TODO: also, I could easily give this up and make a batch update in the DAOImpl
        results.stream().forEach(r -> {
            r.setHasBeenRead(true);
            this.updateNotificationsAfterReading(r);
        });

        return results;
    }

    @Override
    public boolean updateNotificationOfUser(Integer userId, Integer notificationId, NotificationDTO notificationDTO) {
        if (
                notificationDAO.retrieveNotificationByIdAndUser(userId, notificationId).getNotificationId() == -1
        ) {
            return false;
        }

        Notification notification = new Notification();

        notification.setNotificationId(notificationId);
        notification.setUserId(userId);
        notification.setTimeStamp(notificationDTO.getTimeStamp());
        notification.setMessage(notificationDTO.getMessage());
        notification.setHasBeenRead(false); //TODO: this is debatable since it has just been modified

        return notificationDAO.updateNotificationOfUser(userId, notificationId, notification);
    }

    @Override
    public void updateNotificationsAfterReading(Notification notification) {
        notificationDAO.updateNotificationOfUser(
                notification.getUserId(),
                notification.getNotificationId(),
                notification);
    }

}
