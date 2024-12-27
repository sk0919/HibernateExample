package com.hibernate.test.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.hibernate.test.entities.Employee;

import org.hibernate.HibernateException;

import java.util.HashMap;
import java.util.Map;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create a Map for Hibernate configuration
            Map<String, String> settings = new HashMap<>();
            settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/hibernate_db");
            settings.put("hibernate.connection.username", "root");
            settings.put("hibernate.connection.password", "root");
            settings.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
            settings.put("hibernate.hbm2ddl.auto", "update");
            settings.put("hibernate.show_sql", "true");
            settings.put("hibernate.format_sql", "true");

            // Pass the Map to Configuration object
            Configuration configuration = new Configuration();
            
         // Register the entity class programmatically
            configuration.addAnnotatedClass(Employee.class);
            
            settings.forEach(configuration::setProperty);

            // Build the SessionFactory
            sessionFactory = configuration.buildSessionFactory();
        } catch (HibernateException ex) {
            // Log the exception to help with debugging
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
