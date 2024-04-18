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
        /*
            This method used to get few details before redirecting to department_register.jsp page
         */
        List<Institute> instituteList = registerService.getAllDepartments();
        model.addAttribute("institutes", instituteList);
        return "/dept/department_register";
    }

    @RequestMapping(value = "/department_register", method = RequestMethod.POST)
    public String saveHeadOfDepartmentInformation(Model model) {
        /*
            This method used to store head of department registration request data for further processing
         */

        //Here we will get data from department_register.jsp page
        //logic to get that data is yet to implement. Hence, using static data as of.






        return "/dept/department_register";
    }

}
