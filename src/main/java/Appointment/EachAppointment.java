/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Appointment;

import Doctor.DoctorModule;
import Doctor.DoctorService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dell
 */
@WebServlet(name = "EachAppointment", urlPatterns = {"/EachAppointment"})
public class EachAppointment extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();
    Appointment appointment;

    AppointmentService service = new AppointmentService();
    DoctorService ds = new DoctorService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queryString = request.getQueryString();
        //System.out.println("query is " + queryString);

        String result = java.net.URLDecoder.decode(queryString, StandardCharsets.UTF_8.name());

        result = result.trim();
        String[] data = result.split("&", -2);
        String first = data[0].replaceAll("[^0-9]", "");
        String second = data[1].replaceAll("[^0-9]", "");

        List<Appointment> appointments = new ArrayList<Appointment>();

        appointments = service.getAllByDate(Integer.parseInt(first), Integer.parseInt(second));

        if (appointments != null) {

            String json = GSON.toJson(appointments);
            response.setHeader("Content-Type", "application/json");
            response.getOutputStream().println(json);
            response.setStatus(response.SC_OK);

        } else {
            response.setStatus(response.SC_NO_CONTENT);
        }

    }

}
