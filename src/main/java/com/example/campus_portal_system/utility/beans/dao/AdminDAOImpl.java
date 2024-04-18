package com.example.campus_portal_system.utility.beans.dao;

import com.example.campus_portal_system.utility.beans.Admin;
import com.example.campus_portal_system.utility.beans.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class AdminDAOImpl implements AdminDAO {

    JdbcTemplate jdbcTemplate;

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

    @Autowired
    public AdminDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Boolean createAdmin(Admin admin) {
        System.out.println("[AdminDAOImpl]: Inside createAdmin ...");
        return jdbcTemplate.update(SQL_CREATE_ADMIN,
                admin.getInstituteId(),
                admin.getAdminTypeId(),
                admin.getSalutations(),
                admin.getFirstName(),
                admin.getMiddleName(),
                admin.getLastName(),
                admin.getEmailId(),
                admin.getContactNo()
                ) > 0;
    }

    @Override
    public Boolean updateAdminInfo(Admin admin) {
        System.out.println("[AdminDAOImpl]: Inside updateAdminInfo ...");
        return jdbcTemplate.update(
                SQL_UPDATE_ADMIN,
                admin.getSalutations(),
                admin.getFirstName(),
                admin.getMiddleName(),
                admin.getLastName(),
                admin.getQualifications(),
                admin.getEmailId(),
                admin.getContactNo(),
                admin.getInstituteId(),
                admin.getAdminId()
        ) > 0;
    }

    @Override
    public Admin getAdminInfo(int instituteId) {
        System.out.println("[AdminDAOImpl]: Inside getAdminInfo ...");
        return jdbcTemplate.queryForObject(SQL_GET_ADMIN_INFO, new Object[]{instituteId}, new AdminMapper());
    }
}
