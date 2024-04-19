package com.example.campus_portal_system.teacher.beans;

public class Teacher {

    private int teacherId;

    private int instituteId;

    private int departmentId;

    private int teacherTypeId;

    private String salutations;

    private String firstName, middleName, lastName;

    private String qualifications;

    private String emailId;

    private String contactNo;

    private int isDeleted;

    public Teacher(int teacherId, int instituteId, int departmentId, int teacherTypeId, String salutations, String firstName, String middleName, String lastName, String qualifications, String emailId, String contactNo, int isDeleted) {
        this.teacherId = teacherId;
        this.instituteId = instituteId;
        this.departmentId = departmentId;
        this.teacherTypeId = teacherTypeId;
        this.salutations = salutations;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.qualifications = qualifications;
        this.emailId = emailId;
        this.contactNo = contactNo;
        this.isDeleted = isDeleted;
    }

    public Teacher(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public int getInstituteId() {
        return instituteId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public int getTeacherTypeId() {
        return teacherTypeId;
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

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public void setInstituteId(int instituteId) {
        this.instituteId = instituteId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public void setTeacherTypeId(int teacherTypeId) {
        this.teacherTypeId = teacherTypeId;
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

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", instituteId=" + instituteId +
                ", departmentId=" + departmentId +
                ", teacherTypeId=" + teacherTypeId +
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

    public String getFullName(){
        return getSalutations() + " " + getFirstName() + " " + getLastName();
    }

    public String getTeacherType() {
        String result = "NA";
        switch(this.getTeacherTypeId()){

            case 2:
                result = "Head Of Department";
                break;

            case 3:
                result = "Class Teacher";
                break;

            case 4:
                result = "Head Of Department and Class Teacher";
                break;

            case 5:
                result = "Teacher";
                break;

        }

        return result;
    }

}
