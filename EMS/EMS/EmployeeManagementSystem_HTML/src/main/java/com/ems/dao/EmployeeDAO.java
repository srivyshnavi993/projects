package com.ems.dao;

import com.ems.model.Employee;
import com.ems.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public boolean addEmployee(Employee employee) {

        String sql = "INSERT INTO employees(name,email,department,salary) VALUES(?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getDepartment());
            ps.setDouble(4, employee.getSalary());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Employee> getAllEmployees() {

        List<Employee> employees = new ArrayList<>();

        String sql = "SELECT * FROM employees ORDER BY id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("department"),
                        rs.getDouble("salary")
                );

                employees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public Employee getEmployeeById(int id) {

        String sql = "SELECT * FROM employees WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("department"),
                        rs.getDouble("salary")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateEmployee(Employee employee) {

        String sql = "UPDATE employees SET name=?,email=?,department=?,salary=? WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getDepartment());
            ps.setDouble(4, employee.getSalary());
            ps.setInt(5, employee.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteEmployee(int id) {

        String sql = "DELETE FROM employees WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Employee> searchEmployees(String keyword) {

        List<Employee> employees = new ArrayList<>();

        String sql = "SELECT * FROM employees WHERE name LIKE ? OR email LIKE ? OR department LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            ps.setString(1, value);
            ps.setString(2, value);
            ps.setString(3, value);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                employees.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("department"),
                        rs.getDouble("salary")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }
}
