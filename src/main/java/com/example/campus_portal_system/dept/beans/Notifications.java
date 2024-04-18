package com.example.campus_portal_system.dept.beans;

public class Notifications {
    private int id;
    private String userName, message, notificationsReceiverList;


    public Notifications(int id, String userName, String message, String notificationsReceiverList) {
        this.id = id;
        this.userName = userName;
        this.message = message;
        this.notificationsReceiverList = notificationsReceiverList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotificationsReceiverList() {
        return notificationsReceiverList;
    }

    public void setNotificationsReceiverList(String notificationsReceiverList) {
        this.notificationsReceiverList = notificationsReceiverList;
    }

    @Override
    public String toString() {
        return "Notifications{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                ", notificationsReceiverList='" + notificationsReceiverList + '\'' +
                '}';
    }
}
