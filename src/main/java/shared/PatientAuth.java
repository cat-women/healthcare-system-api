/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import Appointment.Appointment;
import Appointment.AppointmentService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dell
 */
@WebServlet(name = "PatientAuth", urlPatterns = {"/PatientAuth"})
public class PatientAuth extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();
    Appointment appointment;
    AppointmentService  service = new AppointmentService();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        String payload = buffer.toString();
        System.out.println("payload " + payload);

        String email = GSON.fromJson(payload, Appointment.class).getEmail();
        String contact = GSON.fromJson(payload, Appointment.class).getContact();
        
        appointment = service.getById(Integer.parseInt(contact));
         System.out.println("userfrom server" + appointment);
        if (appointment != null) {
            System.out.println("email \t" + appointment.getEmail() + "\n contact \t" + appointment.getContact());
            if (email.equals(appointment.getEmail()) && contact.equals(appointment.getContact())) {
                Random rnd = new Random();
                int n = 100000 + rnd.nextInt(9000);
                appointment.setToken(n);
                
                Mailer.send(appointment.getEmail(), "Varification code", "your varification code is \t"+n );  
                
                response.getOutputStream().print(GSON.toJson(appointment));
                response.setStatus(HttpServletResponse.SC_OK);

            } else {
                response.getOutputStream().print("login failed ");
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);

            }
        } else {
            response.getOutputStream().print(404);
        }

    
    }

}
