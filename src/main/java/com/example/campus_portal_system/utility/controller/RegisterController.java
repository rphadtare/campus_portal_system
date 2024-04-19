package com.example.campus_portal_system.utility.controller;


import com.example.campus_portal_system.dept.beans.Institute;
import com.example.campus_portal_system.teacher.beans.Teacher;
import com.example.campus_portal_system.utility.beans.Admin;
import com.example.campus_portal_system.utility.service.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping(value = "/institute_register", method = RequestMethod.POST)
    public String registerInstitute(
            @RequestParam("name") String name,
            @RequestParam("university") String university,
            @RequestParam("emailId") String emailId,
            @RequestParam("address") String address
    ) {
        /*
            This method used to register institute
         */

        //create institute object
        Institute institute = new Institute(1, name, university, emailId, address);
        logger.info("Inside institute_register for registration of " + institute);

        String result = "";
        if(registerService.registerInstitute(institute)){
            logger.info("Inside institute_register for registration of " + institute + " is successful");
            result = "institute_success";
        } else {
            logger.severe("Inside institute_register for registration of " + institute + " is failed");
            result = "institute_failed";
        }

        return result;
    }


    @RequestMapping(value = "/admin_register", method = RequestMethod.POST)
    public String registerAdmin(
            @RequestParam("instituteId") int instituteId,
            @RequestParam("salutations") String salutations,
            @RequestParam("firstName") String firstName,
            @RequestParam("middleName") String middleName,
            @RequestParam("lastName") String lastName,
            @RequestParam("qualifications") String qualifications,
            @RequestParam("emailId") String emailId,
            @RequestParam("contactNo") String contactNo
    ){
        /*
            This method used to register admin for given institute id
         */
        Admin admin = new Admin(1,instituteId,1,salutations,firstName,middleName,lastName,
                qualifications,emailId,contactNo,0);

        logger.info("Inside admin_register to register - " + admin);
        if(registerService.registerAdmin(admin)){
            return "success_admin";
        } else {
            return "failure_admin";
        }

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
    public String saveHeadOfDepartmentInformation(
            @RequestParam("instituteId") int instituteId,
            @RequestParam("departmentId") int departmentId,
            @RequestParam("teacherTypeId") int teacherTypeId,
            @RequestParam("salutations") String salutations,
            @RequestParam("firstName") String firstName,
            @RequestParam("middleName") String middleName,
            @RequestParam("lastName") String lastName,
            @RequestParam("qualifications") String qualifications,
            @RequestParam("emailId") String emailId,
            @RequestParam("contactNo") String contactNo

    ) {
        /*
            This method used to store head of department registration request data for further processing
         */

        logger.info("Inside /department_register post ...");


        //Here we will get data from department_register.jsp page
        //logic to get that data is yet to implement. Hence, using static data as of.

        //Assume we will receive required fields for registering HOD


        // here by default when HOD is registering for particular department, then as per system
        // HOD will be class teacher too.
        // If there is already a HOD for that department then that request will get deny by admin.
        //int teacherTypeId = 4;

        //email ID need to check if it is valid or not on front end

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
