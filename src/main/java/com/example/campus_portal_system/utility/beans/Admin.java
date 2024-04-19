package com.example.campus_portal_system.utility.beans;

public class Admin {

    private int adminId, instituteId, adminTypeId;

    private String salutations;

    private String firstName, middleName, lastName;

    private String qualifications;

    private String emailId;

    private String contactNo;

    private int isDeleted;

    public Admin(int adminId, int instituteId, int adminTypeId, String salutations, String firstName, String middleName, String lastName, String qualifications, String emailId, String contactNo, int isDeleted) {
        this.adminId = adminId;
        this.instituteId = instituteId;
        this.adminTypeId = adminTypeId;
        this.salutations = salutations;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.qualifications = qualifications;
        this.emailId = emailId;
        this.contactNo = contactNo;
        this.isDeleted = isDeleted;
    }

    public Admin(int adminId) {
        this.adminId = adminId;
    }

    public int getAdminId() {
        return adminId;
    }

    public int getAdminTypeId() {
        return adminTypeId;
    }

    public String getSalutations() {
        return salutations;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getQualifications() {
        return qualifications;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getContactNo() {
        return contactNo;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public void setAdminTypeId(int adminTypeId) {
        this.adminTypeId = adminTypeId;
    }

    public void setSalutations(String salutations) {
        this.salutations = salutations;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }


    public int getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(int instituteId) {
        this.instituteId = instituteId;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", instituteId=" + instituteId +
                ", adminTypeId=" + adminTypeId +
                ", salutations='" + salutations + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", qualifications='" + qualifications + '\'' +
                ", emailId='" + emailId + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
