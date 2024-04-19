package com.example.campus_portal_system.dept.beans.dao;

import com.example.campus_portal_system.dept.beans.Institute;
import com.example.campus_portal_system.dept.beans.mapper.InstituteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.logging.Logger;

@Component
public class InstituteDAOImpl implements InstituteDAO {

    JdbcTemplate jdbcTemplate;

    private Logger logger;

    @Autowired
    public InstituteDAOImpl(DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);
        logger = Logger.getLogger(InstituteDAOImpl.class.getName());
    }

    private final String SQL_FIND_INSTITUTE_BY_ID = "select * from institute where institute_id = ?";

    private final String SQL_GET_ALL_INSTITUTES = "select * from institute";

    private final String SQL_DELETE_INSTITUTE_BY_ID = "delete from institute where institute_id = ?";

    private final String SQL_UPDATE_INSTITUTE_BY_ID = "update institute set name=?, university = ?, email_id = ?, address = ? where institute_id = ?";

    private final String SQL_CREATE_INSTITUTE = "insert into institute values(?,?,?,?,?)";

    @Override
    public Institute getInstituteById(int id) {
        return jdbcTemplate.queryForObject(SQL_FIND_INSTITUTE_BY_ID, new Object[] { id }, new InstituteMapper());
    }

    @Override
    public List<Institute> getAllInstitute() {
        return jdbcTemplate.query(SQL_GET_ALL_INSTITUTES, new InstituteMapper());
    }

    @Override
    public boolean deleteInstitute(Institute institute) {
        return jdbcTemplate.update(SQL_DELETE_INSTITUTE_BY_ID, institute.getInstitute_id()) > 0;
    }

    @Override
    public boolean updateInstitute(Institute institute) {
        return jdbcTemplate.update(SQL_UPDATE_INSTITUTE_BY_ID, institute.getName(), institute.getUniversity(),
                institute.getEmail_id(), institute.getAddress(),
                institute.getInstitute_id()) > 0;
    }

    @Override
    public boolean createInstitute(Institute institute) {
        return jdbcTemplate.update(SQL_CREATE_INSTITUTE, institute.getInstitute_id(),
                institute.getName(), institute.getUniversity(),
                institute.getEmail_id(), institute.getAddress()) > 0;
    }
}
