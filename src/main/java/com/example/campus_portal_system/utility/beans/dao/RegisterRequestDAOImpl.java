package com.example.campus_portal_system.utility.beans.dao;

import com.example.campus_portal_system.utility.beans.RegisterRequest;
import com.example.campus_portal_system.utility.beans.mapper.RegisterRequestMaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class RegisterRequestDAOImpl implements  RegisterRequestDAO {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public RegisterRequestDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public final String SQL_GET_REQUESTS_BY_APPROVALS_ID_AND_TYPE = "select * from register_request " +
            "where approver_type_id = ? and approver_id = ?";

    public final String SQL_UPDATE_STATUS_OF_REQUEST = "update register_request" +
            "set" +
            "status = ?" +
            "where id = ?";

    @Override
    public List<RegisterRequest> getRequests(int userTypeIdForApproval, int userIdForApproval) {
        System.out.println("[RegisterRequestDAOImpl]: Inside getRequests ...");
        return jdbcTemplate.query(SQL_GET_REQUESTS_BY_APPROVALS_ID_AND_TYPE,
                new Object[]{userTypeIdForApproval, userIdForApproval},
                new RegisterRequestMaper());
    }

    @Override
    public Boolean updateStatusOfRequest(RegisterRequest registerRequest) {
        System.out.println("[RegisterRequestDAOImpl]: Inside updateStatusOfRequest ...");
        return jdbcTemplate.update(
                SQL_UPDATE_STATUS_OF_REQUEST,
                registerRequest.getStatus(),
                registerRequest.getId()
        ) > 0;
    }
}
