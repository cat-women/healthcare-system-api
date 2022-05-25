/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reports;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import shared.DatabaseConnection;

/**
 *
 * @author Dell
 */
public class ReportServices {

    DatabaseConnection db = new DatabaseConnection();
    ReportModal report;

    String tableName = "reports";
    String sql;
    PreparedStatement preparedStatement;
    Connection con;
    ResultSet result;
    Date date;

    public int insertRecord(ReportModal report) {

        DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
        String date = dateFormat.format(report.getNextDate());

        try {
            sql = "INSERT into " + tableName + "(doctor_id,app_id,patience_id,symptoms, result,medicines,nextDate) values (?,?,?,?,?,?,?) ;";
            con = db.database();

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, report.getDoctor_id());
            preparedStatement.setInt(2, report.getApp_id());
            preparedStatement.setInt(3, report.getPatience_id());
            preparedStatement.setString(4, report.getSymptoms());
            preparedStatement.setString(5, report.getResult());
            preparedStatement.setString(6, report.getMedicine());
            preparedStatement.setString(7, date);

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

    public ReportModal getById(int id) throws ParseException {
        report = new ReportModal();
        try {

            con = db.database();
            sql = "Select * from " + tableName + " where id =" + id;
            System.out.println(sql);
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                report.setId(result.getInt("id"));
                report.setId(result.getInt("id"));
                report.setSymptoms(result.getString("symptoms"));
                report.setDoctor_id(result.getInt("doctor_id"));
                report.setPatience_id(result.getInt("patience_id"));
                report.setApp_id(result.getInt("app_id"));
                report.setResult(result.getString("result"));
                report.setMedicine(result.getString("medicines"));
                report.setNextDate(new SimpleDateFormat("mm/dd/yyyy").parse(result.getString("nextDate")));
                return report;
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
        return report;
    }

    public ReportModal getByAppId(int id) throws ParseException {
        report = new ReportModal();
        try {

            con = db.database();
            sql = "Select * from " + tableName + " where app_id =" + id;
            //  System.out.println(sql);
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();

            while (result.next()) {
                report.setId(result.getInt("id"));
                report.setSymptoms(result.getString("symptoms"));
                report.setDoctor_id(result.getInt("doctor_id"));
                report.setPatience_id(result.getInt("patience_id"));
                report.setApp_id(result.getInt("app_id"));
                report.setResult(result.getString("result"));
                report.setMedicine(result.getString("medicines"));
                report.setNextDate(new SimpleDateFormat("mm/dd/yyyy").parse(result.getString("nextDate")));
                return report;
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
        return report;
    }

    public List<ReportModal> getAll() throws ParseException {
        List<ReportModal> reports = new ArrayList<ReportModal>();

        try {

            con = db.database();
            sql = "Select * from " + tableName;
            System.out.println(sql);
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                report = new ReportModal();
                report.setId(result.getInt("id"));
                report.setSymptoms(result.getString("symptoms"));
                report.setDoctor_id(result.getInt("doctor_id"));
                report.setPatience_id(result.getInt("patience_id"));
                report.setApp_id(result.getInt("app_id"));
                report.setResult(result.getString("result"));
                report.setMedicine(result.getString("medicines"));
                report.setNextDate(new SimpleDateFormat("mm/dd/yyyy").parse(result.getString("nextDate")));

                reports.add(report);
            }
            return reports;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("connection !" + ex.getMessage());
            }
        }
        return reports;
    }

    public boolean containsKey(int id) {
        try {
            con = db.database();
            sql = "SELECT EXISTS ( SELECT 1 FROM  " + tableName + "  WHERE  id =" + id + ")";
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            if (result != null) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e) {
            }
        }
        return false;

    }

    public int updateRecord(ReportModal report) {
        DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
        String date = dateFormat.format(report.getNextDate());

        try {
            sql = "UPDATE " + tableName + " set doctor_id = ? , app_id = ?,patience_id =?, symptoms = ?, result = ? , medicines = ?, nextDate = ? where id = ?";

            //System.out.println("sql of update "+sql);
            con = db.database();

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, report.getDoctor_id());
            preparedStatement.setInt(2, report.getApp_id());
            preparedStatement.setInt(3, report.getPatience_id());
            preparedStatement.setString(4, report.getSymptoms());
            preparedStatement.setString(5, report.getResult());
            preparedStatement.setString(6, report.getMedicine());
            preparedStatement.setString(7, date);

            preparedStatement.setInt(8, report.getId());
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

    public int deleteRecord(int id) {
        try {
            sql = "DELETE from " + tableName + " where id = "+id;
            con = db.database();
            preparedStatement = con.prepareStatement(sql);
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

    public List<ReportModal> getByAppId2(String data) throws ParseException {

        List<ReportModal> list = new ArrayList<ReportModal>();
        sql = "SELECT reports.id as id,reports.symptoms as symptoms,reports.medicines as medicines,"
                + "reports.result as result,"
                + "reports.nextDate as date,"
                + "reports.doctor_id as doctor_id , reports.app_id as app_id,"
                + "doctors.name as name, doctors.id as did from reports,doctors  "
                + "WHERE reports.doctor_id = doctors.id and " + data;
        System.out.println("the sql is " + sql);

        try {
            con = db.database();
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                report = new ReportModal();
                report.setId(result.getInt("id"));
                report.setDoctor_id(result.getInt("doctor_id"));
                report.setApp_id(result.getInt("app_id"));
                report.setMedicine(result.getString("medicines"));
                report.setResult(result.getString("result"));
                report.setName(result.getString("name"));
                report.setSymptoms(result.getString("symptoms"));
                report.setNextDate(new SimpleDateFormat("mm/dd/yyyy").parse(result.getString("date")));
                list.add(report);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("there is error " + e);  
        }
        return list;
    }

}
