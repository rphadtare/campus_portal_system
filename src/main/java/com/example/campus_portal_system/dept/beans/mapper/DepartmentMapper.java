package com.example.campus_portal_system.dept.beans.mapper;

import com.example.campus_portal_system.dept.beans.Department;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentMapper implements RowMapper<Department> {
    @Override
    public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Department(
                rs.getInt("id"),
                rs.getString("department_name")
        );
    }
}
