package com.ems.servlet;

import com.ems.dao.EmployeeDAO;
import com.ems.model.Employee;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {

    private EmployeeDAO employeeDAO;

    @Override
    public void init() {
        employeeDAO = new EmployeeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {

            case "get":
                getEmployee(request, response);
                break;

            case "delete":
                deleteEmployee(request, response);
                break;

            case "search":
                searchEmployees(request, response);
                break;

            default:
                listEmployees(response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addEmployee(request, response);
        } else if ("update".equals(action)) {
            updateEmployee(request, response);
        }
    }

    private void addEmployee(HttpServletRequest request,
                             HttpServletResponse response)
            throws IOException {

        Employee employee = new Employee(
                request.getParameter("name"),
                request.getParameter("email"),
                request.getParameter("department"),
                Double.parseDouble(request.getParameter("salary"))
        );

        boolean result = employeeDAO.addEmployee(employee);

        response.getWriter().print(
                result ? "Employee added successfully"
                       : "Failed to add employee"
        );
    }

    private void listEmployees(HttpServletResponse response)
            throws IOException {

        List<Employee> employees =
                employeeDAO.getAllEmployees();

        sendEmployeesAsJson(employees, response);
    }

    private void getEmployee(HttpServletRequest request,
                             HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(
                request.getParameter("id")
        );

        Employee employee =
                employeeDAO.getEmployeeById(id);

        if (employee == null) {
            response.getWriter().print("{}");
            return;
        }

        PrintWriter out = response.getWriter();

        out.print("{");
        out.print("\"id\":" + employee.getId() + ",");
        out.print("\"name\":\"" + escape(employee.getName()) + "\",");
        out.print("\"email\":\"" + escape(employee.getEmail()) + "\",");
        out.print("\"department\":\"" + escape(employee.getDepartment()) + "\",");
        out.print("\"salary\":" + employee.getSalary());
        out.print("}");
    }

    private void updateEmployee(HttpServletRequest request,
                                HttpServletResponse response)
            throws IOException {

        Employee employee = new Employee(
                Integer.parseInt(request.getParameter("id")),
                request.getParameter("name"),
                request.getParameter("email"),
                request.getParameter("department"),
                Double.parseDouble(request.getParameter("salary"))
        );

        boolean result = employeeDAO.updateEmployee(employee);

        response.getWriter().print(
                result ? "Employee updated successfully"
                       : "Failed to update employee"
        );
    }

    private void deleteEmployee(HttpServletRequest request,
                                HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(
                request.getParameter("id")
        );

        boolean result = employeeDAO.deleteEmployee(id);

        response.getWriter().print(
                result ? "Employee deleted successfully"
                       : "Failed to delete employee"
        );
    }

    private void searchEmployees(HttpServletRequest request,
                                 HttpServletResponse response)
            throws IOException {

        String keyword = request.getParameter("keyword");

        if (keyword == null) {
            keyword = "";
        }

        List<Employee> employees =
                employeeDAO.searchEmployees(keyword);

        sendEmployeesAsJson(employees, response);
    }

    private void sendEmployeesAsJson(List<Employee> employees,
                                     HttpServletResponse response)
            throws IOException {

        PrintWriter out = response.getWriter();

        out.print("[");

        for (int i = 0; i < employees.size(); i++) {

            Employee e = employees.get(i);

            out.print("{");
            out.print("\"id\":" + e.getId() + ",");
            out.print("\"name\":\"" + escape(e.getName()) + "\",");
            out.print("\"email\":\"" + escape(e.getEmail()) + "\",");
            out.print("\"department\":\"" + escape(e.getDepartment()) + "\",");
            out.print("\"salary\":" + e.getSalary());
            out.print("}");

            if (i < employees.size() - 1) {
                out.print(",");
            }
        }

        out.print("]");
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"");
    }
}
