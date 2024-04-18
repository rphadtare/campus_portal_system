package com.example.campus_portal_system.dept.beans.mapper;

import com.example.campus_portal_system.dept.beans.Notifications;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationsMapper implements RowMapper<Notifications> {


    @Override
    public Notifications mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Notifications(
          rs.getInt("id"),
          rs.getString("user_name"),
          rs.getString("message"),
                rs.getString("end_user_list")
        );
    }
}
