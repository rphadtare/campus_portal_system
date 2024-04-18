package com.example.campus_portal_system.utility.service;

import com.example.campus_portal_system.CampusPortalSystemApplication;
import com.example.campus_portal_system.db.beans.DatabaseConfig;
import com.example.campus_portal_system.dept.beans.Institute;
import com.example.campus_portal_system.dept.beans.dao.InstituteDAO;
import com.example.campus_portal_system.teacher.beans.Teacher;
import com.example.campus_portal_system.teacher.beans.dao.TeacherDAO;
import com.example.campus_portal_system.utility.beans.Admin;
import com.example.campus_portal_system.utility.beans.RegisterRequest;
import com.example.campus_portal_system.utility.beans.dao.AdminDAO;
import com.example.campus_portal_system.utility.beans.dao.RegisterRequestDAO;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Bool;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterService {

    public List<Institute> getAllDepartments(){
        System.out.println("[RegisterService]: Inside getAllDepartments ...");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);

        InstituteDAO instituteDAO = context.getBean(InstituteDAO.class);
        List<Institute> instituteList = instituteDAO.getAllInstitute();
        System.out.println("[RegisterService]: List of institute is - ");
        for (Institute i : instituteList) {
            System.out.println(i);
        }

        return instituteList;
    }

    public Admin getAdminByInstituteId(int instituteId){
        /*
            To get admin details of specific institute
         */

        System.out.println("[RegisterService]: Inside getAdminByInstituteId ...");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        AdminDAO adminDAO = context.getBean(AdminDAO.class);
        return adminDAO.getAdminInfo(instituteId);
    }

    public Boolean storeRequestDetails(RegisterRequest request){
        /*
            To store specific register request which holds information
            mainly -
            Request type - Student, Teacher or HOD
            Institute ID
            Admin approval id
         */
        System.out.println("[RegisterService]: Inside storeRequestDetails ...");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        RegisterRequestDAO registerRequestDAO = context.getBean(RegisterRequestDAO.class);
        return registerRequestDAO.createRequest(request);
    }


    public Boolean registerTeacher(Teacher teacher){
        /*
            To submit new request of teacher or head of department into submit
         */

        System.out.println("[RegisterService]: Inside registerTeacher ...");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        TeacherDAO teacherDAO = context.getBean(TeacherDAO.class);
        return teacherDAO.createTeacher(teacher);

    }






}
