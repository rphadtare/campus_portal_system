package com.example.campus_portal_system.utility.service;

import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class LoginService {

    private Logger logger;

    LoginService(){
        this.logger = Logger.getLogger(LoginService.class.getName());
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

    public boolean provideLoginToUser(){


        return true;
    }


}
