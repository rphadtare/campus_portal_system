package com.example.campus_portal_system.dept.beans.dao;

import com.example.campus_portal_system.dept.beans.ClassDetails;

import java.util.List;

public interface ClassDetailsDAO {

    public List<ClassDetails> getClassDetailsByInstituteAndDepartmentId(int instituteId, int departmentId);

    public Boolean setTimetable(int id, int instituteId, int departmentId, byte[] newTimeTable);

    public Boolean setClassTeacher(int instituteId, int departmentId, int classTeacherId);

}
