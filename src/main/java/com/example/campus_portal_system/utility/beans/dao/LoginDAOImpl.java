package com.example.campus_portal_system.utility.beans.dao;

import com.example.campus_portal_system.utility.beans.Login;
import com.example.campus_portal_system.utility.beans.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.logging.Logger;

@Component
public class LoginDAOImpl implements  LoginDAO {

    JdbcTemplate jdbcTemplate;

    private Logger logger;

    private final String SQL_CREATE_LOGIN = "insert into login(user_type," +
            "user_name, password" +
            "is_deleted)" +
            "values(?,?,?,0)";

    private final String SQL_DELETE_LOGIN = "update login" +
            "set" +
            "is_deleted = 1" +
            "where id = ?";

    private final String SQL_GET_LOGIN = "select * from login where id = ?";

    private final String SQL_GET_ALL_LOGIN = "select * from login";

    private final String SQL_GET_ALL_LOGIN_OF_USERS = "select * from login where user_type = ?";


    @Autowired
    public LoginDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        logger = Logger.getLogger(LoginDAOImpl.class.getName());
    }

    @Override
    public Boolean createLogin(Login login) {
        logger.info("Inside createLogin ...");
        return jdbcTemplate.update(
                SQL_CREATE_LOGIN,
                login.getUserType(),
                login.getUserName(),
                login.getUserPassword()
        ) > 0;
    }

    @Override
    public Boolean deleteLogin(int loginId) {
        logger.info("Inside deleteLogin ...");
        return jdbcTemplate.update(
                SQL_DELETE_LOGIN,
                loginId
        ) > 0;
    }

    @Override
    public Login getLogin(int loginId) {
        logger.info("Inside getLogin ...");
        return jdbcTemplate.queryForObject(SQL_GET_LOGIN, new Object[] {loginId}, new LoginMapper());
    }

    @Override
    public List<Login> getAllLogin() {
        logger.info("Inside getAllLogin ...");
        return jdbcTemplate.query(SQL_GET_ALL_LOGIN, new LoginMapper());
    }

    @Override
    public List<Login> getAllLogin(int userTypeId) {
        logger.info("Inside getAllLogin of user type : " + userTypeId);
        return jdbcTemplate.query(SQL_GET_ALL_LOGIN_OF_USERS, new Object[] {userTypeId}, new LoginMapper());
    }
}
