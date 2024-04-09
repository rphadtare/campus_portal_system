package com.example.campus_portal_system.utility.controller;

import com.example.campus_portal_system.student.controller.CampusStudentController;
import com.example.campus_portal_system.utility.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final LoginService service;
    private final CampusStudentController campusStudentController;

    public LoginController(LoginService loginService, CampusStudentController campusStudentController) {
        this.service = loginService;
        this.campusStudentController = campusStudentController;
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
        System.out.println("Validated user: " + isValidUser);
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

        System.out.println("login result: " + result);
        return result;

    }

}