package com.example.campus_portal_system.utility.service;

import com.example.campus_portal_system.CampusPortalSystemApplication;
import com.example.campus_portal_system.db.beans.DatabaseConfig;
import com.example.campus_portal_system.dept.beans.Institute;
import com.example.campus_portal_system.dept.beans.dao.InstituteDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterService {

    public List<Institute> getAllDepartments(){
        System.out.println("[RegisterService]: Inside getAllDepartments ...");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);

        InstituteDAO instituteDAO = context.getBean(InstituteDAO.class);
        List<Institute> instituteList = instituteDAO.getAllInstitute();
        System.out.println("[RegisterService]: List of institute is - ");
        for (Institute i : instituteList) {
            System.out.println(i);
        }

        return instituteList;
    }

}
