package com.example.campus_portal_system.db.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.example.campus_portal_system")
@PropertySource("classpath:database.properties")
public class DatabaseConfig {

    @Autowired
    private Environment env;

    @Bean
    DataSource dataSource() {
        System.out.println("[DatabaseConfig] creating data source with following properties");
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        String[] arr = new String[] {"db_connection_str", "db_user_name", "db_password", "driver_class_name"};
        for (int i = 0; i < arr.length; i++) {
            System.out.println("[" + arr[i] + "] : " + env.getProperty(arr[i]));
        }

        driverManagerDataSource.setUrl(env.getProperty("db_connection_str"));
        driverManagerDataSource.setUsername(env.getProperty("db_user_name"));
        driverManagerDataSource.setPassword(env.getProperty("db_password"));
        driverManagerDataSource.setDriverClassName(env.getProperty("driver_class_name"));
        return driverManagerDataSource;
    }

}
