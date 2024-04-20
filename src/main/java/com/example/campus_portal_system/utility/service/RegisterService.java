package com.example.campus_portal_system.utility.service;

import com.example.campus_portal_system.db.beans.DatabaseConfig;
import com.example.campus_portal_system.dept.beans.Department;
import com.example.campus_portal_system.dept.beans.Institute;
import com.example.campus_portal_system.dept.beans.dao.DepartmentDAO;
import com.example.campus_portal_system.dept.beans.dao.InstituteDAO;
import com.example.campus_portal_system.teacher.beans.Teacher;
import com.example.campus_portal_system.teacher.beans.dao.TeacherDAO;
import com.example.campus_portal_system.utility.beans.*;
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
        logger.info(" Inside storeRequestDetails to store " + request);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        RegisterRequestDAO registerRequestDAO = context.getBean(RegisterRequestDAO.class);
        return registerRequestDAO.createRequest(request);
    }

    public Boolean registerInstitute(Institute institute) {
        logger.info(" Inside registerInstitute ..." + institute);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        InstituteDAO instituteDAO = context.getBean(InstituteDAO.class);
        boolean result = false;
        result = instituteDAO.checkExist(institute);

        if(!result){

            result = instituteDAO.createInstitute(institute);
            if(result){
                //successfully registered and send email on institute id
                String message = EmailMessageUtil.fetchMessageTemplate("institute_onboard_success")
                        .replaceAll("institute",institute.getName());

                logger.info(" message for user: " + message);

                emailService.SendEmail(institute.getEmail_id(), "Onboarded successfully!!", message);

            } else {
                //successfully registered and send email on institute id
                String message = EmailMessageUtil.fetchMessageTemplate("institute_onboard_failed")
                        .replaceAll("institute",institute.getName());

                logger.info(" message for user: " + message);

                emailService.SendEmail(institute.getEmail_id(), "Onboarding failed!!", message);
                logger.severe("registerInstitute failed due to technical issue to register institute " + institute);
            }
        } else {
            logger.severe("registerInstitute failed to register as institute is already exist" + institute);
        }

        return  result;
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

            /****
             * check if admin is present for that institute or not
             * if yes then reject this registration request and send email to user stating admin exist already
             */

            if(adminDAO.checkIfAdminExistForInstitute(admin.getInstituteId())){
                logger.severe("registerAdmin - admin already exist for : " + institute.getName());

                //sending mail to user
                String message = EmailMessageUtil.fetchMessageTemplate("admin_already_exist")
                        .replaceAll("user",admin.getFullName())
                        .replaceAll("institute_name", institute.getName());

                logger.info(" message for user: " + message);

                emailService.SendEmail(admin.getEmailId(),
                        institute.getEmail_id(), "Admin registration failed !!", message);

                return false;
            }

            //random pass code which will be provided to institute to authenticate admin at later stage
            String passCode = Helper.getPassCode();

            RegisterRequest registerRequest =
                    new RegisterRequest(1,"Admin Register",
                            admin.toString() + "_passcode:" + passCode,
                            UserTypes.INSTITUTE.getNumVal(),institute.getInstitute_id(),
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


    private boolean registerHeadOfDepartment(AnnotationConfigApplicationContext context, TeacherDAO teacherDAO, Teacher teacher){

        //To check admin details for given institute id
        Admin admin = this.getAdminByInstituteId(teacher.getInstituteId());
        logger.info(" Inside registerHeadOfDepartment admin details received: " + admin + " for requester " + teacher);



        if(admin.getAdminId() == -1) {
            /***************************************************
             admin is not found for given institute.
             Hence, request could not be complete further
             ****************************************************/

            logger.severe("registerHeadOfDepartment- Admin details are not present for institute id " + teacher.getInstituteId());

            //Fetch email id from institute table
            InstituteDAO instituteDAO = context.getBean(InstituteDAO.class);
            Institute institute = instituteDAO.getInstituteById(teacher.getInstituteId());

            //Sending email to institute regarding this error
            String message = EmailMessageUtil.fetchMessageTemplate("hod_already_exist")
                    .replaceAll("user",teacher.getFullName())
                    .replaceAll("institute_name",institute.getName());

            logger.info("registerHeadOfDepartment- message for user: " + message);
            emailService.SendEmail(teacher.getEmailId(),
                    institute.getEmail_id(), "Unable to register !!", message);

            return false;
        }

        logger.info("registerHeadOfDepartment- Admin details fetch successfully for institute id : " + admin.getInstituteId());


        /*********************
         check if HOD is present for that institute and department
         if yes then reject this registration request and send email to user stating HOD exist already
         ***********************/

        if(teacherDAO.checkIfHODExist(teacher.getInstituteId(), teacher.getDepartmentId())){
            //get institute details and department details
            InstituteDAO instituteDAO = context.getBean(InstituteDAO.class);
            Institute institute = instituteDAO.getInstituteById(teacher.getInstituteId());

            DepartmentDAO departmentDAO = context.getBean(DepartmentDAO.class);
            Department department = departmentDAO.getDepartmentByID(teacher.getDepartmentId());

            logger.severe("registerHeadOfDepartment - HOD already exist for institute : "
                    + institute.getName() + " and department - " + department.getDepartmentName());

            //sending mail to user
            String message = EmailMessageUtil.fetchMessageTemplate("hod_already_exist")
                    .replaceAll("user",teacher.getFullName())
                    .replaceAll("department_name", department.getDepartmentName())
                    .replaceAll("institute_name", institute.getName());

            logger.info("registerHeadOfDepartment- message for user: " + message);

            emailService.SendEmail(admin.getEmailId(),
                    institute.getEmail_id(), "Admin registration failed !!", message);

            return false;
        }


        /**
         *  store register request details in database system
         */

        RegisterRequest registerRequest =
                new RegisterRequest(1,"HOD Register",teacher.toString(), admin.getAdminTypeId(),admin.getAdminId(),"OPEN");

        boolean registerTeacherFlag = teacherDAO.createTeacher(teacher);
        boolean registerRequestFlag = this.storeRequestDetails(registerRequest);

        logger.info("registerHeadOfDepartment - register HOD status : " +
                "" + registerTeacherFlag  + "" +
                "register request status " + registerRequestFlag);

        if(registerTeacherFlag && registerRequestFlag) {
            //send mail to user and cc corresponding approval
            String message = EmailMessageUtil.fetchMessageTemplate("registration")
                    .replaceAll("user",teacher.getFullName());
            logger.info("registerHeadOfDepartment - message for user: " + message);
            emailService.SendEmail(teacher.getEmailId(),
                    admin.getEmailId(), "Thanks for registration !!", message);

            return true;
        } else {
            String message = EmailMessageUtil.fetchMessageTemplate("registration_failed")
                    .replaceAll("user",teacher.getFullName());
            logger.info("registerHeadOfDepartment - message for user: " + message);
            emailService.SendEmail(teacher.getEmailId(),
                    admin.getEmailId(), "Thanks for registration !!", message);

            return false;
        }

    }



    public Boolean registerTeacher(Teacher teacher){
        /***
            To submit new request of teacher or head of department into system
         ***/

        logger.info(" Inside registerTeacher ... " + teacher);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        TeacherDAO teacherDAO = context.getBean(TeacherDAO.class);
        AdminDAO adminDAO = context.getBean(AdminDAO.class);
        InstituteDAO instituteDAO = context.getBean(InstituteDAO.class);
        DepartmentDAO departmentDAO = context.getBean(DepartmentDAO.class);

        if(teacher.getTeacherTypeId() == UserTypes.HEAD_OF_DEPARTMENT_AND_CLASS_TEACHER.getNumVal()){
            return this.registerHeadOfDepartment(context, teacherDAO, teacher);
        } else {

            /**
             * Check if HOD is present or not for approval.
             * If not then, reject request stating approval for teacher role is not present for that institute
             */


            if(teacherDAO.checkIfHODExist(teacher.getInstituteId(), teacher.getDepartmentId())){

                //Get approvals details
                Teacher headOfDepartment = teacherDAO.getHOD(teacher.getInstituteId(), teacher.getDepartmentId());


                RegisterRequest registerRequest =
                        new RegisterRequest(1,"Teacher Register",teacher.toString(), headOfDepartment.getTeacherTypeId(),
                                headOfDepartment.getTeacherId(),"OPEN");

                boolean registerTeacherFlag = teacherDAO.createTeacher(teacher);
                boolean registerRequestFlag = this.storeRequestDetails(registerRequest);

                logger.info("registerTeacher - register teacher status : " +
                        "" + registerTeacherFlag  + "" +
                        "register request status " + registerRequestFlag);

                if(registerTeacherFlag && registerRequestFlag) {
                    //send mail to user and cc corresponding approval
                    String message = EmailMessageUtil.fetchMessageTemplate("registration")
                            .replaceAll("user",teacher.getFullName());
                    logger.info("registerTeacher - message for user: " + message);
                    emailService.SendEmail(teacher.getEmailId(),
                            headOfDepartment.getEmailId(), "Thanks for registration !!", message);

                    return true;
                } else {
                    String message = EmailMessageUtil.fetchMessageTemplate("registration_failed")
                            .replaceAll("user",teacher.getFullName());
                    logger.info("registerTeacher - message for user: " + message);
                    emailService.SendEmail(teacher.getEmailId(),
                            headOfDepartment.getEmailId(), "Thanks for registration !!", message);

                    return false;
                }

            } else {

                logger.severe("registerTeacher- HOD details are not present for institute id " + teacher.getInstituteId());
                Institute institute = instituteDAO.getInstituteById(teacher.getInstituteId());
                Department department = departmentDAO.getDepartmentByID(teacher.getDepartmentId());

                String message = EmailMessageUtil.fetchMessageTemplate("hod_not_exist")
                        .replaceAll("user",teacher.getFullName())
                        .replaceAll("department_name",department.getDepartmentName())
                        .replaceAll("institute_name",institute.getName());
                logger.info("registerTeacher - message for user: " + message);
                emailService.SendEmail(teacher.getEmailId(),
                        institute.getEmail_id(), "Unable to register !!", message);

                return false;

            }


        }

    }







}
