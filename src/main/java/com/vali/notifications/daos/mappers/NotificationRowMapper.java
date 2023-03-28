package com.vali.notifications.daos.mappers;

import com.vali.notifications.entities.Notification;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationRowMapper implements RowMapper<Notification> {
    @Override
    public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
        Notification notification = new Notification();
        notification.setNotificationId(rs.getInt(1));
        notification.setUserId(rs.getInt(2));
        notification.setTimeStamp(rs.getDate(3));
        notification.setMessage(rs.getString(4));
        notification.setHasBeenRead(rs.getBoolean(5));
        return notification;
    }
}
