package com.example.campus_portal_system.utility.controller;


import com.example.campus_portal_system.dept.beans.Institute;
import com.example.campus_portal_system.teacher.beans.Teacher;
import com.example.campus_portal_system.utility.beans.Admin;
import com.example.campus_portal_system.utility.beans.RegisterRequest;
import com.example.campus_portal_system.utility.service.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class RegisterController {

    private final RegisterService registerService;

    private Logger logger;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
        logger = Logger.getLogger(RegisterController.class.getName());
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

        logger.info("Inside /department_register post ...");


        //Here we will get data from department_register.jsp page
        //logic to get that data is yet to implement. Hence, using static data as of.

        //Assume we will receive required fields for registering HOD

        int instituteId = 1;
        int departmentId = 1;

        // here by default when HOD is registering for particular department, then as per system
        // HOD will be class teacher too.
        // If there is already a HOD for that department then that request will get deny by admin.
        int teacherTypeId = 4;

        String salutations = "Mr.";
        String firstName = "Rohit";
        String middleName = "Prakash";
        String lastName = "Phadtare";
        String qualifications = "B.E. Information Technology";

        //email ID need to check if it is valid or not on front end
        String emailId = "rohitphadtare39@outlook.com";
        String contactNo = "7709665713";

        // here isDeleted is 1 because by default when user will register, that record still is deleted in system
        // until admin approves it
        int isDeleted = 1;

        Teacher teacher = new Teacher(0,instituteId,departmentId,teacherTypeId,salutations,
                firstName,middleName,lastName,qualifications,emailId,contactNo,isDeleted);

        boolean flag = registerService.registerTeacher(teacher);
        logger.info("status of  department_register " + flag);

        if(flag){
            return "next_step_post_successful_registartion";
        } else {
            return "error";
        }
    }



}
