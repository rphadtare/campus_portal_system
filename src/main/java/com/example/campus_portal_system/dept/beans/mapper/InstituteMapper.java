package com.example.campus_portal_system.dept.beans.mapper;

import com.example.campus_portal_system.dept.beans.Institute;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InstituteMapper implements RowMapper<Institute> {
    @Override
    public Institute mapRow(ResultSet resultSet, int i) throws SQLException {
        Institute institute = new Institute();
        institute.setInstitute_id(resultSet.getInt("institute_id"));
        institute.setName(resultSet.getString("name"));
        institute.setUniversity(resultSet.getString("university"));
        institute.setEmail_id(resultSet.getString("email_id"));
        institute.setAddress(resultSet.getString("address"));

        return institute;
    }
}
