package com.example.campus_portal_system.utility.beans.mapper;

import com.example.campus_portal_system.utility.beans.Admin;
import com.example.campus_portal_system.utility.beans.Login;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginMapper implements RowMapper<Login>  {
    @Override
    public Login mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Login(
                rs.getInt("id"),
                rs.getInt("user_type"),
                rs.getString("user_name"),
                rs.getString("password"),
                rs.getInt("is_deleted")
        );
    }
}
