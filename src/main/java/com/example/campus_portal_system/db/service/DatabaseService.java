package com.example.campus_portal_system.db.service;

import com.example.campus_portal_system.db.beans.DatabaseDetails;

public class DatabaseService {

    private static boolean DB_Details_Flag = false;
    private static DatabaseDetails DB_Details = null;

    public DatabaseDetails getDBDetails() {
        if (!DB_Details_Flag) {
            DB_Details = new DatabaseDetails();
            DB_Details_Flag = true;
        }
        return DB_Details;
    }




}
