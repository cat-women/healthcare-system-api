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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * for patient  authentication 
 */
@WebServlet(name = "AppointmentGetById", urlPatterns = {"/AppointmentGetById"})
public class AppointmentGetById extends HttpServlet {
    Appointment appointment ;
     private static final Gson GSON = new GsonBuilder().create();
     AppointmentService service = new AppointmentService();
     
     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        String payload = buffer.toString();
        System.out.println("payload " + payload);
        
        appointment = GSON.fromJson(payload, Appointment.class);
        String c = appointment.getContact();
        String e = appointment.getEmail();
        
        appointment = service.getByEmailContact(c, e);
        if (appointment != null) {
            String json = GSON.toJson(appointment);
            
            response.setHeader("Content-Type", "application/json");
            response.getOutputStream().println(json);
        } else {
            response.getOutputStream().print("no data found ");
        }

    }
    
}
