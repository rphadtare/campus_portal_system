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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterService {

    private EmailService emailService = new EmailService();

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

        //To check admin details for given institute id
        Admin admin = this.getAdminByInstituteId(teacher.getInstituteId());
        if(admin == null){
            //no admin is found for given institute.
            //Hence, request could not be complete further
            System.out.println("[RegisterService]: Admin details are not present for institute id " + admin.getInstituteId());

            //Sending email to institute regarding this error


            return false;
        }

        System.out.println("[RegisterService]: Admin details fetch successfully for institute id : " + admin.getInstituteId());

        // store register request details in database system
        RegisterRequest registerRequest =
                new RegisterRequest(1,"HOD Register",teacher.toString(), admin.getAdminTypeId(),admin.getAdminId(),"OPEN");

        boolean registerTeacherFlag = teacherDAO.createTeacher(teacher);
        boolean registerRequestFlag = this.storeRequestDetails(registerRequest);

        System.out.println("[RegisterService]: register teacher status : " +
                "" + registerTeacherFlag  + "" +
                "register request status " + registerRequestFlag);

        if(registerTeacherFlag && registerRequestFlag) {
            return true;
        } else {
            return false;
        }
    }








}
