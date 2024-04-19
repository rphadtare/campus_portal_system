package com.example.campus_portal_system.utility.beans.dao;

import com.example.campus_portal_system.utility.beans.RegisterRequest;
import com.example.campus_portal_system.utility.beans.mapper.RegisterRequestMaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.logging.Logger;

@Component
public class RegisterRequestDAOImpl implements  RegisterRequestDAO {

    JdbcTemplate jdbcTemplate;

    private Logger logger;

    @Autowired
    public RegisterRequestDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        logger = Logger.getLogger(RegisterRequestDAOImpl.class.getName());
    }

    public final String SQL_GET_REQUESTS_BY_APPROVALS_ID_AND_TYPE = "select * from register_request " +
            "where approver_type_id = ? and approver_id = ?";

    public final String SQL_UPDATE_STATUS_OF_REQUEST = "update register_request" +
            "set" +
            "status = ?" +
            "where id = ?";

    public final String SQL_CREATE_REQUEST = "insert into register_request" +
            "(request_type,request_information,approver_type_id,approver_id,status)" +
            "values(?,?,?,?,?)";

    @Override
    public List<RegisterRequest> getRequests(int userTypeIdForApproval, int userIdForApproval) {
        logger.info("Inside getRequests ...");
        return jdbcTemplate.query(SQL_GET_REQUESTS_BY_APPROVALS_ID_AND_TYPE,
                new Object[]{userTypeIdForApproval, userIdForApproval},
                new RegisterRequestMaper());
    }

    @Override
    public Boolean updateStatusOfRequest(RegisterRequest registerRequest) {
        logger.info("Inside updateStatusOfRequest ...");
        return jdbcTemplate.update(
                SQL_UPDATE_STATUS_OF_REQUEST,
                registerRequest.getStatus(),
                registerRequest.getId()
        ) > 0;
    }

    @Override
    public Boolean createRequest(RegisterRequest registerRequest) {
        logger.info("Inside updateStatusOfRequest ...");
        return jdbcTemplate.update(
                SQL_CREATE_REQUEST,
                registerRequest.getrequestType(),
                registerRequest.getRequestInformation(),
                registerRequest.getApproveByTypeId(),
                registerRequest.getApproveById(),
                registerRequest.getStatus()
        ) > 0;
    }
}
