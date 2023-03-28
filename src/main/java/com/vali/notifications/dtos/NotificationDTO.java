package com.vali.notifications.dtos;

import java.sql.Date;

public class NotificationDTO {
    private Integer userId;
    private Date timeStamp;
    private String message;

    public NotificationDTO() {
    }

    public NotificationDTO(Integer userId, Date timeStamp, String message) {
        this.userId = userId;
        this.timeStamp = timeStamp;
        this.message = message;
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

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "userId=" + userId +
                ", timeStamp=" + timeStamp +
                ", message='" + message + '\'' +
                '}';
    }
}
