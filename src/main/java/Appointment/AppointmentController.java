/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Appointment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "AppointmentController", urlPatterns = {"/AppointmentController"})
public class AppointmentController extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();
    Appointment appointment;
    AppointmentService service = new AppointmentService();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        String payload = buffer.toString();
        System.out.println("payload" + payload);
        appointment = GSON.fromJson(payload, Appointment.class);

        int result = service.insertRecord(appointment);
        if (result > 0) {
            response.getOutputStream().print("data added successfully ");
            response.setStatus(HttpServletResponse.SC_OK);

        } else {
            response.getOutputStream().print("failed to add ");
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);

        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Appointment> appointment = new ArrayList<Appointment>();
        appointment = service.getAll();
        if (appointment != null) {
            String json = GSON.toJson(appointment);

            response.setStatus(200);
            response.setHeader("Content-Type", "application/json");
            response.getOutputStream().println(json);
        } else {
            response.getOutputStream().print("no data found ");
        }
    }


    
    
    
    
    

}
