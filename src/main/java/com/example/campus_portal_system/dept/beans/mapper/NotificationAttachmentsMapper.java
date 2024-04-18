package com.example.campus_portal_system.dept.beans.mapper;

import com.example.campus_portal_system.dept.beans.NotificationAttachments;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationAttachmentsMapper implements RowMapper<NotificationAttachments> {

    @Override
    public NotificationAttachments mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new NotificationAttachments(
                rs.getInt("id"),
                rs.getBytes("attachments")
        );
    }
}
