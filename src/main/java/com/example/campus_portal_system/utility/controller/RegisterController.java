package com.example.campus_portal_system.utility.controller;


import com.example.campus_portal_system.dept.beans.Institute;
import com.example.campus_portal_system.utility.service.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterPage(){
        return "register";
    }

    @RequestMapping(value = "/department_register", method = RequestMethod.GET)
    public String showDepartmentRegisterPage(Model model) {
        List<Institute> instituteList = registerService.getAllDepartments();
        model.addAttribute("institutes", instituteList);
        return "/dept/department_register";
    }



}
