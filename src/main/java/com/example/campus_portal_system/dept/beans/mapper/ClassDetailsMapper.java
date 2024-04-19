package com.example.campus_portal_system.dept.beans.mapper;

import com.example.campus_portal_system.dept.beans.ClassDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClassDetailsMapper implements RowMapper<ClassDetails> {
    @Override
    public ClassDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        if(rs.isBeforeFirst()){
            return new ClassDetails(
                    rs.getInt("id"),
                    rs.getString("class_name"),
                    rs.getInt("institute_id"),
                    rs.getInt("department_id"),
                    rs.getInt("class_teacher_id"),
                    rs.getBytes("timetable")
            );
        } else {
            return null;
        }

    }
}
