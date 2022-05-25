/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Doctor;

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
public class DoctorService {

    DatabaseConnection db = new DatabaseConnection();
    DoctorModule doctor;

    String tableName = "doctors";
    String sql;
    PreparedStatement preparedStatement;
    Connection con;
    ResultSet result;

    public int insertRecord(DoctorModule doctor) {
        String time = doctor.getFrom()+" to "+ doctor.getTo();
        System.out.println("doctor tiem is "+time );
        System.out.println("nmc c is " + doctor.getNmc_no());
        if (isNMC(doctor.getNmc_no())) {
            return -1;
        } else {

            try {
                sql = "Insert into " + tableName + " (name,citizenship, email,contact,address, specialization,nmc_no,qualification,charge,image,isAvailable,time) "
                        + "values (?,?,?,?,?,?,?,?,?,?,?,?);";
                con = db.database();

                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, doctor.getName());
                preparedStatement.setString(2, doctor.getCitizenship());
                preparedStatement.setString(3, doctor.getEmail());
                preparedStatement.setString(4, doctor.getContact());
                preparedStatement.setString(5, doctor.getAddress());
                preparedStatement.setString(6, doctor.getSpecialization());
                preparedStatement.setInt(7, doctor.getNmc_no());
                preparedStatement.setString(8, doctor.getQualification());
                preparedStatement.setInt(9, doctor.getCharge());
                preparedStatement.setString(10, doctor.getImage());
                preparedStatement.setBoolean(11, doctor.getIsAvailable());
                preparedStatement.setString(12, time);
                int result = preparedStatement.executeUpdate();
                //System.out.println("the inset resut is " + result);
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

    }

    public DoctorModule getById(int id) {
        doctor = new DoctorModule();

        con = db.database();
        sql = "Select * from " + tableName + " where id =" + id + " or nmc_no =" + id;
       // System.out.println(sql);

        try {
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                doctor.setId(result.getInt("id"));
                doctor.setName(result.getString("name"));
                doctor.setEmail(result.getString("email"));
                doctor.setContact(result.getString("contact"));
                doctor.setAddress(result.getString("address"));
                doctor.setQualification(result.getString("qualification"));
                doctor.setSpecialization(result.getString("specialization"));
                doctor.setCharge(result.getInt("charge"));
                doctor.setNmc_no(result.getInt("nmc_no"));
                doctor.setIsAvailable(result.getBoolean("isAvailable"));
                doctor.setCitizenship(result.getString("citizenship"));
                doctor.setImage(result.getString("image"));
                doctor.setTime(result.getString("time"));
                return doctor;
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
        return doctor;
    }

    public List<DoctorModule> getAll(int start,int end) {
        List<DoctorModule> doctors = new ArrayList<DoctorModule>();

        try {

            con = db.database();
            sql = "Select * from " + tableName+ " limit "+start+" ,"+ end;
            //System.out.println(sql);
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                doctor = new DoctorModule();
                doctor.setId(result.getInt("id"));
                doctor.setName(result.getString("name"));
                doctor.setEmail(result.getString("email"));
                doctor.setContact(result.getString("contact"));
                doctor.setAddress(result.getString("address"));
                doctor.setQualification(result.getString("qualification"));
                doctor.setSpecialization(result.getString("specialization"));
                doctor.setCharge(result.getInt("charge"));
                doctor.setNmc_no(result.getInt("nmc_no"));
                doctor.setIsAvailable(result.getBoolean("isAvailable"));
                doctor.setCitizenship(result.getString("citizenship"));
                doctor.setImage(result.getString("image"));
                doctor.setTime(result.getString("time"));
                doctors.add(doctor);
            }
            return doctors;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("connection !" + ex.getMessage());
            }
        }
        return doctors;
    }

