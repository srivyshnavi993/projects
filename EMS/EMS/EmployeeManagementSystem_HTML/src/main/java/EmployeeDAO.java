import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    public List<Employee> list(String keyword) throws SQLException {
        String sql = "SELECT id,name,email,department,salary FROM employees " +
                (keyword == null || keyword.isBlank() ? "" : "WHERE name LIKE ? OR email LIKE ? OR department LIKE ? ") +
                "ORDER BY id";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            if (keyword != null && !keyword.isBlank()) {
                String k = "%" + keyword + "%";
                ps.setString(1, k); ps.setString(2, k); ps.setString(3, k);
            }
            try (ResultSet rs = ps.executeQuery()) {
                List<Employee> out = new ArrayList<>();
                while (rs.next()) out.add(from(rs));
                return out;
            }
        }
    }

    public Employee get(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(
                "SELECT id,name,email,department,salary FROM employees WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? from(rs) : null; }
        }
    }

    public void add(String name, String email, String department, double salary) throws SQLException {
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(
                "INSERT INTO employees(name,email,department,salary) VALUES(?,?,?,?)")) {
            ps.setString(1,name); 
            ps.setString(2,email); 
            ps.setString(3,department); 
            ps.setDouble(4,salary); 
            ps.executeUpdate();
        }
    }

    public void update(int id, String name, String email, String department, double salary) throws SQLException {
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(
                "UPDATE employees SET name=?,email=?,department=?,salary=? WHERE id=?")) {
            ps.setString(1,name); ps.setString(2,email); ps.setString(3,department); ps.setDouble(4,salary); ps.setInt(5,id); ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement("DELETE FROM employees WHERE id=?")) {
            ps.setInt(1,id); ps.executeUpdate();
        }
    }

    private Employee from(ResultSet rs) throws SQLException {
        return new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("department"), rs.getDouble("salary"));
    }
}
