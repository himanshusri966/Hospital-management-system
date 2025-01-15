package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection conn;
    private Scanner sc;

    public Patient(Connection conn, Scanner sc) {
        this.conn = conn;
        this.sc = sc;
    }


    public void addPatient() {
        System.out.print("Enter Patient Name: ");
        String name = sc.next();
        System.out.print("Enter Patient Age: ");
        int age = sc.nextInt();
        System.out.print("Enter Patient Gender: ");
        String gender = sc.next();

        try{
            String query = "INSERT INTO patients(name, age, gender)VALUES(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            int affectedRows = ps.executeUpdate();
            if(affectedRows>0) {
                System.out.println("Patient Added Successfully");
            }else{
                System.out.println("Patient Not Added Successfully");

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void viewPatients() throws SQLException {
        String query = "select * from patients";
        try{

            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("Patient List");
            System.out.println("---------------+-------------------------+------+--------+");
            System.out.println("|PATIENT ID     |NAME                     |AGE   |GENDER  |");
            System.out.println("+--------------+-------------------------+------+--------+");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                System.out.printf("|%-16s|%-25s|%-6s|-%-12s|\n", id, name, age, gender);
                System.out.println("+------------+-------------------------+------+--------+");


}




    }catch (SQLException e){
         e.printStackTrace();
}
}


    public boolean getPatientById(int id) {
       String query = "select * from patients where id = ?";
       try{
          PreparedStatement ps = conn.prepareStatement(query);
          ps.setInt(1, id);
          ResultSet rs = ps.executeQuery();
          if(rs.next()) {
              return true;
          }else{
              return  false;
          }

       }catch (SQLException e){
       e.printStackTrace();
    }
    return false;
}


}