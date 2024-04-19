package com.example.campus_portal_system.utility.beans;

public enum UserTypes {

    ADMIN(1),
    INSTITUTE(2),
    INSTITUTE_ADMIN(3),
    HEAD_OF_DEPARTMENT(4),
    CLASS_TEACHER(5),
    HEAD_OF_DEPARTMENT_AND_CLASS_TEACHER(6),
    TEACHER(7),
    STUDENT(8);

    private int numVal;

    UserTypes(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }


}
