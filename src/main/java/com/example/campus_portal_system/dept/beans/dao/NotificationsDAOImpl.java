package com.example.campus_portal_system.dept.beans.dao;

import com.example.campus_portal_system.dept.beans.NotificationAttachments;
import com.example.campus_portal_system.dept.beans.Notifications;
import com.example.campus_portal_system.dept.beans.mapper.NotificationAttachmentsMapper;
import com.example.campus_portal_system.dept.beans.mapper.NotificationsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.logging.Logger;

@Component
public class NotificationsDAOImpl implements NotificationsDAO {

    JdbcTemplate jdbcTemplate;

    private Logger logger;
    
    @Autowired
    public NotificationsDAOImpl(DataSource dataSource) {
        
        jdbcTemplate = new JdbcTemplate(dataSource);
        logger = Logger.getLogger(NotificationsDAOImpl.class.getName());
    }

    public final String SQL_GET_ALL_NOTIFICATIONS_OF_USER = "select * from notifications" +
            "where user_name = ?";

    public final String SQL_GET_ALL_ATTACHMENTS_OF_NOTIFICATIONS= "select * from notification_attachments" +
            "where id = ?";

    @Override
    public List<Notifications> getAllNotificationsByUser(String userName) {
        logger.info("Inside getAllNotificationsByUser");
        return jdbcTemplate.query(SQL_GET_ALL_NOTIFICATIONS_OF_USER, new Object[]{userName}, new NotificationsMapper());
    }

    @Override
    public List<NotificationAttachments> getNotificationsAttachments(int id) {
        logger.info("Inside getNotificationsAttachments");
        return jdbcTemplate.query(SQL_GET_ALL_ATTACHMENTS_OF_NOTIFICATIONS, new Object[]{id}, new NotificationAttachmentsMapper());
    }
}
