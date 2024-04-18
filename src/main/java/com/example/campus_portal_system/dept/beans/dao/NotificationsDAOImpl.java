package com.example.campus_portal_system.dept.beans.dao;

import com.example.campus_portal_system.dept.beans.NotificationAttachments;
import com.example.campus_portal_system.dept.beans.Notifications;
import com.example.campus_portal_system.dept.beans.mapper.NotificationAttachmentsMapper;
import com.example.campus_portal_system.dept.beans.mapper.NotificationsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class NotificationsDAOImpl implements NotificationsDAO {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public NotificationsDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public final String SQL_GET_ALL_NOTIFICATIONS_OF_USER = "select * from notifications" +
            "where user_name = ?";

    public final String SQL_GET_ALL_ATTACHMENTS_OF_NOTIFICATIONS= "select * from notification_attachments" +
            "where id = ?";

    @Override
    public List<Notifications> getAllNotificationsByUser(String userName) {
        System.out.println("[NotificationsDAOImpl]: Inside getAllNotificationsByUser");
        return jdbcTemplate.query(SQL_GET_ALL_NOTIFICATIONS_OF_USER, new Object[]{userName}, new NotificationsMapper());
    }

    @Override
    public List<NotificationAttachments> getNotificationsAttachments(int id) {
        System.out.println("[NotificationsDAOImpl]: Inside getNotificationsAttachments");
        return jdbcTemplate.query(SQL_GET_ALL_ATTACHMENTS_OF_NOTIFICATIONS, new Object[]{id}, new NotificationAttachmentsMapper());
    }
}
