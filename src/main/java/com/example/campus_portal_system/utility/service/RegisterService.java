package com.example.campus_portal_system.utility.service;

import com.example.campus_portal_system.db.beans.DatabaseConfig;
import com.example.campus_portal_system.dept.beans.Institute;
import com.example.campus_portal_system.dept.beans.dao.InstituteDAO;
import com.example.campus_portal_system.teacher.beans.Teacher;
import com.example.campus_portal_system.teacher.beans.dao.TeacherDAO;
import com.example.campus_portal_system.utility.beans.Admin;
import com.example.campus_portal_system.utility.beans.EmailMessageUtil;
import com.example.campus_portal_system.utility.beans.Helper;
import com.example.campus_portal_system.utility.beans.RegisterRequest;
import com.example.campus_portal_system.utility.beans.dao.AdminDAO;
import com.example.campus_portal_system.utility.beans.dao.RegisterRequestDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class RegisterService {

    private EmailService emailService = new EmailService();

    private Logger logger = Logger.getLogger(RegisterService.class.getName());

    public List<Institute> getAllDepartments(){
        logger.info(" Inside getAllDepartments ...");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);

        InstituteDAO instituteDAO = context.getBean(InstituteDAO.class);
        List<Institute> instituteList = instituteDAO.getAllInstitute();
        logger.info(" List of institute is - ");
        for (Institute i : instituteList) {
            logger.info(String.valueOf(i));
        }

        return instituteList;
    }

    public Admin getAdminByInstituteId(int instituteId){
        /*
            To get admin details of specific institute
         */

        logger.info(" Inside getAdminByInstituteId ...");
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
        logger.info(" Inside storeRequestDetails ...");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        RegisterRequestDAO registerRequestDAO = context.getBean(RegisterRequestDAO.class);
        return registerRequestDAO.createRequest(request);
    }

    public Boolean registerInstitute(Institute institute) {

        return false;
    }

    public Boolean registerAdmin(Admin admin){
        logger.info(" Inside registerAdmin to register : " + admin);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        AdminDAO adminDAO = context.getBean(AdminDAO.class);
        RegisterRequestDAO registerRequestDAO = context.getBean(RegisterRequestDAO.class);
        InstituteDAO instituteDAO = context.getBean(InstituteDAO.class);

        //Fetch email id from institute table
        Institute institute = instituteDAO.getInstituteById(admin.getInstituteId());
        logger.info("registerAdmin - fetched institute details " + institute);

        if(institute.getInstitute_id() != -1){
            //random pass code which will be provided to institute to authenticate admin at later stage
            String passCode = Helper.getPassCode();

            RegisterRequest registerRequest =
                    new RegisterRequest(1,"Admin Register",
                            admin.toString() + "_passcode:" + passCode,
                            admin.getAdminTypeId(),institute.getInstitute_id(),
                            "OPEN");

            Boolean adminCreateResult = adminDAO.createAdmin(admin);
            Boolean registerRequestResult = registerRequestDAO.createRequest(registerRequest);


            //send email of registration to user and institute
            if(adminCreateResult && registerRequestResult){

                //to get admin id from system
                Admin updatedAdmin = adminDAO.getAdminInfo(institute.getInstitute_id(), admin.getEmailId());

                //send mail to user and cc corresponding approval
                String message = EmailMessageUtil.fetchMessageTemplate("registration")
                        .replaceAll("user",admin.getFullName());

                logger.info(" message for user: " + message);

                emailService.SendEmail(admin.getEmailId(),
                        institute.getEmail_id(), "Thanks for registration !!", message);

                //sending one more email to institute with passcode to authenticate admin request at later stage
                message = EmailMessageUtil.fetchMessageTemplate("admin_passcode")
                        .replaceAll("collegeName",institute.getName())
                        .replaceAll("Requester_user_name", admin.getFullName())
                        .replaceAll("email_id", admin.getEmailId())
                        .replaceAll("contact_no",admin.getContactNo())
                        .replaceAll("pass_code",passCode)
                        .replaceAll("admin_id", String.valueOf(updatedAdmin.getAdminId()));

                logger.info(" message for user: " + message);

                emailService.SendEmail(institute.getEmail_id(), "Notification for admin handle request !!", message);

                return true;
            }
            //send email of unsuccessful registration to user and institute
            else {
                String message = EmailMessageUtil.fetchMessageTemplate("registration_failed")
                        .replaceAll("user",admin.getFirstName() + " " + admin.getLastName());

                logger.info(" message for user: " + message);
                emailService.SendEmail(admin.getEmailId(),
                        institute.getEmail_id(), "Admin registration failed !!", message);

                return false;
            }
        } else {
            logger.severe("registerAdmin - no institute present for id: " + admin.getInstituteId());
            return false;
        }
    }

    public Boolean registerTeacher(Teacher teacher){
        /*
            To submit new request of teacher or head of department into submit
         */

        logger.info(" Inside registerTeacher ... " + teacher);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        TeacherDAO teacherDAO = context.getBean(TeacherDAO.class);

        //To check admin details for given institute id
        Admin admin = this.getAdminByInstituteId(teacher.getInstituteId());
        logger.info(" Inside registerTeacher admin details received: " + admin);

        if(admin.getAdminId() == -1){
            //no admin is found for given institute.
            //Hence, request could not be complete further
            logger.info(" Admin details are not present for institute id " + teacher.getInstituteId());

            //Fetch email id from institute table
            InstituteDAO instituteDAO = context.getBean(InstituteDAO.class);
            Institute institute = instituteDAO.getInstituteById(teacher.getInstituteId());

            //Sending email to institute regarding this error
            String message = EmailMessageUtil.fetchMessageTemplate("admin_not_present")
                    .replaceAll("user",teacher.getFirstName() + " " + teacher.getLastName())
                    .replaceAll("institute_name",institute.getName());

            logger.info(" message for user: " + message);
            emailService.SendEmail(teacher.getEmailId(),
                    institute.getEmail_id(), "Unable to register !!", message);

            return false;
        }

        logger.info(" Admin details fetch successfully for institute id : " + admin.getInstituteId());

        // store register request details in database system
        RegisterRequest registerRequest =
                new RegisterRequest(1,"HOD Register",teacher.toString(), admin.getAdminTypeId(),admin.getAdminId(),"OPEN");

        boolean registerTeacherFlag = teacherDAO.createTeacher(teacher);
        boolean registerRequestFlag = this.storeRequestDetails(registerRequest);

        logger.info(" register teacher status : " +
                "" + registerTeacherFlag  + "" +
                "register request status " + registerRequestFlag);

        if(registerTeacherFlag && registerRequestFlag) {
            //send mail to user and cc corresponding approval
            String message = EmailMessageUtil.fetchMessageTemplate("registration")
                    .replaceAll("user",teacher.getFirstName() + " " + teacher.getLastName());
            logger.info(" message for user: " + message);
            emailService.SendEmail(teacher.getEmailId(),
                    admin.getEmailId(), "Thanks for registration !!", message);

            return true;
        } else {
            String message = EmailMessageUtil.fetchMessageTemplate("registration_failed")
                    .replaceAll("user",teacher.getFirstName() + " " + teacher.getLastName());
            logger.info(" message for user: " + message);
            emailService.SendEmail(teacher.getEmailId(),
                    admin.getEmailId(), "Thanks for registration !!", message);

            return false;
        }
    }








}
