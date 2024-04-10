package com.example.campus_portal_system.dept.beans;

public class Institute {

    private int institute_id;

    private String name;
    private String university;
    private String email_id;
    private String address;

    public Institute(int institute_id, String name, String university, String email_id, String address) {
        this.institute_id = institute_id;
        this.name = name;
        this.university = university;
        this.email_id = email_id;
        this.address = address;
    }

    public Institute() {
    }

    public int getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(int institute_id) {
        this.institute_id = institute_id;
    }


    public String getName() {
        return name;
    }

    public String getUniversity() {
        return university;
    }

    public String getEmail_id() {
        return email_id;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Institute{" +
                "institute_id=" + institute_id +
                ", name='" + name + '\'' +
                ", university='" + university + '\'' +
                ", email_id='" + email_id + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
