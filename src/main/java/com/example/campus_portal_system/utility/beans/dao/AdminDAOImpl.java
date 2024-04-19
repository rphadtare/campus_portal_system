package com.example.campus_portal_system.utility.beans.dao;

import com.example.campus_portal_system.utility.beans.Admin;
import com.example.campus_portal_system.utility.beans.mapper.AdminMapper;
import com.example.campus_portal_system.utility.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.logging.Logger;

@Component
public class AdminDAOImpl implements AdminDAO {

    JdbcTemplate jdbcTemplate;

    private Logger logger = Logger.getLogger(AdminDAOImpl.class.getName());

    private final String SQL_CREATE_ADMIN = "insert into admin(institute_id, admin_type_id," +
            "salutations, first_name, middle_name, last_name," +
            "email_id, contact_no, is_deleted)" +
            "values(?,?,?,?,?,?,?,?,0)";

    private final String SQL_UPDATE_ADMIN = "update admin" +
            "set" +
            "salutations = ?," +
            "first_name = ?, middle_name = ?, last_name=? " +
            "qualifications = ?, email_id = ?, contact_no = ?" +
            "where institute_id = ? and admin_id = ?";

    private final String SQL_GET_ADMIN_INFO = "select * from admin where institute_id = ?";

    private final String SQL_GET_ADMIN_INFO_BY_INSTITUTE_AND_EMAIL_ID = "select * from admin where institute_id = ?" +
            " and email_id = ?";

    private final String SQL_CHECK_ADMIN_EXIST_FOR_INSTITUTE = "select * from admin where institute_id = ? limit 1";

    @Autowired
    public AdminDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Boolean createAdmin(Admin admin) {
        logger.info("Inside createAdmin " + admin);
        int result = 0;
        try {

            result = jdbcTemplate.update(SQL_CREATE_ADMIN,
                    admin.getInstituteId(),
                    admin.getAdminTypeId(),
                    admin.getSalutations(),
                    admin.getFirstName(),
                    admin.getMiddleName(),
                    admin.getLastName(),
                    admin.getEmailId(),
                    admin.getContactNo()
            );

        } catch(Exception e) {
            logger.severe("Exception occurred while creating " + admin + " Exception details are -" + e.getMessage());
            result = 0;
        }

        if(result > 0) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Boolean updateAdminInfo(Admin admin) {
        logger.info("Inside updateAdminInfo for admin " + admin);
        int result = 0;

        try {

            result = jdbcTemplate.update(
                    SQL_UPDATE_ADMIN,
                    admin.getSalutations(),
                    admin.getFirstName(),
                    admin.getMiddleName(),
                    admin.getLastName(),
                    admin.getQualifications(),
                    admin.getEmailId(),
                    admin.getContactNo(),
                    admin.getInstituteId(),
                    admin.getAdminId());

        } catch(Exception e) {
            logger.severe("Exception occurred while updating " + admin + " Exception details are -" + e.getMessage());
            result = 0;
        }

        if(result > 0) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Admin getAdminInfo(int instituteId) {
        logger.info("Inside getAdminInfo for institute id : " + instituteId);
        try{
            return jdbcTemplate.queryForObject(SQL_GET_ADMIN_INFO, new Object[]{instituteId}, new AdminMapper());
        } catch (Exception e) {
            logger.severe("Inside getAdminInfo exception occurred - " + e.getMessage());
        }
        return new Admin(-1);
    }

    @Override
    public Admin getAdminInfo(int instituteId, String emailId) {
        logger.info("Inside getAdminInfo for email id " + emailId + " and institute id : " + instituteId);
        try{
            return jdbcTemplate.queryForObject(SQL_GET_ADMIN_INFO_BY_INSTITUTE_AND_EMAIL_ID, new Object[]{instituteId, emailId}, new AdminMapper());
        } catch (Exception e) {
            logger.severe("Inside getAdminInfo for email id " + emailId + " and institute id : " + instituteId +
                    "exception occurred - " + e.getMessage());
        }
        return new Admin(-1);
    }

    @Override
    public Boolean checkIfAdminExistForInstitute(int instituteId) {
        logger.info("Inside checkIfAdminExistForInstitute for institute id : " + instituteId);
        Admin admin = new Admin(-1);

        try{
            admin = jdbcTemplate.queryForObject(SQL_CHECK_ADMIN_EXIST_FOR_INSTITUTE,
                    new Object[]{instituteId}, new AdminMapper());

        } catch (Exception e) {
            logger.severe("Inside checkIfAdminExistForInstitute for institute id : " + instituteId +
                    "exception occurred - " + e.getMessage());
        }

        if(admin.getAdminId() == -1){
            return false;
        } else {
            return true;
        }
    }
}
