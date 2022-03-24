package shared;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Dell
 */
public class DatabaseConnection {

    Connection con;
    Statement stmt = null;

    String url = "jdbc:mysql://localhost:3306/db_healthcaresystem";
    final String username = "root";
    final String password = "";

    public Connection database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            System.out.println("con file " + con);

            // stmt = con.createStatement();
        } catch (SQLException ex) {
            System.out.println("Error !" + ex.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("class not found error  !" + e.getMessage());

        }
        return con;
    }

}
