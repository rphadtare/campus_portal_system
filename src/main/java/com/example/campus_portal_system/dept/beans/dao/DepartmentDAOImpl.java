package com.example.campus_portal_system.dept.beans.dao;

import com.example.campus_portal_system.dept.beans.Department;
import com.example.campus_portal_system.dept.beans.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.logging.Logger;

@Component
public class DepartmentDAOImpl implements DepartmentDAO {

    JdbcTemplate jdbcTemplate;

    private Logger logger;

    @Autowired
    public DepartmentDAOImpl(DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);
        logger = Logger.getLogger(DepartmentDAOImpl.class.getName());
    }

    private final String SQL_GET_ALL_DEPARTMENTS = "select * from department";

    private final String SQL_GET_DEPARTMENT_BY_ID = "select * from department where id = ?";

    private final String SQL_GET_DEPARTMENT_BY_NAME = "select * from department where department_name = ?";


    @Override
    public List<Department> getAllDepartments() {
        logger.info("Inside getAllDepartments");
        return jdbcTemplate.query(SQL_GET_ALL_DEPARTMENTS, new DepartmentMapper());
    }

    @Override
    public Department getDepartmentByID(int id) {
        logger.info("Inside getDepartmentByID");
        return jdbcTemplate.queryForObject(SQL_GET_DEPARTMENT_BY_ID, new Object[] {id}, new DepartmentMapper());
    }

    @Override
    public Department getDepartmentByName(String name) {
        logger.info("Inside getDepartmentByName");
        return jdbcTemplate.queryForObject(SQL_GET_DEPARTMENT_BY_NAME, new Object[] {name}, new DepartmentMapper());
    }
}
