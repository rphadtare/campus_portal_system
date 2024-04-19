package com.example.campus_portal_system.utility.service;

import com.example.campus_portal_system.db.beans.DatabaseConfig;
import com.example.campus_portal_system.dept.beans.dao.InstituteDAO;
import com.example.campus_portal_system.utility.beans.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.logging.Logger;

public class EmailService {

    private Logger logger;

    public EmailService(){
        this.logger = Logger.getLogger(EmailService.class.getName());
    }

    public void SendEmail(String toEmail,
                          String subject,
                          String body){
        logger.info("Inside SendEmail  ...");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MailConfig.class);
        JavaMailSender javaMailSender = context.getBean(JavaMailSender.class);


        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        javaMailSender.send(message);

        logger.info("Message sent to : " + toEmail);

    }

    public void SendEmail(String toEmail,
                          String ccEmail,
                          String subject,
                          String body){
        logger.info("Inside SendEmail with CC ...");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MailConfig.class);
        JavaMailSender javaMailSender = context.getBean(JavaMailSender.class);

        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(toEmail);
        message.setCc(ccEmail);
        message.setText(body);
        message.setSubject(subject);
        javaMailSender.send(message);
        logger.info("Message sent to : " + toEmail);

    }


}
