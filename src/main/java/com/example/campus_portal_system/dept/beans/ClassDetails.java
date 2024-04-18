package com.example.campus_portal_system.dept.beans;

public class ClassDetails {

    private int id;
    private String className;

    private int instituteId, departmentId, classTeacherId;

    private byte[] timetableImage;

    public ClassDetails(int id, String className, int instituteId, int departmentId, int classTeacherId, byte[] timetableImage) {
        this.id = id;
        this.className = className;
        this.instituteId = instituteId;
        this.departmentId = departmentId;
        this.classTeacherId = classTeacherId;
        this.timetableImage = timetableImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(int instituteId) {
        this.instituteId = instituteId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getClassTeacherId() {
        return classTeacherId;
    }

    public void setClassTeacherId(int classTeacherId) {
        this.classTeacherId = classTeacherId;
    }

    public byte[] getTimetableImage() {
        return timetableImage;
    }

    public void setTimetableImage(byte[] timetableImage) {
        this.timetableImage = timetableImage;
    }

    @Override
    public String toString() {
        return "ClassDetails{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", instituteId=" + instituteId +
                ", departmentId=" + departmentId +
                ", classTeacherId=" + classTeacherId +
                '}';
    }
}
