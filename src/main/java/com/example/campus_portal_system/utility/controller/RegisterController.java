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

        System.out.println("[RegisterController]: Inside /department_register post ...");


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


        //To check admin details for given institute id
        Admin admin = registerService.getAdminByInstituteId(instituteId);
        if(admin == null){
            //no admin is found for given institute.
            //Hence, request could not be complete further
            return "no_admin";
        }

        System.out.println("[RegisterController]: Admin details fetch successfully for institute id : " + instituteId);

        // store register request details in database system
        RegisterRequest registerRequest =
                new RegisterRequest(1,"HOD Register",teacher.toString(),
                        admin.getAdminTypeId(),admin.getAdminId(),"OPEN");

        boolean registerTeacherFlag = registerService.registerTeacher(teacher);
        boolean registerRequestFlag = registerService.storeRequestDetails(registerRequest);

        System.out.println("[RegisterController]: register teacher status : " +
                "" + registerTeacherFlag  + "" +
                "register request status " + registerRequestFlag);


        if(registerTeacherFlag && registerRequestFlag) {
            return "department_register_success";
        } else {
            return "department_register_failed";
        }

    }




}
