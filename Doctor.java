package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection conn;


    public Doctor(Connection conn) {
        this.conn = conn;

    }




    public void viewDoctors() throws SQLException {
        String query = "select * from doctors";
        try{

            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("Doctors List");
            System.out.println("-------------+---------------------+-------------------------+");
            System.out.println("|Doctor ID   |NAME                 |Specialization           |");
            System.out.println("+------------+---------------------+-------------------------+");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
               String specialization = rs.getString("specialization");
                System.out.printf("|%-12s|%-19s|%-24s|\n", id, name, specialization);
                System.out.println("+------------+---------------------+-------------------------+");


            }




        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public boolean getDoctorById(int id) {
        String query = "select * from doctors where id = ?";
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
