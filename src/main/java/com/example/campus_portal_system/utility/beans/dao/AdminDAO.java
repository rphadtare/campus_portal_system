package com.example.campus_portal_system.utility.beans.dao;

import com.example.campus_portal_system.utility.beans.Admin;

public interface AdminDAO {

    public Boolean createAdmin(Admin admin);

    public Boolean updateAdminInfo(Admin admin);

    public Admin getAdminInfo(int instituteId);

}
