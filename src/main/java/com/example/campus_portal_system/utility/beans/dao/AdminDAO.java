package com.example.campus_portal_system.utility.beans.dao;

import com.example.campus_portal_system.utility.beans.Admin;

public interface AdminDAO {

    public Boolean createAdmin(Admin admin);

    public Boolean updateAdminInfo(Admin admin);

    public Admin getAdminInfo(int instituteId);

    public Admin getAdminInfo(int instituteId, String emailId);

    public Boolean checkIfAdminExistForInstitute(int instituteId);

    public Admin getAdminInfoByAdminId(int adminId);

    public Boolean deleteAdmin(Admin admin);

}
