package com.example.campus_portal_system.dept.beans.dao;

import com.example.campus_portal_system.dept.beans.NotificationAttachments;
import com.example.campus_portal_system.dept.beans.Notifications;

import java.util.List;

public interface NotificationsDAO {

    public List<Notifications> getAllNotificationsByUser(String userName);

    public List<NotificationAttachments> getNotificationsAttachments(int id);
}
