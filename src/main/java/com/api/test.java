/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dell
 */
@WebServlet(name = "test", urlPatterns = {"/test"})
public class test extends HttpServlet {

    Connection con;
    Statement stmt = null;
    String sql;
    PreparedStatement preparedStatement;
    ResultSet result;
    String tableName = "users";
    String url = "jdbc:mysql://localhost:3306/db_vaccinationsystem";
    final String username = "root";
    final String password = "";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        writer.print("hello");
        resp.setContentType("application/json");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            System.out.println("con file " + con);
            sql = "Select * from " + tableName;
            System.out.println(sql);

            preparedStatement = con.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

            while (result.next()) {
                writer.print("name" +result.getString("name"));
//                String res = gson.toJson(result);
//                writer.print(res);
//                writer.flush();

            }

            // stmt = con.createStatement();
        } catch (SQLException ex) {
            System.out.println("Error !" + ex.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("class not found error  !" + e.getMessage());

        }

    }

}
