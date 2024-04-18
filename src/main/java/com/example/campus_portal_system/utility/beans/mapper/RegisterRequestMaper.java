package com.example.campus_portal_system.utility.beans.mapper;

import com.example.campus_portal_system.utility.beans.RegisterRequest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterRequestMaper implements RowMapper<RegisterRequest> {
    @Override
    public RegisterRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RegisterRequest(
                rs.getInt("id"),
                rs.getString("request_type"),
                rs.getString("request_information"),
                rs.getInt("approver_type_id"),
                rs.getInt("approver_id"),
                rs.getString("status")
        );
    }
}
