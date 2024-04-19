package com.example.campus_portal_system.teacher.service;

import com.example.campus_portal_system.db.beans.DatabaseConfig;
import com.example.campus_portal_system.teacher.beans.Teacher;
import com.example.campus_portal_system.teacher.beans.dao.TeacherDAO;
import com.example.campus_portal_system.utility.service.EmailService;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Bool;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class TeacherService {

    private EmailService emailService;
    private Logger logger;

    TeacherService(){
        this.emailService = new EmailService();
        logger = Logger.getLogger(TeacherService.class.getName());
    }

    public Boolean updateTeacherInfo(Teacher teacher){
        /*
            To update teacher basic information
         */

        logger.info("Inside updateTeacherInfo ...");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        TeacherDAO teacherDAO = context.getBean(TeacherDAO.class);
        return teacherDAO.updateTeacherInfo(teacher);

    }

    public List<Teacher> getTeachers(int teacher_type, int institute_id){
        /*
            To get list of teachers as required from system
         */

        logger.info("Inside getTeachers ...");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        TeacherDAO teacherDAO = context.getBean(TeacherDAO.class);
        List<Teacher> teacherList = null;

        switch (teacher_type){
            case 1:
                logger.info("Getting all teachers for institute : " + institute_id);
                teacherList = teacherDAO.getAllTeachers();
                break;

            case 2:
                logger.info("Getting all teachers for institute : " + institute_id);
                teacherList = teacherDAO.getAllTeachers(institute_id);
                break;

            case 3:
                logger.info("Getting all head of departments for institute : " + institute_id);
                teacherList = teacherDAO.getAllHOD(institute_id);
                break;

            case 4:
                logger.info("Getting all class teachers for institute : " + institute_id);
                teacherList = teacherDAO.getAllClassTeachers(institute_id);
                break;
        }

        return teacherList;
    }

    public Teacher getTeacher(int institute_id, int teacher_id){
        /*
            To get specific teacher from system
         */

        logger.info("Inside getTeacher ...");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        TeacherDAO teacherDAO = context.getBean(TeacherDAO.class);
        return teacherDAO.getTeacherById(institute_id, teacher_id);

    }

    public Boolean deleteTeacher(int institute_id, int teacher_id){
        /*
            To delete specific teacher info from system
         */

        logger.info("Inside deleteTeacher using deleteTeacherById ...");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        TeacherDAO teacherDAO = context.getBean(TeacherDAO.class);
        return teacherDAO.deleteTeacherById(institute_id, teacher_id);

    }

    public Boolean deleteTeacher(Teacher teacher){
        /*
            To delete specific teacher info from system
         */

        logger.info("Inside deleteTeacher ...");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        TeacherDAO teacherDAO = context.getBean(TeacherDAO.class);
        return teacherDAO.deleteTeacher(teacher);

    }

    public Boolean activateTeacher(int institute_id, int teacher_id){
        /*
            To retrieve soft-delete teacher record from system
         */

        logger.info("[RegisterService]: Inside activateTeacher ...");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        TeacherDAO teacherDAO = context.getBean(TeacherDAO.class);
        return teacherDAO.activateTeacher(institute_id, teacher_id);

    }

    public Boolean activateTeacher(Teacher teacher){
        /*
            To retrieve soft-delete teacher record from system
         */

        logger.info("[RegisterService]: Inside activateTeacher ...");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        TeacherDAO teacherDAO = context.getBean(TeacherDAO.class);
        return teacherDAO.activateTeacher(teacher);

    }

    public Boolean assignClassTeacher(){
        /*
            To assign class teacher to specific class
         */


        return true;
    }

    public Boolean setTimeTable() {
        /*
            To set timetable of specific class
         */

        return true;
    }


    public Boolean sendNotifications(){
        /*
            To send notifications to recipient list
         */

        return true;
    }

    public Boolean submitAttendance(){
        /*
            To submit attendance of students of current session
         */

        return true;
    }




}
