package com.example.campus_portal_system.utility.service;

import org.springframework.stereotype.Service;
@Service
public class LoginService {
    public boolean validateUser(String userid, String password, int usertype) {

        System.out.println("[LoginService]: Inside validateUser ...");
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
