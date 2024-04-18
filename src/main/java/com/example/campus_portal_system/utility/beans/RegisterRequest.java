package com.example.campus_portal_system.utility.beans;

public class RegisterRequest {

    private int id;

    private String requestType;

    private String requestInformation;

    private int approveByTypeId, approveById;

    private String status;

    public RegisterRequest(int id, String requestType, String requestInformation, int approveByTypeId, int approveById, String status) {
        this.id = id;
        this.requestType = requestType;
        this.requestInformation = requestInformation;
        this.approveByTypeId = approveByTypeId;
        this.approveById = approveById;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getrequestType() {
        return requestType;
    }

    public void setrequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestInformation() {
        return requestInformation;
    }

    public void setRequestInformation(String requestInformation) {
        this.requestInformation = requestInformation;
    }

    public int getApproveByTypeId() {
        return approveByTypeId;
    }

    public void setApproveByTypeId(int approveByTypeId) {
        this.approveByTypeId = approveByTypeId;
    }

    public int getApproveById() {
        return approveById;
    }

    public void setApproveById(int approveById) {
        this.approveById = approveById;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "id=" + id +
                ", requestType='" + requestType + '\'' +
                ", requestInformation='" + requestInformation + '\'' +
                ", approveByTypeId=" + approveByTypeId +
                ", approveById=" + approveById +
                ", status='" + status + '\'' +
                '}';
    }
}
