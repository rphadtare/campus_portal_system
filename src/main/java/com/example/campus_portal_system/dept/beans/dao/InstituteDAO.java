package com.example.campus_portal_system.dept.beans.dao;

import com.example.campus_portal_system.dept.beans.Institute;

import java.util.List;

public interface InstituteDAO {

    Institute getInstituteById(int id);

    List<Institute> getAllInstitute();

    boolean deleteInstitute(Institute institute);

    boolean deleteInstitute(int id);

    boolean updateInstitute(Institute institute);

    boolean createInstitute(Institute institute);

    boolean checkExist(Institute institute);

}
