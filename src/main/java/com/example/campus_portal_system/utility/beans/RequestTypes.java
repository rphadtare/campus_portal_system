package com.example.campus_portal_system.utility.beans;

public enum RequestTypes {

    INSTITUTE_ADMIN_REGISTER("Admin Register"),
    HEAD_OF_DEPARTMENT_REGISTER("HOD Register"),
    TEACHER_REGISTER("Teacher Register"),
    STUDENT_REGISTER("Student Register");

    private String requestTypeValue;

    RequestTypes(String requestTypeValue) {
        this.requestTypeValue = requestTypeValue;
    }

    public String getRequestTypeValue() {
        return requestTypeValue;
    }

}
