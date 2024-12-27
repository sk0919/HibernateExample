package com.hibernate.test.client;


import java.util.Date;
import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import com.hibernate.test.entities.Employee;
import com.hibernate.test.util.HibernateUtil;






public class ClientTest {

    public static void main(String[] args) {
        // Using try-with-resources to manage the Hibernate session
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Native SQL Query to get the database version
            Employee employee = createEmployee();
            
            session.beginTransaction();
            Serializable serializable = (Serializable) session.save(employee);
            Integer id = (Integer) serializable;
           
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // HibernateUtil shutdown (if needed elsewhere in the program)
            HibernateUtil.shutdown();
        }
    }
    
    private static Employee createEmployee() {
    	
    	Employee employee  = new Employee();
    	employee.setEmployeeId(1);
    	employee.setEmployeeName("Sean");
    	employee.setEmail("Test");
    	employee.setSalary(70000.00);
    	employee.setDateOfJoining(new Date());
    	
    	return employee;
    	
    }
}
//public class ClientTest {
//
//    public static void main(String[] args) {
//        // Using try-with-resources to manage the Hibernate session
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            // Native SQL Query to get the database version
//            String sql = "SELECT version()";
//            NativeQuery<String> query = session.createNativeQuery(sql);
//
//            // Execute the query and retrieve the result
//            String dbVersion = query.getSingleResult();
//            System.out.println("Database Version: " + dbVersion);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // HibernateUtil shutdown (if needed elsewhere in the program)
//            HibernateUtil.shutdown();
//        }
//    }
//}
