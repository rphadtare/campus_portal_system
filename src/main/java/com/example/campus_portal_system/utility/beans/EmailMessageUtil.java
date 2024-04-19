package com.example.campus_portal_system.utility.beans;

import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class EmailMessageUtil {

    public static String fetchMessageTemplate(String type){

        String fileName = "";
        switch (type){
            case "registration":
                fileName = "registration_successful_message.txt";
                break;

            case "registration_failed":
                fileName = "registration_unsuccessful_message.txt";
                break;

            case "admin_not_present":
                fileName = "admin_not_present.txt";
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
