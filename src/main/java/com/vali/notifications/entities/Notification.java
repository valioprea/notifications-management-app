package com.vali.notifications.entities;

import java.sql.Date;

public class Notification {

    private Integer notificationId;
    private Integer userId;
    private Date timeStamp;
    private String message;
    private Boolean hasBeenRead;

    public Notification(){}
    public Notification(Integer notificationId, Integer userId, Date timeStamp, String message, Boolean hasBeenRead) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.timeStamp = timeStamp;
        this.message = message;
        this.hasBeenRead = hasBeenRead;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getHasBeenRead() {
        return hasBeenRead;
    }

    public void setHasBeenRead(Boolean hasBeenRead) {
        this.hasBeenRead = hasBeenRead;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", userId=" + userId +
                ", date=" + timeStamp +
                ", message='" + message + '\'' +
                ", hasBeenRead=" + hasBeenRead +
                '}';
    }
}
