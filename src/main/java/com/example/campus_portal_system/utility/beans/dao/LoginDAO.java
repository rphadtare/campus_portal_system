package com.example.campus_portal_system.utility.beans.dao;

import com.example.campus_portal_system.utility.beans.Login;

import java.util.List;

public interface LoginDAO {

    public Boolean createLogin(Login login);

    public Boolean deleteLogin(int loginId);

    public Login getLogin(int loginId);

    public Login getLogin(int userType, String userName);

    public List<Login> getAllLogin();

    public List<Login> getAllLogin(int userTypeId);

    public boolean checkUserNameExist(String userName);

}
