package com.example.campus_portal_system.utility.beans;

public class UserType {

    private int id;
    private String userType;


    public UserType(int id, String userType) {
        this.id = id;
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public String getUserType() {
        return userType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "id=" + id +
                ", userType='" + userType + '\'' +
                '}';
    }
}
