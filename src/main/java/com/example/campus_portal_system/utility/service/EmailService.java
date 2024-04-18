package com.example.campus_portal_system.utility.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void SendEmail(String toEmail,
                          String subject,
                          String body){
        System.out.println("[RegisterService]: Inside SendEmail  ...");
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);

        System.out.println("[RegisterService]: Message sent to : " + toEmail);

    }

    public void SendEmail(String toEmail,
                          String ccEmail,
                          String subject,
                          String body){
        System.out.println("[RegisterService]: Inside SendEmail with CC ...");

        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(toEmail);
        message.setCc(ccEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("[RegisterService]: Message sent to : " + toEmail);

    }


}
