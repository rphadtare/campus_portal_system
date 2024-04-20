package com.example.campus_portal_system.teacher.beans.dao;

import com.example.campus_portal_system.teacher.beans.Teacher;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Bool;

import java.util.List;

public interface TeacherDAO {

    List<Teacher> getAllTeachers();

    List<Teacher> getAllTeachers(int institute_id);

    List<Teacher> getAllHOD(int institute_id);

    Boolean checkIfHODExist(int institute_id, int department_id);

    Teacher getHOD(int institute_id, int department_id);

    List<Teacher> getAllClassTeachers(int institute_id);

    Teacher getTeacherById(int institute_id, int teacher_id);

    Teacher getTeacherById(int teacher_id);

    Teacher getTeacherByEmail(int institute_id, int department_id, String emailId);

    Boolean updateTeacherInfo(Teacher teacher);

    Boolean deleteTeacher(Teacher teacher);

    Boolean deleteTeacherById(int institute_id, int teacher_id);

    Boolean createTeacher(Teacher teacher);

    Boolean activateTeacher(Teacher teacher);

    Boolean activateTeacher(int institute_id, int teacher_id);



}
