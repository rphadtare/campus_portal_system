package com.example.campus_portal_system.dept.beans.dao;

import com.example.campus_portal_system.dept.beans.Department;

import java.util.List;

public interface DepartmentDAO {

    public List<Department> getAllDepartments();

    public Department getDepartmentByID(int id);

    public Department getDepartmentByName(String name);

}
