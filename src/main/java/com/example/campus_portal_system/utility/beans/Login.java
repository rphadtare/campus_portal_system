package com.example.campus_portal_system.utility.beans;

public class Login {
    private int id, userType;
    private String userName, userPassword;

    private int isDeleted;


    public Login(int id, int userType, String userName, String userPassword, int isDeleted) {
        this.id = id;
        this.userType = userType;
        this.userName = userName;
        this.userPassword = userPassword;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public int getUserType() {
        return userType;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                ", userType=" + userType +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
