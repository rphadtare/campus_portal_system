package com.example.campus_portal_system.utility.controller;

import com.example.campus_portal_system.dept.beans.Institute;
import com.example.campus_portal_system.utility.service.AdminService;
import com.example.campus_portal_system.utility.service.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Logger;

@Controller
public class AdminController {

    private final AdminService adminService;

    private Logger logger;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
        logger = Logger.getLogger(AdminController.class.getName());
    }

    @RequestMapping(value = "/institute_off_boarding", method = RequestMethod.POST)
    public String deleteInstitute(
            @RequestParam("instituteId") int instituteId
    ) {

        logger.info("Inside deleteInstitute for off boarding instituteId " + instituteId);

        String result = "";
        if(adminService.deleteInstitute(instituteId)){
            logger.info("Inside deleteInstitute for off boarding of instituteId" + instituteId + " is successful");
            result = "institute_off_boarding_success";
        } else {
            logger.severe("Inside deleteInstitute for off boarding of instituteId " + instituteId + " is failed");
            result = "institute_off_boarding_failed";
        }

        return result;
    }





}
