package com.example.campus_portal_system.utility.beans.mapper;

import com.example.campus_portal_system.utility.beans.Admin;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminMapper implements RowMapper<Admin>  {
    @Override
    public Admin mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Admin(
                rs.getInt("admin_id"),
                rs.getInt("institute_id"),
                rs.getInt("admin_type_id"),
                rs.getString("salutations"),
                rs.getString("first_name"),
                rs.getString("middle_name"),
                rs.getString("last_name"),
                rs.getString("qualifications"),
                rs.getString("email_id"),
                rs.getString("contact_no"),
                rs.getInt("is_deleted"));

    }
}
