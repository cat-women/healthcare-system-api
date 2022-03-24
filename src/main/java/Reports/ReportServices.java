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
import java.util.ArrayList;
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

    public int insertRecord(ReportModal report) {

        try {
            sql = "INSERT into " + tableName + "(doctor_id,patience_id,medicines,nextDate) values (?,?,?,?) ;";
            con = db.database();

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, report.getDoctor_id());
            preparedStatement.setInt(2, report.getPatience_id());
            preparedStatement.setString(3, report.getMedicine());
            preparedStatement.setDate(4, (report.getNextDate()));

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

    public ReportModal getById(int id) {
        report = new ReportModal();
        try {

            con = db.database();
            sql = "Select * from " + tableName + " where id =" + id;
            System.out.println(sql);
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                report.setId(result.getInt("id"));
                report.setDoctor_id(result.getInt("doctor_id"));
                report.setPatience_id(result.getInt("patience_id"));
                report.setMedicine(result.getString("medicines"));
                report.setNextDate(result.getDate("nextDate"));
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

    public List<ReportModal> getAll() {
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
                report.setDoctor_id(result.getInt("doctor_id"));
                report.setPatience_id(result.getInt("patience_id"));
                report.setMedicine(result.getString("medicines"));
                report.setNextDate(result.getDate("nextDate"));
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

    public int updateRecord(ReportModal report, int id) {
        int count;

        try {
            sql = " update " + tableName;

            con = db.database();
            if (report.getMedicine() != null) {
                sql = sql + " set medicines = " + "'" + report.getMedicine() + "'";
            }
            if (report.getNextDate() != null) {
                sql = (report.getNextDate() != null && report.getMedicine() != null)
                        ? sql.concat(", nextDate =  " + "'" + report.getNextDate() + "'")
                        : sql.concat(" set nextDate =  " + "'" + report.getNextDate() + "'");
            }

            sql = sql + " where id = " + id;

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

}
