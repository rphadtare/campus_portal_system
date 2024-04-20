package com.example.campus_portal_system.utility.beans.dao;

import com.example.campus_portal_system.utility.beans.RegisterRequest;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Bool;

import java.util.List;

public interface RegisterRequestDAO {

    public List<RegisterRequest> getRequests(int userTypeIdForApproval, int userIdForApproval, String requestType, String status );

    public Boolean updateStatusOfRequest(RegisterRequest registerRequest);

    public Boolean createRequest(RegisterRequest registerRequest);

}
