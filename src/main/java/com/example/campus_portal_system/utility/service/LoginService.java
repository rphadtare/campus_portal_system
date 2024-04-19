package com.example.campus_portal_system.utility.service;

import org.springframework.stereotype.Service;

import java.util.logging.Logger;

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

    public boolean adminRegisterRequestAuthorization(int adminId, int instituteId, String approval, String passCode){
        logger.info("inside adminRegisterRequestAuthorization to " + approval + " request for admin id - " + adminId);

        //get register request details of admin id






        return false;
    }


    public boolean provideLoginToUser(){


        return true;
    }




}
