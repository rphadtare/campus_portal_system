package com.example.campus_portal_system.utility.beans;

import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class EmailMessageUtil {

    public static String fetchMessageTemplate(String type){

        String fileName = "";
        switch (type){
            case "institute_onboard_success":
                fileName = "institute_onboarding_success.txt";
                break;

            case "institute_onboard_failed":
                fileName = "institute_onboarding_failed.txt";
                break;


            case "registration":
                fileName = "registration_successful_message.txt";
                break;

            case "registration_failed":
                fileName = "registration_unsuccessful_message.txt";
                break;

            case "registration_request_deny":
                fileName = "registration_request_deny.txt";
                break;

            case "admin_not_present":
                fileName = "admin_not_present.txt";
                break;

            case "admin_already_exist":
                fileName = "admin_already_exist.txt";
                break;

            case "admin_passcode":
                fileName = "admin_passcode_message.txt";
                break;

            case "hod_already_exist":
                fileName = "HOD_already_exist.txt";
                break;

            case "hod_not_exist":
                fileName = "HOD_not_exist.txt";
                break;

            case "login":
                fileName = "login_success.txt";
                break;

            case "authorization":
                fileName = "authorization_success.txt";
                break;


        }


        StringBuffer sb = new StringBuffer();
        try {
            File file = ResourceUtils.getFile("classpath:email/"+fileName);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()){
                sb.append(sc.nextLine() + "\n");
            }

        } catch (IOException e) {
            e.getMessage();
        }
        return sb.toString();
    }

}
