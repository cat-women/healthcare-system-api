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
            sql = "INSERT into " + tableName + "(doctor_id,app_id,test_name,result) values (?,?,?,?) ;";
            con = db.database();
            System.out.println("this insert case of lab" + sql);
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, lab.getDoctor_id());
            preparedStatement.setInt(2, lab.getApp_id());
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
            sql = "select * from " + tableName + " where app_id = " + id;
            //System.out.println(sql);
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                lab.setId(result.getInt("id"));
                lab.setDoctor_id(result.getInt("doctor_id"));
                lab.setApp_id(result.getInt("app_id"));
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

    public List<LabModal> getAll1() {
        List<LabModal> labs = new ArrayList<LabModal>();

        try {

            con = db.database();
            sql = "SELECT labs.id as id,labs.test_name as test_name,labs.result as result,"
                    + "labs.doctor_id as doctor_id, labs.app_id as app_id, doctors.name as name, doctors.id as did "
                    + " from " + tableName + " ,doctors WHERE labs.doctor_id = doctors.id and labs.result is NULL";

            System.out.println(sql);
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                lab = new LabModal();
                lab.setId(result.getInt("id"));
                lab.setDoctor_id(result.getInt("doctor_id"));
                lab.setApp_id(result.getInt("app_id"));
                lab.setTest_name(result.getString("test_name"));
                lab.setResult(result.getString("result"));
                lab.setDoctorName(result.getString("name"));
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

    public LabModal getByApp_id(int id) {
        lab = new LabModal();
        try {

            con = db.database();
            sql = "Select * from " + tableName + " where app_id =" + id;
            System.out.println(sql);
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                lab.setId(result.getInt("id"));
                lab.setDoctor_id(result.getInt("doctor_id"));
                lab.setApp_id(result.getInt("app_id"));
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
                lab.setApp_id(result.getInt("app_id"));
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

    public int updateRecord(LabModal lab) {
        try {

            con = db.database();
            sql = "UPDATE  " + tableName + " set doctor_id = ?, app_id = ?, test_name = ?, result = ? where id =  ?";
            System.out.println("sql of update lab " + sql);
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, lab.getDoctor_id());
            preparedStatement.setInt(2, lab.getApp_id());
            preparedStatement.setString(3, lab.getTest_name());
            preparedStatement.setString(4, lab.getResult());
            preparedStatement.setInt(5, lab.getId());

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
            sql = "DELETE from " + tableName + " where id =  " + id;
            con = db.database();
            preparedStatement = con.prepareStatement(sql);
            System.out.println("the delete sql :" + sql);
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

    public List<LabModal> getByAppId(int id) {
        List<LabModal> list = new ArrayList<LabModal>();
        sql = "SELECT labs.id as id,labs.test_name as test_name,labs.result as result,"
                + "labs.doctor_id, labs.app_id, doctors.name as name, doctors.id as did "
                + " from " + tableName + " ,doctors WHERE labs.doctor_id = doctors.id and labs.app_id =  " + id;
        //System.out.println("the sql is " + sql);
        try {
            con = db.database();
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                lab = new LabModal();
                lab.setId(result.getInt("id"));
                lab.setDoctor_id(result.getInt("doctor_id"));
                lab.setApp_id(result.getInt("app_id"));
                lab.setTest_name(result.getString("test_name"));
                lab.setResult(result.getString("result"));
                lab.setName(result.getString("name"));
                lab.setPatience_id(result.getInt("did"));
                list.add(lab);
            }
            return list;
        } catch (SQLException e) {
            //
        }
        return list;
    }

}
