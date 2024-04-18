package com.example.campus_portal_system.utility.beans.mapper;

import com.example.campus_portal_system.utility.beans.Admin;
import com.example.campus_portal_system.utility.beans.UserType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTypeMapper implements RowMapper<UserType>  {


    @Override
    public UserType mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserType(
                rs.getInt("id"),
                rs.getString("user_type")
        );
    }
}
