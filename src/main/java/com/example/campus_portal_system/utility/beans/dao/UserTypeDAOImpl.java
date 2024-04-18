package com.example.campus_portal_system.utility.beans.dao;

import com.example.campus_portal_system.utility.beans.UserType;
import com.example.campus_portal_system.utility.beans.mapper.UserTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class UserTypeDAOImpl implements UserTypeDAO {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public UserTypeDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final String SQL_GET_ALL_USER_TYPES = "select * from all_user_types";

    @Override
    public List<UserType> getAllUserTypes() {
        System.out.println("[UserTypeDAOImpl]: Inside getAllUserTypes ...");
        jdbcTemplate.query(SQL_GET_ALL_USER_TYPES, new UserTypeMapper());
        return null;
    }
}
