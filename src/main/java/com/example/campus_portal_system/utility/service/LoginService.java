package com.example.campus_portal_system.utility.service;

import com.example.campus_portal_system.db.beans.DatabaseConfig;
import com.example.campus_portal_system.dept.beans.Institute;
import com.example.campus_portal_system.dept.beans.dao.InstituteDAO;
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





}