    public boolean containsKey(int id) {
        try {
            con = db.database();
            sql = "SELECT EXISTS ( SELECT 1 FROM " + tableName + " WHERE id = '" + id + "')";
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

    public boolean isNMC(int id) {
        try {
            con = db.database();
            //sql = "SELECT EXISTS ( SELECT 1 FROM " + tableName + " WHERE nmc_no     = " + id + ")";

            sql = "select * from  " + tableName + "  where nmc_no = " + id;
            //System.out.println("is nmc sql " + sql);
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            //System.out.println("result is "+result);
            while (result.next()) {
                if (result.getInt("nmc_no") > 0) {
                    System.out.println("nmc no from result set " + result.getInt("nmc_no"));
                    return true;
                } else {
                    return false;
                }
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

    public int updateRecord(DoctorModule doctor, int id) {
        try {
            sql = " update " + tableName + " ";
            con = db.database();

            Boolean istrue = false;

            if (doctor.getName() != null) {
                sql = sql + " set name = " + "'" + doctor.getName() + "'";
                istrue = true;
            }

            if (doctor.getCitizenship() != null) {
                if (istrue) {
                    istrue = true;
                    sql = sql.concat(", citizenship = " + "'" + doctor.getCitizenship() + "'");
                } else {
                    istrue = false;
                    sql = sql.concat("set citizenship =" + "'" + doctor.getCitizenship() + "'");

                }
            }

            if (doctor.getEmail() != null) {
                if (istrue) {
                    istrue = true;
                    sql = sql.concat(", email = " + "'" + doctor.getEmail() + "'");
                } else {
                    istrue = false;
                    sql = sql.concat("set email = " + "'" + doctor.getEmail() + "'");

                }
            }

            if (doctor.getContact() != null) {
                if (istrue) {
                    istrue = true;
                    sql = sql.concat(", contact =" + "'" + doctor.getContact() + "'");
                } else {
                    istrue = false;
                    sql = sql.concat("set contact =" + "'" + doctor.getContact() + "'");

                }
            }

            if (doctor.getAddress() != null) {
                if (istrue) {
                    istrue = true;
                    sql = sql.concat(", address =" + "'" + doctor.getAddress() + "'");
                } else {
                    istrue = false;
                    sql = sql.concat(" set address =" + "'" + doctor.getAddress() + "'");

                }
            }

            if (doctor.getSpecialization() != null) {
                if (istrue) {
                    istrue = true;
                    sql = sql.concat(", specialization =" + "'" + doctor.getSpecialization() + "'");
                } else {
                    istrue = false;
                    sql = sql.concat("set specialization =" + "'" + doctor.getSpecialization() + "'");

                }
            }

            if (doctor.getContact() != null) {

                if (istrue) {
                    sql = sql.concat(", contact =" + "'" + doctor.getContact() + "'");
                    istrue = false;
                } else {
                    sql = sql.concat(" set contact =" + "'" + doctor.getContact() + "'");
                    istrue = false;
                }
            }

            if (doctor.getNmc_no() != 0) {
                if (istrue) {
                    sql = sql.concat(", nmc_no =" + "'" + doctor.getNmc_no() + "'");
                    istrue = true;
                    System.out.println("sql" + sql);

                } else {
                    sql = sql.concat(" set nmc_no =" + "'" + doctor.getNmc_no() + "'");
                    istrue = false;

                }
            }

            if (doctor.getQualification() != null) {

                if (istrue) {
                    sql = sql.concat(", qualification =" + "'" + doctor.getQualification() + "'");
                    istrue = true;
                } else {
                    sql = sql.concat(" set qualification =" + "'" + doctor.getQualification() + "'");
                    istrue = false;
                }
            }

            if (doctor.getCharge() != 0) {

                if (istrue) {
                    sql = sql.concat(", charge =" + "'" + doctor.getCharge() + "'");
                    System.out.println("sql" + sql);

                    istrue = true;
                } else {
                    sql = sql.concat(" set charge =" + "'" + doctor.getCharge() + "'");
                    istrue = false;
                }
            }
            if (doctor.getIsAvailable() != null) {

                if (istrue) {
                    sql = sql.concat(", isAvailable =" + "'" + doctor.getIsAvailable() + "'");
                    istrue = true;
                } else {
                    sql = sql.concat(" set isAvailable =" + "'" + doctor.getIsAvailable() + "'");
                    istrue = false;
                }
            }
            sql = sql.concat(" where id =" + id);

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

    public List<DoctorModule> findDoctor() {

        List<DoctorModule> doctors = new ArrayList<DoctorModule>();

        try {
            con = db.database();
            sql = "Select id,name,nmc_no,charge,time from " + tableName + " order by name ";
            //System.out.println(sql);
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                doctor = new DoctorModule();
                doctor.setId(result.getInt("id"));
                doctor.setName(result.getString("name"));
                doctor.setCharge(result.getInt("charge"));
                doctor.setNmc_no(result.getInt("nmc_no"));
                doctor.setTime(result.getString("time"));
                doctors.add(doctor);
            }
            return doctors;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("connection !" + ex.getMessage());
            }
        }
        return doctors;

    }
    
    
    
    public int getRow() {
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
