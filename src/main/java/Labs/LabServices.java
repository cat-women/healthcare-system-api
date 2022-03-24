/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Labs;

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
public class LabServices {

    DatabaseConnection db = new DatabaseConnection();
    LabModal lab;

    String tableName = "labs";
    String sql;
    PreparedStatement preparedStatement;
    Connection con;
    ResultSet result;

    public int insertRecord(LabModal lab) {

        try {
            sql = "INSERT into " + tableName + "(doctor_id,patience_id,test_name,result) values (?,?,?,?) ;";
            con = db.database();

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, lab.getDoctor_id());
            preparedStatement.setInt(2, lab.getPatience_id());
            preparedStatement.setString(3, lab.getTest_name());
            preparedStatement.setString(4, lab.getResult());

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

    public LabModal getById(int id) {
        lab = new LabModal();
        try {

            con = db.database();
            sql = "Select * from " + tableName + " where id =" + id;
            System.out.println(sql);
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                lab.setId(result.getInt("id"));
                lab.setDoctor_id(result.getInt("doctor_id"));
                lab.setPatience_id(result.getInt("patience_id"));
                lab.setTest_name(result.getString("test_name"));
                lab.setResult(result.getString("result"));
                return lab;
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
        return lab;
    }

    public List<LabModal> getAll() {
        List<LabModal> labs = new ArrayList<LabModal>();

        try {

            con = db.database();
            sql = "Select * from " + tableName;
            System.out.println(sql);
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                lab = new LabModal();
                lab.setId(result.getInt("id"));
                lab.setDoctor_id(result.getInt("doctor_id"));
                lab.setPatience_id(result.getInt("patience_id"));
                lab.setTest_name(result.getString("test_name"));
                lab.setResult(result.getString("result"));
                labs.add(lab);
            }
            return labs;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("connection !" + ex.getMessage());
            }
        }
        return labs;
    }

    public boolean containsKey(int id) {
        try {
            con = db.database();
            sql = "SELECT EXISTS ( SELECT 1 FROM  " + tableName + "  WHERE  id =" + id + ")";
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            System.out.println("contains key result " + result);
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

    public int updateRecord(LabModal lab, int id) {
        try {
            sql = " update " + tableName;

            con = db.database();

            if (lab.getTest_name() != null) {
                sql = sql + " set test_name = " + "'" + lab.getTest_name() + "'";
            }
            if (lab.getResult() != null) {
                sql = (lab.getResult() != null && lab.getTest_name() != null)
                        ? sql.concat(", result = " + "'" + lab.getResult() + "'")
                        : sql.concat(" set result " + "'" + lab.getResult() + "'");
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
