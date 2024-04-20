package com.example.campus_portal_system.utility.controller;

import com.example.campus_portal_system.student.controller.CampusStudentController;
import com.example.campus_portal_system.utility.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
public class LoginController {

    private final LoginService service;
    private final CampusStudentController campusStudentController;

    private Logger logger;

    public LoginController(LoginService loginService, CampusStudentController campusStudentController) {
        this.service = loginService;
        this.campusStudentController = campusStudentController;
        this.logger = Logger.getLogger(LoginController.class.getName());
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String showWelcomePage( Model model,
                                   @RequestParam String name,
                                   @RequestParam String password,
                                   @RequestParam int usertype) {
        boolean isValidUser = service.validateUser(name, password, usertype);
        logger.info("Validated user: " + isValidUser);
        String result = "login";

        // if user is valid
        if (isValidUser) {

            switch (usertype){
                case 1:

                    model.addAttribute("user_name", name);
                    model.addAttribute("user_type", usertype);

                    return campusStudentController.welcomeStudent();

                case 2:
                    break;
            }

        } else {
            // return with default login page
            model.addAttribute("errorMessage", "Invalid Credentials!!");
        }

        logger.info("login result: " + result);
        return result;

    }

    @RequestMapping(value = "/approve_admin_registration", method = RequestMethod.POST)
    public String approveAdminRegistration(
            @RequestParam("adminId") int adminId,
            @RequestParam("instituteId") int instituteId,
            @RequestParam("passCode") String passCode,
            @RequestParam("approval") String approval
    ) {

        logger.info("Inside approve_admin_registration for " + approval + " of admin id " + adminId);
        boolean flag = service.adminRegisterRequestAuthorization(adminId,instituteId,approval,passCode);
        logger.info("approve_admin_registration for " + approval + " of admin id " + adminId + " final result: " + flag);

        if(flag){
            return "admin_approved";
        } else {
            return "admin_approval_denied";
        }
    }

    @RequestMapping(value = "/approve_hod_registration", method = RequestMethod.POST)
    public String approveHODRegistration(
            @RequestParam("adminId") int adminId,
            @RequestParam("teacherId") int teacherId,
            @RequestParam("approval") String approval
    ) {

        logger.info("Inside approve_hod_registration for " + approval + " of HOD ID " + teacherId);
        boolean flag = service.hodRegisterRequestAuthorization(adminId,teacherId,approval);
        logger.info("approve_hod_registration for " + approval + " of HOD ID " + teacherId + " final result: " + flag);

        if(flag){
            return "hod_approved";
        } else {
            return "hod_approval_denied";
        }
    }


}
