package com.example.campus_portal_system.db.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("/WEB-INF/database.properties")
public class DatabaseDetails {

    @Autowired
    private Environment env;

    public void getDBConnectionString() {
        env.getProperty("db_connection_str");
    }

    public void getDBUserName() {
        env.getProperty("db_user_name");
    }

    public void getDBUserPassword() {
        env.getProperty("db_password");
    }

}
