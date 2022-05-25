/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import shared.DatabaseConnection;

/**
 *
 * @author Dell
 */
public class UserService {

    String tableName = "users";
    String sql;
    PreparedStatement preparedStatement;
    DatabaseConnection db = new DatabaseConnection();
    Connection con;
    ResultSet result;

    public int insertRecord(UserModal user) {
        try {
            sql = "Insert into " + tableName + " (name,username, email,password,token, date) "
                    + "values (?,?,?,?,?,?);";
            con = db.database();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, user.getToken());
            preparedStatement.setDate(6, user.getDate());

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

    public UserModal getById(int id) {
        UserModal user = new UserModal();
        try {
            con = db.database();
            sql = "Select * from " + tableName + " where id =" + id;
            System.out.println(sql);
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {

                user.setId(result.getInt("id"));
                user.setName(result.getString("name"));
                user.setUsername(result.getString("username"));
                user.setEmail(result.getString("email"));
                user.setToken(result.getInt("token"));
                user.setDate(result.getDate("date"));
                user.setPassword(result.getString("password"));
                return user;
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
        return user;
    }

    public ArrayList<UserModal> getAll(int start, int end) {

        ArrayList<UserModal> list = new ArrayList<UserModal>();
        try {
            con = db.database();
            sql = "Select * from " + tableName + " limit " + start + "," + end;
            System.out.println(sql);
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                UserModal user = new UserModal();
                user.setId(result.getInt("id"));
                user.setName(result.getString("name"));
                user.setUsername(result.getString("username"));
                user.setEmail(result.getString("email"));
                user.setToken(result.getInt("token"));
                user.setDate(result.getDate("date"));
                user.setPassword(result.getString("password"));
                user.setUserType(result.getString("userType"));
                list.add(user);
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
        return list;
    }

    public boolean containsKey(int id) {
        try {
            con = db.database();
            sql = "SELECT EXISTS ( SELECT 1 FROM  " + tableName + "  WHERE  id =" + id + ")";
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            System.out.println("contains key" + result);
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

    public int updateRecord(UserModal user, int id) {
        UserModal temp = getById(id);
        int count;

        try {
            sql = " update " + tableName;
            count = sql.length();

            con = db.database();
            boolean isUsernameAndUser = (user.name != null || user.username != null);
            if (user.name != null) {
                sql = sql + " set name = " + "'" + user.name + "'";
            }
            if (user.username != null) {
                sql = (user.username != null && user.name != null)
                        ? sql.concat(", username =  " + "'" + user.username + "'")
                        : sql.concat(" set username =  " + "'" + user.username + "'");
            }
            if (user.email != null) {
                sql = (user.email != null && (user.name != null || user.username != null))
                        ? sql.concat(", eamil =  " + "'" + user.email + "'")
                        : sql.concat(" set email =  " + "'" + user.email + "'");
            }

            if (user.password != null) {
                sql = (user.password != null && (user.email != null || user.username != null || user.name != null))
                        ? sql.concat(", password =  " + "'" + user.password + "'")
                        : sql.concat(" set password =  " + "'" + user.password + "'");
            }

//            if (user.token != null) {
//                sql = (user.token != null && (user.password != null || user.email != null || user.username != null || user.name != null))
//                        ? sql.concat(", token =  " + "'" + user.token + "'")
//                        : sql.concat(" set token =  " + "'" + user.token + "'");
//            }
            if (user.date != null) {
                sql = (user.date != null && (user.password != null || user.email != null || user.username != null || user.name != null))
                        ? sql.concat(", token =  " + "'" + user.date + "'")
                        : sql.concat(" set token =  " + "'" + user.date + "'");
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
            sql = "DELETE from " + tableName + " where id =  "+id;
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

    public UserModal authenticate(String data) throws SQLException {
        UserModal user = new UserModal();
        try {
            con = db.database();

            sql = "SELECT EXISTS ( SELECT 1 FROM  " + tableName + "  WHERE  email = '" + data + " ')";
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            if (result != null) {

                sql = "Select * from " + tableName + "  WHERE  email = '" + data + " '";
                //System.out.println(sql);
                preparedStatement = con.prepareStatement(sql);
                result = preparedStatement.executeQuery();
                while (result.next()) {

                    user.setId(result.getInt("id"));
                    user.setName(result.getString("name"));
                    user.setUsername(result.getString("username"));
                    user.setEmail(result.getString("email"));
                    user.setToken(result.getInt("token"));
                    user.setDate(result.getDate("date"));
                    user.setPassword(result.getString("password"));
                    user.setUserType(result.getString("userType"));

                    return user;
                }
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
        return user;
    }

    public UserModal getByuserName(int id, String name) {
        UserModal user = new UserModal();
        try {
            con = db.database();
            sql = "Select id,name,username,email from " + tableName + " where id =" + id + " or username = '" + name + "'";
            System.out.println(sql);
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {

                user.setId(result.getInt("id"));
                user.setName(result.getString("name"));
                user.setUsername(result.getString("username"));
                user.setEmail(result.getString("email"));
                return user;
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
        return user;
    }

    public int getRow() {
        try {
            con = db.database();
            sql = "SELECT COUNT(id) as row from " + tableName;
            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                UserModal user = new UserModal();
                user.setId(result.getInt("row"));
                return user.getId();

            }

        } catch (SQLException e) {
            //
        }
        return 0;
    }

}
