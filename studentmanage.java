import java.sql.*;
import java.util.*;
public class studentmanage {
        static final String URL="jdbc:mysql://localhost:3306/student_management";
        static final String USER="root";
        static final String PASSWORD="vyshnavi";
         static Scanner sc=new Scanner(System.in);
         public static void main(String[] args){
            while(true){
                System.out.println("x:/n====STUDENT MANGEMENT SYSTEM====");
                System.out.println("1.Add student");
                System.out.println("2.View All Statement");
                System.out.println("3.Search Student");
                System.out.println("4..Update Student");
                System.out.println("5..Delete Student");
                System.out.println("6.Exit");
                System.out.println("Enter your choice");
                int choice=sc.nextInt();
                switch(choice) {
                    case 1:
                    addStudent();
                    break;
                    case 2:
                    viewStudent();
                    break;
                    case 3:
                    searchStudent();
                    break;
                    case 4:
                    updateStudent();
                    break;
                    case 5:
                    deleteStudent();
                    break;
                    case 6:
                    System.out.println("thank you");
                    System.exit(0);
                    default:
                        System.out.print("invalid choice");
                }
            }
        
        }
        static void addStudent(){
            System.out.println("Enter student ID:");
            int id=sc.nextInt();
            sc.nextLine();
            System.out.println("enter name:");
            String name=sc.nextLine();
            System.out.println("enter Age:");
            int age=sc.nextInt();
            sc.nextLine();
            System.out.print("Enter course");
            String course=sc.next();
            System.out.print("Enter Email");
            String email=sc.nextLine();
            String sql ="INSERT INTO students VALUES(?,?,?,?,?)";
            try(Connection con=DriverManager.getConnection(URL,USER,PASSWORD);
                PreparedStatement ps=con.prepareStatement("sql"))
                {
                    ps.setInt(1,id);
                    ps.setString(2,name);
                    ps.setInt(3,age);
                    ps.setString(4,course);
                    ps.setString(5,email);
                    int rows=ps.executeUpdate();
                    if(rows>0) {
                        System.out.print("Student added successful");
    
                    }
                }
                catch(SQLException e) {
                    e.printStackTrace();
                    //System.out.print("Enter:"+ e.getmessage());
                }
            }
        static void viewStudent(){
            String sql1="SELECT * FROM Students";
            try(Connection con=DriverManager.getConnection(URL,USER,PASSWORD);
             Statement st=con.createStatement();
             ResultSet rs=st.executeQuery(sql1)){
             System.out.print("/ID\tName\tAge\tcourse\tEmail");
             System.out.print("--------------");
             while(rs.next()) {
                System.out.print(
                    rs.getInt(1)+"\t"+
                    rs.getString(2)+"/t"+
                    rs.getInt(3)+"\t"+
                    rs.getString(4)+"\t"+
                    rs.getString(5));
            }
        }
            catch(Exception e) {
                System.out.print("error");
            }
        }
        static void searchStudent() {
            System.out.print("enter student Id");
            int id=sc.nextInt();
            try(Connection con=DriverManager.getConnection(URL,USER,PASSWORD);
        PreparedStatement ps=con.prepareStatement("SELECT * FROM students WHERE id=?");
        ResultSet rs=ps.executeQuery()){
            ps.setInt(1,id);
            if(rs.next()) {
                System.out.print("student found()");
                System.out.print("ID:+rs.getInt(1)");
                System.out.print("Name:+rs.getString(2)");
                System.out.print("Age:+rs.getString(3)");
                System.out.print("course:"+rs.getString(4));
                System.out.print("Email:+rs.getString(5)");
    
            }
            else {
                System.out.print("student not found");
            }
        }
            catch(Exception e) {
                System.out.print("Error:e.getmessage()");
            }
        }
        static void updateStudent() {
            System.out.print("Enter student ID to update");
            int id=sc.nextInt();
            sc.nextLine();
            System.out.print("Enter your name");
            String name=sc.nextLine();
            System.out.print("Enter new Age");
            int age=sc.nextInt();
            sc.nextLine();
            System.out.print("Enter new course");
            String course=sc.nextLine();
            
    
        System.out.println("Enter New Email:");
        String email=sc.nextLine();
        String sql="UPDATE Student set name=?,age=?,Course=?,email=?,where id=?";
        try(Connection con=DriverManager.getConnection(URL,USER,PASSWORD);
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1,name);
            ps.setInt(2,age);
            ps.setString(3,course);
            ps.setString(4,email);
            ps.setInt(5,id);
            int rows=ps.executeUpdate();
            if(rows>0) {
              System.out.print("Student Update Successful");
            }
             else {
            System.out.print("student ID not found");
            }
        } 
        catch(SQLException e) {
               System.out.print("Error"+e.getMessage());
            }
        }
    static void deleteStudent() {
        System.out.print("enter Student ID to delete");
        int id=sc.nextInt();
        String sql="Delete from student whereid=?";
        try(Connection con=DriverManager.getConnection(URL,USER,PASSWORD);
        PreparedStatement ps=con.prepareStatement(sql))
        {
            ps.setInt(1,id);
            int rows=ps.executeUpdate();
            if(rows>0) {
                System.out.print("student selected succesfully");
            }
            else{
                System.out.print("Student ID not found");
            }
        }
            catch(SQLException e) {
                System.out.print("Error"+e.getMessage());
            }
        }
}
