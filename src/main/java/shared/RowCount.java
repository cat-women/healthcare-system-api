/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import Appointment.AppointmentService;
import Doctor.DoctorService;
import Users.UserService;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dell
 */
@WebServlet(name = "RowCount", urlPatterns = {"/RowCount"})
public class RowCount extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queryString = request.getQueryString();
        //System.out.println("query is " + queryString);

        String result = java.net.URLDecoder.decode(queryString, StandardCharsets.UTF_8.name());
        System.out.println("the rsult is " + result);
        int row;
        if (result.equals("users")) {
            Users.UserService u = new UserService();
            row = u.getRow();

            response.getOutputStream().println(row);
            response.setStatus(response.SC_OK);
        } else if (result.equals("doctors")) {
            Doctor.DoctorService d = new DoctorService();
            row = d.getRow();

            response.getOutputStream().println(row);
            response.setStatus(response.SC_OK);
        } else if (result.equals("appointments")) {
          Appointment.AppointmentService a = new AppointmentService();
            row = a.getRow();

            response.getOutputStream().println(row);
            response.setStatus(response.SC_OK);
        } else {
            response.setStatus(response.SC_NO_CONTENT);
        }
    }

}
