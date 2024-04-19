package com.example.campus_portal_system.utility.service;

import com.example.campus_portal_system.db.beans.DatabaseConfig;
import com.example.campus_portal_system.dept.beans.dao.InstituteDAO;
import com.example.campus_portal_system.utility.beans.dao.RegisterRequestDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AdminService {

    private Logger logger = Logger.getLogger(AdminService.class.getName());

    public Boolean deleteInstitute(int id){

        /*
            To delete institute by id
         */
        logger.info(" Inside deleteInstitute to delete institute id " + id);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        InstituteDAO instituteDAO = context.getBean(InstituteDAO.class);
        return instituteDAO.deleteInstitute(id);

    }


}
