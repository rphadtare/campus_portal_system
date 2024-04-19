package com.example.campus_portal_system.dept.beans.dao;

import com.example.campus_portal_system.dept.beans.Institute;
import com.example.campus_portal_system.dept.beans.mapper.InstituteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
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

    private final String SQL_CREATE_INSTITUTE = "insert into institute(name,university,email_id,address) values(?,?,?,?)";

    private final String SQL_CHECK_INSTITUTE_EXIST = "select * from institute " +
            "where " +
            "name = ? and email_id = ?";

    @Override
    public Institute getInstituteById(int id) {
        logger.info("Inside getInstituteById to fetch information for for institute id " + id);
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_INSTITUTE_BY_ID, new Object[] { id }, new InstituteMapper());
        } catch (Exception e){
            logger.severe("Exception occurred while fetching institute information for institute id " + id);
            Institute institute = new Institute();
            institute.setInstitute_id(-1);
            return institute;
        }

    }

    @Override
    public List<Institute> getAllInstitute() {
        return jdbcTemplate.query(SQL_GET_ALL_INSTITUTES, new InstituteMapper());
    }

    @Override
    public boolean deleteInstitute(Institute institute) {
        return jdbcTemplate.update(SQL_DELETE_INSTITUTE_BY_ID, institute.getInstitute_id()) > 0;
    }

    public boolean deleteInstitute(int id) {
        return jdbcTemplate.update(SQL_DELETE_INSTITUTE_BY_ID, id) > 0;
    }

    @Override
    public boolean updateInstitute(Institute institute) {
        return jdbcTemplate.update(SQL_UPDATE_INSTITUTE_BY_ID, institute.getName(), institute.getUniversity(),
                institute.getEmail_id(), institute.getAddress(),
                institute.getInstitute_id()) > 0;
    }

    @Override
    public boolean createInstitute(Institute institute) {
        logger.info("Inside createInstitute to register institute - " + institute);
        return jdbcTemplate.update(SQL_CREATE_INSTITUTE,
                institute.getName(), institute.getUniversity(),
                institute.getEmail_id(), institute.getAddress()) > 0;
    }

    @Override
    public boolean checkExist(Institute institute) {
        logger.info("Inside checkExist to check institute exist or not - " + institute);
        Institute institute1 = new Institute();

        try {
            institute1 = jdbcTemplate.queryForObject(SQL_CHECK_INSTITUTE_EXIST,
                    new Object[]{institute.getName(), institute.getEmail_id()},
                    new InstituteMapper());
        } catch (Exception e) {
            logger.severe("Exception in checkExist - " + e.getMessage());
            institute1.setInstitute_id(-1);
        }
         boolean result = true;

        if(institute1.getInstitute_id() == -1){
            result = false;
        }

        logger.info("checkExist - result of institute exist or not - " + institute + " is " + result);

        return result;
    }
}
