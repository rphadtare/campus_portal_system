package com.example.campus_portal_system.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CampusStudentController {

    @RequestMapping(value = "/student_welcome", method = RequestMethod.GET)
    public String welcomeStudent() {
        System.out.println("[CampusStudentController] : Returning student/StudentHome ... ");
        return "student/student_home";
    }

}
