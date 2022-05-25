/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Appointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import shared.DatabaseConnection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Dell
 */
public class AppointmentService {

    DatabaseConnection db = new DatabaseConnection();
    String tableName = "appointment";
    String sql;
    PreparedStatement preparedStatement;
    Connection con;
    ResultSet result;
    Appointment appointment;

    public int insertRecord(Appointment app) {
        try {
            sql = "Insert into " + tableName + " (name,contact, email,department,date, doctor_id,patience_id,patient_type,last_visite_date) "
                    + "values (?,?,?,?,?,?,?,?,?);";
            con = db.database();

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, app.getName());
            preparedStatement.setString(2, app.getContact());
            preparedStatement.setString(3, app.getEmail());
            preparedStatement.setString(4, app.getDepartment());
            preparedStatement.setString(5, app.getDate());
            preparedStatement.setInt(6, app.getDoctor_id());
            preparedStatement.setInt(7, app.getPatience_id());
            preparedStatement.setString(8, app.getPatient_type());
            preparedStatement.setString(9, app.getLast_viste_date());

            int result = preparedStatement.executeUpdate();

            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e) {
            }
        }
        return 0;
    }

    public Appointment getById(int id) {
        appointment = new Appointment();
        try {

            con = db.database();
            sql = "Select * from " + tableName + " where id =" + id + " or contact =" + id;
            System.out.println(sql);
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                appointment.setId(result.getInt("id"));
                appointment.setName(result.getString("name"));
                appointment.setEmail(result.getString("email"));
                appointment.setDepartment(result.getString("department"));
                appointment.setContact(result.getString("contact"));
                appointment.setDoctor_id(result.getInt("doctor_id"));
                appointment.setPatience_id(result.getInt("patience_id"));
                appointment.setDate(result.getString("date"));
                appointment.setPatient_type(result.getString("patient_type"));
                appointment.setLast_viste_date(result.getString("last_visite_date"));

                return appointment;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("connection !" + ex.getMessage());
            }
        }
        return appointment;
    }

    public Appointment getByEmailContact(String c, String e) {
        appointment = new Appointment();
        try {

            con = db.database();
            sql = "Select * from " + tableName + " where  email  = '" + e + "' and  contact = '" + c + " ' ";
            System.out.println(sql);
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                appointment.setId(result.getInt("id"));
                appointment.setName(result.getString("name"));
                appointment.setEmail(result.getString("email"));
                appointment.setDepartment(result.getString("department"));
                appointment.setContact(result.getString("contact"));
                appointment.setDoctor_id(result.getInt("doctor_id"));
                appointment.setPatience_id(result.getInt("patience_id"));
                appointment.setDate(result.getString("date"));
                appointment.setPatient_type(result.getString("patient_type"));
                //appointment.setLast_viste_date(result.getString("last_visite_date"));
                appointment.setLast_viste_date(result.getString("last_visite_date"));
                return appointment;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("connection !" + ex.getMessage());
            }
        }
        return appointment;
    }

    public List<Appointment> getByDotorId(int id) {

        List<Appointment> appointments = new ArrayList<Appointment>();

        try {
            con = db.database();
            sql = "Select * from " + tableName + " where doctor_id =" + id;
            System.out.println(sql);
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                appointment = new Appointment();
                appointment.setId(result.getInt("id"));
                appointment.setName(result.getString("name"));
                appointment.setEmail(result.getString("email"));
                appointment.setDepartment(result.getString("department"));
                appointment.setContact(result.getString("contact"));
                appointment.setDoctor_id(result.getInt("doctor_id"));
                appointment.setPatience_id(result.getInt("patience_id"));
                appointment.setDate(result.getString("date"));
                appointment.setPatient_type(result.getString("patient_type"));
                appointment.setLast_viste_date(result.getString("last_visite_date"));

                appointments.add(appointment);
            }
            return appointments;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("connection !" + ex.getMessage());
            }
        }
        return appointments;
    }

    public List<Appointment> getAll() {
        List<Appointment> appointments = new ArrayList<Appointment>();

        try {

            con = db.database();
            sql = "Select * from " + tableName;
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                appointment = new Appointment();
                appointment.setId(result.getInt("id"));
                appointment.setName(result.getString("name"));
                appointment.setEmail(result.getString("email"));
                appointment.setDepartment(result.getString("department"));
                appointment.setContact(result.getString("contact"));
                appointment.setDoctor_id(result.getInt("doctor_id"));
                appointment.setPatience_id(result.getInt("patience_id"));
                appointment.setDate(result.getString("date"));
                appointment.setPatient_type(result.getString("patient_type"));
                appointment.setLast_viste_date(result.getString("last_visite_date"));

                appointments.add(appointment);
            }
            return appointments;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("connection !" + ex.getMessage());
            }
        }
        return appointments;
    }

    public List<Appointment> getAllByDate(int a, int b) {
        List<Appointment> appointments = new ArrayList<Appointment>();

        try {

            con = db.database();
            sql = "Select * from " + tableName + " limit " + a + " ," + b;
            sql = "SELECT appointment.id as id,"
                    + "appointment.name as name,"
                    + "appointment.contact as contact,"
                    + "appointment.email as email,"
                    + "appointment.department as department,"
                    + "appointment.date as date,"
                    + "appointment.patient_type as type,"                    
                    + "appointment.isChecked as isChecked,"
                    + "appointment.doctor_id as doctor_id ,"
                    + "doctors.name as doctorName, doctors.id as did from appointment,doctors  "
                    + "WHERE appointment.doctor_id = doctors.id limit " + a + " ," + b;
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                appointment = new Appointment();
                appointment.setId(result.getInt("id"));
                appointment.setName(result.getString("name"));
                appointment.setEmail(result.getString("email"));
                appointment.setDepartment(result.getString("department"));
                appointment.setContact(result.getString("contact"));
                appointment.setDoctor_id(result.getInt("doctor_id"));
                appointment.setDate(result.getString("date"));
                appointment.setPatient_type(result.getString("type"));
               appointment.setDoctor_name(result.getString("doctorName"));
               appointment.setIsChecked(result.getBoolean("isChecked"));

                appointments.add(appointment);
            }
            return appointments;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("connection !" + ex.getMessage());
            }
        }
        return appointments;
    }

    public int deleteRecord(long id) {
        try {
            sql = "DELETE from " + tableName + " where id = ? ";
            con = db.database();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e) {
            }
        }
        return 0;
    }

    public int update(Appointment app) {
        try {
            sql = "UPDATE  " + tableName + " set name = ?, contact = ?,email = ?,department = ?, date = ?, doctor_id = ?,"
                    + "patient_type = ?,last_visite_date = ? where id = ?";
            con = db.database();

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, app.getName());
            preparedStatement.setString(2, app.getContact());
            preparedStatement.setString(3, app.getEmail());
            preparedStatement.setString(4, app.getDepartment());
            preparedStatement.setString(5, app.getDate());
            preparedStatement.setInt(6, app.getDoctor_id());
            preparedStatement.setString(7, app.getPatient_type());
            preparedStatement.setString(8, app.getLast_viste_date());
            preparedStatement.setInt(9, app.getId());

            int result = preparedStatement.executeUpdate();
            return result;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e) {
            }
        }
        return 0;
    }

    public int getRow() {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm/dd/yyyy");
//        LocalDateTime now = LocalDateTime.now();
//
//        DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
//        String date = dateFormat.format(now);

        try {
            con = db.database();
            sql = "SELECT COUNT(id) as row from " + tableName;
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {

                return result.getInt("row");

            }

        } catch (SQLException e) {
            //
        }
        return 0;
    }

}
