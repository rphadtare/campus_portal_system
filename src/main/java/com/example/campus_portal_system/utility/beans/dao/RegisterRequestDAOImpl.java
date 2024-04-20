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
            "where approver_type_id = ? and approver_id = ? and request_type = ? and status = ?";

    public final String SQL_UPDATE_STATUS_OF_REQUEST = "update register_request" +
            "set" +
            "status = ?" +
            "where id = ?";

    public final String SQL_CREATE_REQUEST = "insert into register_request" +
            "(request_type,request_information,approver_type_id,approver_id,status)" +
            "values(?,?,?,?,?)";

    @Override
    public List<RegisterRequest> getRequests(int userTypeIdForApproval, int userIdForApproval, String requestType, String status) {
        logger.info("Inside getRequests for approval type : " + userTypeIdForApproval
                + " approval id: " + userIdForApproval + " request type: " + requestType + " status: " + status);
        List<RegisterRequest> registerRequests = null;

        try {
            registerRequests = jdbcTemplate.query(SQL_GET_REQUESTS_BY_APPROVALS_ID_AND_TYPE,
                    new Object[]{userTypeIdForApproval, userIdForApproval, requestType, status},
                    new RegisterRequestMaper());
        } catch (Exception e){
            logger.severe("Exception occurred in getRequests for approval type : "
                    + userTypeIdForApproval + " approval id: " + userIdForApproval + " request type: " + requestType + " status: " + status
                    + " and exception - " + e.getMessage());
        }
        return registerRequests;
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
