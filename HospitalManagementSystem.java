package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url="jdbc:mysql://localhost:3306/hospital";

    private static final String username = "root";

    private static final String password = "Himanshu@1234";


    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner sc=new Scanner(System.in);
        try{
            Connection con= DriverManager.getConnection(url,username,password);
            Patient patient = new Patient(con,sc);
            Doctor doctor = new Doctor(con);
            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM: ");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. View Doctor");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choice: ");
                int choice=sc.nextInt();
                switch(choice){
                    case 1:
                        // Add Patient
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        System.out.println();
                        // View Patient
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        // View Doctor
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        // Book Appointment
                        bookAppointment(patient,doctor,con,sc);
                        System.out.println();
                        break;
                    case 5:
                        return;
                        default:
                            System.out.println("Enter valid choice");
                            break;
                }

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor,Connection con,Scanner sc){
        System.out.println("Enter Patient ID: ");
        int patientID=sc.nextInt();
        System.out.println("Enter Doctor ID: ");
        int doctorID=sc.nextInt();
        System.out.println("Enter Appointment Date (YYYY-MM-DD): ");
        String appointmentDate=sc.next();
        if(patient.getPatientById(patientID) && doctor.getDoctorById(doctorID)){
            if(checkDoctorAvailability(doctorID, appointmentDate, con)){
                String appointmentQuery =" INSERT INTO appointments(patient_ID,doctor_ID,appointment_date) VALUES(?,?,?) ";
                try{
                    PreparedStatement pst=con.prepareStatement(appointmentQuery);
                    pst.setInt(1,patientID);
                    pst.setInt(2,doctorID);
                    pst.setString(3,appointmentDate);
                    int rowsAffected=pst.executeUpdate();
                    if(rowsAffected>0){
                        System.out.println("Appointment Booked Successfully");
                    }else{
                        System.out.println("Appointment Booking Failed");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }

            }else{
                System.out.println("Doctor is not available");
            }
        }else{
            System.out.println("Patient or Doctor not found");
        }

    }



       public static boolean checkDoctorAvailability(int doctorID,String appointmentDate, Connection con){
        String query = " SELECT COUNT(*) FROM appointments WHERE doctor_ID=? AND appointment_date=?";
        try{
            PreparedStatement pst=con.prepareStatement(query);
            pst.setInt(1,doctorID);
            pst.setString(2,appointmentDate);
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                int count=rs.getInt(1);
                if(count==0){
                    return true;
                }else{
                    return false;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
       }

}



