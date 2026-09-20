package com.skillbridge.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Notification implements Serializable {

    private int notificationId;
    private int userId;
    private String title;
    private String message;
    private int isRead;
    private Timestamp createdAt;

    public Notification() {
    }

    public Notification(int userId,
                        String title,
                        String message) {

        this.userId = userId;
        this.title = title;
        this.message = message;
        this.isRead = 0;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isRead() {
        return isRead == 1;
    }
}