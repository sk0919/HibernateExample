package com.hibernate.test.client;


import java.util.Date;
import java.io.Serializable;
import org.hibernate.Session;


import org.hibernate.query.NativeQuery;

import com.hibernate.test.entities.Employee;
import com.hibernate.test.util.HibernateUtil;
import org.hibernate.Transaction;





public class ClientTest {

    public static void main(String[] args) {
        // Using try-with-resources to manage the Hibernate session
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Perform CRUD operations
            createEmployee(session);
            readEmployee(session);
            updateEmployee(session);
            deleteEmployee(session);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // HibernateUtil shutdown (if needed elsewhere in the program)
            HibernateUtil.shutdown();
        }
    }

    // Create operation
    private static void createEmployee(Session session) {
        Employee employee = createEmployeeData();

        // Start transaction
        Transaction transaction = session.beginTransaction();
        try {
            Serializable serializable = (Serializable) session.save(employee); // Save the employee
            Integer id = (Integer) serializable;
            System.out.println("Employee saved with ID: " + id);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    // Read operation
    private static void readEmployee(Session session) {
        // Get an employee by its ID (for example, ID = 1)
        Employee employee = session.get(Employee.class, 1);

        if (employee != null) {
            System.out.println("Employee found: " + employee.getEmployeeName());
        } else {
            System.out.println("Employee not found!");
        }
    }

    // Update operation
    private static void updateEmployee(Session session) {
        // Assume the employee with ID 1 exists
        Employee employee = session.get(Employee.class, 1);

        if (employee != null) {
            // Update employee details
            employee.setEmployeeName("Updated Employee Name");
            employee.setSalary(80000.00);

            // Start transaction
            Transaction transaction = session.beginTransaction();
            try {
                session.update(employee); // Update the employee
                transaction.commit();
                System.out.println("Employee updated: " + employee.getEmployeeName());
            } catch (Exception e) {
                e.printStackTrace();
                session.getTransaction().rollback();
            }
        } else {
            System.out.println("Employee not found to update!");
        }
    }

    // Delete operation
    private static void deleteEmployee(Session session) {
        // Assume the employee with ID 1 exists
        Employee employee = session.get(Employee.class, 1);

        if (employee != null) {
            // Start transaction
            Transaction transaction = session.beginTransaction();
            try {
                session.delete(employee); // Delete the employee
                transaction.commit();
                System.out.println("Employee deleted: " + employee.getEmployeeName());
            } catch (Exception e) {
                e.printStackTrace();
                session.getTransaction().rollback();
            }
        } else {
            System.out.println("Employee not found to delete!");
        }
    }

    // Helper method to create employee data
    private static Employee createEmployeeData() {
        Employee employee = new Employee();
        employee.setEmployeeName("Sean");
        employee.setEmail("sean@example.com");
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
