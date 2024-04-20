package com.example.campus_portal_system.utility.service;

import com.example.campus_portal_system.db.beans.DatabaseConfig;
import com.example.campus_portal_system.dept.beans.Institute;
import com.example.campus_portal_system.dept.beans.dao.DepartmentDAO;
import com.example.campus_portal_system.dept.beans.dao.InstituteDAO;
import com.example.campus_portal_system.teacher.beans.Teacher;
import com.example.campus_portal_system.teacher.beans.dao.TeacherDAO;
import com.example.campus_portal_system.utility.beans.*;
import com.example.campus_portal_system.utility.beans.dao.AdminDAO;
import com.example.campus_portal_system.utility.beans.dao.LoginDAO;
import com.example.campus_portal_system.utility.beans.dao.RegisterRequestDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class LoginService {

    private EmailService emailService;
    private Logger logger;

    LoginService(){
        this.logger = Logger.getLogger(LoginService.class.getName());
        this.emailService =  new EmailService();
    }

    public boolean validateUser(String userid, String password, int usertype) {

        logger.info("Inside validateUser ...");
        boolean result = true;


        switch (usertype){
            case 1:
                result = userid.equalsIgnoreCase("rutu")
                            && password.equalsIgnoreCase("Rutu@123$");
                break;

            default:
                result = false;
                break;
        }

        return result;

    }

    public boolean adminRegisterRequestAuthorization(int adminId, int instituteId, String approvalStatus, String passCode){
        logger.info("inside adminRegisterRequestAuthorization to " + approvalStatus + " request for admin id - " + adminId);

        //get register request details of admin id
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        RegisterRequestDAO registerRequestDAO = context.getBean(RegisterRequestDAO.class);
        AdminDAO adminDAO = context.getBean(AdminDAO.class);


        List<RegisterRequest> allRegisterRequest = registerRequestDAO.
                getRequests(UserTypes.INSTITUTE.getNumVal(),
                        instituteId,
                        RequestTypes.INSTITUTE_ADMIN_REGISTER.getRequestTypeValue(), approvalStatus);

        Admin admin = adminDAO.getAdminInfoByAdminId(adminId);

        if(admin.getAdminId() == -1){
            logger.severe("adminRegisterRequestAuthorization to " + approvalStatus + " request for admin id - "
                    + adminId + " failed as unable to fetch admin information from admin table from database");
            return false;
        }

        if(allRegisterRequest == null){
            logger.severe("adminRegisterRequestAuthorization to " + approvalStatus + " request for admin id - "
                    + adminId + " failed as unable to fetch any admin request information given id");
            return false;
        }

        String searchInformation = admin.toString() + "_passcode:" + passCode;

        List<RegisterRequest> filteredRequestList =  allRegisterRequest.stream().filter(r -> r.getRequestInformation().equalsIgnoreCase(searchInformation))
                .collect(Collectors.toList());

        if(filteredRequestList == null || filteredRequestList.size() == 0 || filteredRequestList.size() > 1){
            logger.severe("adminRegisterRequestAuthorization to " + approvalStatus + " request for admin id - "
                    + adminId + " failed as unable to find specific admin request information from register_request table from database");
            return false;
        } else {

            LoginDAO loginDAO = context.getBean(LoginDAO.class);
            InstituteDAO instituteDAO = context.getBean(InstituteDAO.class);
            Institute institute = instituteDAO.getInstituteById(instituteId);

            //Get first register request
            RegisterRequest registerRequest = filteredRequestList.get(0);

            registerRequest.setStatus(approvalStatus);

            //updating latest status in database first
            if(registerRequestDAO.updateStatusOfRequest(registerRequest)){
                logger.info("adminRegisterRequestAuthorization to " + approvalStatus + " request for admin id - " + adminId + " updated in register request !!");
            } else {
                logger.severe("adminRegisterRequestAuthorization to " + approvalStatus + " request for admin id - " + adminId + " failed while updating in register request !!");
                return false;
            }

            if(approvalStatus == "APPROVED"){

                //create username and password
                String userName = "";
                String passWord = "";
                boolean flag = true;

                while(flag){

                    //to check if username doesn't exist already in login table

                    userName = admin.getFirstName().substring(0,4) + admin.getLastName().substring(0,4)
                            + "_" + Helper.getPassCode(3);

                    passWord = Helper.getPassCode(12);

                    flag = loginDAO.checkUserNameExist(userName);

                }

                // create login for admin
                Login login = new Login(1, UserTypes.INSTITUTE_ADMIN.getNumVal(), userName, passWord, 0);

                if(loginDAO.createLogin(login)){
                    logger.info("adminRegisterRequestAuthorization - login created successfully for " + admin
                            + " with login userName: " + login.getUserName());

                    //send email of approval to user and institute
                    String message = EmailMessageUtil.fetchMessageTemplate("login")
                            .replaceAll("user",admin.getFullName())
                            .replaceAll("request_type",RequestTypes.INSTITUTE_ADMIN_REGISTER.getRequestTypeValue());

                    logger.info("registerAdmin - approval message for user: " + message);

                    emailService.SendEmail(admin.getEmailId(),
                            institute.getEmail_id(), "Notification - registration request approved !!", message);


                    //send email to user with username and password
                    message = EmailMessageUtil.fetchMessageTemplate("authorization")
                            .replaceAll("user",admin.getFullName())
                            .replaceAll("user_name",userName)
                            .replaceAll("pass_word",passWord);

                    logger.info("registerAdmin - login credentials message for user: " + message);

                    emailService.SendEmail(admin.getEmailId(), "Credentials for login !!", message);

                    return true;

                } else {
                    logger.severe("adminRegisterRequestAuthorization to " + approvalStatus + " request for admin id - "
                            + adminId + " failed to create login");
                    return false;
                }

            } else {

                //send mail of denial to requester
                String message = EmailMessageUtil.fetchMessageTemplate("registration_request_deny")
                        .replaceAll("user",admin.getFullName())
                        .replaceAll("request_type",RequestTypes.INSTITUTE_ADMIN_REGISTER.getRequestTypeValue());

                logger.info("adminRegisterRequestAuthorization - denial message for user: " + message);

                emailService.SendEmail(admin.getEmailId(),
                        institute.getEmail_id(), "Notification - registration request denied !!", message);

                return false;
            }

        }
    }


    public boolean hodRegisterRequestAuthorization(int adminId, int teacherId, String approvalStatus){
        logger.info("inside hodRegisterRequestAuthorization to " + approvalStatus + " request for HOD ID - " + teacherId);

        //get register request details of admin id
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        RegisterRequestDAO registerRequestDAO = context.getBean(RegisterRequestDAO.class);
        AdminDAO adminDAO = context.getBean(AdminDAO.class);
        TeacherDAO teacherDAO = context.getBean(TeacherDAO.class);

        Admin admin = adminDAO.getAdminInfoByAdminId(adminId);

        if(admin.getAdminId() == -1){
            logger.severe("hodRegisterRequestAuthorization to " + approvalStatus + " request for HOD ID - "
                    + teacherId + " failed as admin id does not exist : " + adminId);
            return false;
        }

        Teacher hod = teacherDAO.getTeacherById(admin.getInstituteId(), teacherId);
        if(hod.getTeacherId() == -1){
            logger.severe("hodRegisterRequestAuthorization to " + approvalStatus + " request for HOD ID - "
                    + teacherId + " failed as teacher does not exist for institute id : " + admin.getInstituteId() + " and teacher_id " + teacherId);
            return false;
        }

        List<RegisterRequest> allRegisterRequest = registerRequestDAO.
                getRequests(UserTypes.INSTITUTE_ADMIN.getNumVal(),
                        adminId,
                        RequestTypes.HEAD_OF_DEPARTMENT_REGISTER.getRequestTypeValue(), approvalStatus);

        if(allRegisterRequest == null){
            logger.severe("hodRegisterRequestAuthorization to " + approvalStatus + " request for HOD ID - "
                    + teacherId + " failed as unable to fetch any HOD register request information of given id");
            return false;
        }

        String searchInformation = hod.toString();

        List<RegisterRequest> filteredRequestList =  allRegisterRequest.stream().filter(r -> r.getRequestInformation().equalsIgnoreCase(searchInformation))
                .collect(Collectors.toList());

        if(filteredRequestList == null || filteredRequestList.size() == 0 || filteredRequestList.size() > 1){
            logger.severe("hodRegisterRequestAuthorization to " + approvalStatus + " request for HOD ID - "
                    + teacherId + " failed as unable to find specific HDO register request information from register_request table from database");
            return false;
        } else {

            LoginDAO loginDAO = context.getBean(LoginDAO.class);
            //Get first register request
            RegisterRequest registerRequest = filteredRequestList.get(0);

            registerRequest.setStatus(approvalStatus);
            hod.setIsDeleted(0);


            boolean registerRequestUpdateFlag = registerRequestDAO.updateStatusOfRequest(registerRequest);
            boolean hodUpdateFlag = teacherDAO.activateTeacher(hod);

            //updating latest status in database first
            if(registerRequestUpdateFlag && hodUpdateFlag){
                logger.info("hodRegisterRequestAuthorization to " + approvalStatus + " request for HOD ID - "
                        + teacherId + " updated in register request and teacher table!!");
            } else {
                logger.info("hodRegisterRequestAuthorization to " + approvalStatus + " request for HOD ID - "
                        + teacherId + " failed to update in register request and teacher table!!");
                return false;
            }

            if(approvalStatus == "APPROVED"){

                //create username and password
                String userName = "";
                String passWord = "";
                boolean flag = true;

                while(flag){

                    //to check if username doesn't exist already in login table

                    userName = hod.getFirstName().substring(0,4) + hod.getLastName().substring(0,4)
                            + "_" + Helper.getPassCode(3);

                    passWord = Helper.getPassCode(12);

                    flag = loginDAO.checkUserNameExist(userName);

                }

                // create login for admin
                Login login = new Login(1, hod.getTeacherTypeId(), userName, passWord, 0);

                if(loginDAO.createLogin(login)){
                    logger.info("hodRegisterRequestAuthorization - login created successfully for " + hod
                            + " with login userName: " + login.getUserName());

                    //send email of approval to user and institute
                    String message = EmailMessageUtil.fetchMessageTemplate("login")
                            .replaceAll("user",hod.getFullName())
                            .replaceAll("request_type",RequestTypes.HEAD_OF_DEPARTMENT_REGISTER.getRequestTypeValue());

                    logger.info("hodRegisterRequestAuthorization - approval message for user: " + message);

                    emailService.SendEmail(hod.getEmailId(),
                            admin.getEmailId(), "Notification - registration request approved !!", message);


                    //send email to user with username and password
                    message = EmailMessageUtil.fetchMessageTemplate("authorization")
                            .replaceAll("user",hod.getFullName())
                            .replaceAll("user_name",userName)
                            .replaceAll("pass_word",passWord);

                    logger.info("hodRegisterRequestAuthorization - login credentials message for user: " + message);

                    emailService.SendEmail(hod.getEmailId(), "Credentials for login !!", message);

                    return true;

                } else {
                    logger.severe("hodRegisterRequestAuthorization to " + approvalStatus + " request for HOD - "
                            + teacherId + " failed to create login");
                    return false;
                }

            } else {

                //send mail of denial to requester
                String message = EmailMessageUtil.fetchMessageTemplate("registration_request_deny")
                        .replaceAll("user",hod.getFullName())
                        .replaceAll("request_type",RequestTypes.HEAD_OF_DEPARTMENT_REGISTER.getRequestTypeValue());

                logger.info("hodRegisterRequestAuthorization - denial message for user: " + message);

                emailService.SendEmail(hod.getEmailId(),
                        admin.getEmailId(), "Notification - registration request denied !!", message);

                return false;
            }

        }
    }


    public boolean teacherRegisterRequestAuthorization(int hodID, int teacherId, String approvalStatus){
        logger.info("inside teacherRegisterRequestAuthorization to " + approvalStatus + " request for teacher ID - " + teacherId);

        //get register request details of admin id
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        RegisterRequestDAO registerRequestDAO = context.getBean(RegisterRequestDAO.class);
        TeacherDAO teacherDAO = context.getBean(TeacherDAO.class);


        Teacher hod = teacherDAO.getTeacherById(hodID);
        Teacher teacher = teacherDAO.getTeacherById(teacherId);

        if(hod.getTeacherId() == -1){
            logger.severe("teacherRegisterRequestAuthorization to " + approvalStatus + " request for teacher ID - "
                    + teacherId + " failed as HOD information does not exist : " + hodID);
            return false;
        }

        if(teacher.getTeacherId() == -1){
            logger.severe("teacherRegisterRequestAuthorization to " + approvalStatus + " request for teacher ID - "
                    + teacherId + " failed as teacher information does not exist : " + teacherId);
            return false;
        }

        List<RegisterRequest> allRegisterRequest = registerRequestDAO.
                getRequests(hod.getTeacherTypeId(),
                        hodID,
                        RequestTypes.TEACHER_REGISTER.getRequestTypeValue(), approvalStatus);

        if(allRegisterRequest == null){
            logger.severe("teacherRegisterRequestAuthorization to " + approvalStatus + " request for Teacher id - "
                    + teacherId + " failed as unable to fetch any teacher register request information of given id");
            return false;
        }

        String searchInformation = teacher.toString();

        List<RegisterRequest> filteredRequestList =  allRegisterRequest.stream().
                filter(r -> r.getRequestInformation().equalsIgnoreCase(searchInformation))
                .collect(Collectors.toList());

        if(filteredRequestList == null || filteredRequestList.size() == 0 || filteredRequestList.size() > 1){
            logger.severe("teacherRegisterRequestAuthorization to " + approvalStatus + " request for Teacher ID - "
                    + teacherId + " failed as unable to find specific teacher register request information from register_request table from database");
            return false;
        } else {

            LoginDAO loginDAO = context.getBean(LoginDAO.class);
            //Get first register request
            RegisterRequest registerRequest = filteredRequestList.get(0);

            registerRequest.setStatus(approvalStatus);
            teacher.setIsDeleted(0);


            boolean registerRequestUpdateFlag = registerRequestDAO.updateStatusOfRequest(registerRequest);
            boolean teacherUpdateFlag = teacherDAO.activateTeacher(teacher);

            //updating latest status in database first
            if(registerRequestUpdateFlag && teacherUpdateFlag){
                logger.info("teacherRegisterRequestAuthorization to " + approvalStatus + " request for Teacher ID - "
                        + teacherId + " updated in register request and teacher table!!");
            } else {
                logger.info("teacherRegisterRequestAuthorization to " + approvalStatus + " request for Teacher ID - "
                        + teacherId + " failed to update in register request and teacher table!!");
                return false;
            }

            if(approvalStatus == "APPROVED"){

                //create username and password
                String userName = "";
                String passWord = "";
                boolean flag = true;

                while(flag){

                    //to check if username doesn't exist already in login table

                    userName = teacher.getFirstName().substring(0,4) + teacher.getLastName().substring(0,4)
                            + "_" + Helper.getPassCode(3);

                    passWord = Helper.getPassCode(12);

                    flag = loginDAO.checkUserNameExist(userName);

                }

                // create login for admin
                Login login = new Login(1, teacher.getTeacherTypeId(), userName, passWord, 0);

                if(loginDAO.createLogin(login)){
                    logger.info("teacherRegisterRequestAuthorization - login created successfully for " + teacher
                            + " with login userName: " + login.getUserName());

                    //send email of approval to user and institute
                    String message = EmailMessageUtil.fetchMessageTemplate("login")
                            .replaceAll("user",teacher.getFullName())
                            .replaceAll("request_type",RequestTypes.TEACHER_REGISTER.getRequestTypeValue());

                    logger.info("teacherRegisterRequestAuthorization - approval message for user: " + message);

                    emailService.SendEmail(teacher.getEmailId(),
                            hod.getEmailId(), "Notification - registration request approved !!", message);


                    //send email to user with username and password
                    message = EmailMessageUtil.fetchMessageTemplate("authorization")
                            .replaceAll("user",teacher.getFullName())
                            .replaceAll("user_name",userName)
                            .replaceAll("pass_word",passWord);

                    logger.info("teacherRegisterRequestAuthorization - login credentials message for user: " + message);

                    emailService.SendEmail(teacher.getEmailId(), "Credentials for login !!", message);

                    return true;

                } else {
                    logger.severe("teacherRegisterRequestAuthorization to " + approvalStatus + " request for teacher - "
                            + teacherId + " failed to create login");
                    return false;
                }

            } else {

                //send mail of denial to requester
                String message = EmailMessageUtil.fetchMessageTemplate("registration_request_deny")
                        .replaceAll("user",teacher.getFullName())
                        .replaceAll("request_type",RequestTypes.TEACHER_REGISTER.getRequestTypeValue());

                logger.info("teacherRegisterRequestAuthorization - denial message for user: " + message);

                emailService.SendEmail(teacher.getEmailId(),
                        hod.getEmailId(), "Notification - registration request denied !!", message);

                return false;
            }

        }
    }

}
