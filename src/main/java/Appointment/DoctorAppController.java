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
@WebServlet(name = "DoctorAppController", urlPatterns = {"/DoctorAppController"})
public class DoctorAppController extends HttpServlet {

    Appointment Appointment;
    AppointmentService service = new AppointmentService();

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();

        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        String payload = buffer.toString();
        System.out.println("payload of appointment " + payload);
        int id = GSON.fromJson(payload, Appointment.class).getId();
        List<Appointment> appointments = service.getByDotorId(id);
        if (appointments != null) {
            
            String json = GSON.toJson(appointments);
            //System.out.println("app "+json);
            response.setStatus(200);
            //System.out.println("json"+json);
            response.setHeader("Content-Type", "application/json");
            response.getOutputStream().println(json);
        } else {
            response.getOutputStream().print("no data found ");
        }

    }

}
