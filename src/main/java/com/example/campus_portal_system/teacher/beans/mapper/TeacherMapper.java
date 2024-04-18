package com.example.campus_portal_system.teacher.beans.mapper;

import com.example.campus_portal_system.teacher.beans.Teacher;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherMapper implements RowMapper<Teacher>  {


    @Override
    public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Teacher(
                rs.getInt("teacher_id"),
                rs.getInt("institute_id"),
                rs.getInt("department_id"),
                rs.getInt("teacher_type_id"),
                rs.getString("salutations"),
                rs.getString("first_name"),
                rs.getString("middle_name"),
                rs.getString("last_name"),
                rs.getString("qualifications"),
                rs.getString("email_id"),
                rs.getString("contact_no"),
                rs.getInt("is_deleted")
        );
    }
}
