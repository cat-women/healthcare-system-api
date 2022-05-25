/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Doctor;

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
import shared.DatabaseConnection;

/**
 *
 * @author Dell
 */
@WebServlet(name = "FindDoctorController", urlPatterns = {"/FindDoctorController"})
public class FindDoctorController extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    DoctorService service = new DoctorService();
    DatabaseConnection db = new DatabaseConnection();
    DoctorModule doctor;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<DoctorModule> doctors = new ArrayList<DoctorModule>();
        doctors = service.findDoctor();
        if (doctors != null) {
            String json = GSON.toJson(doctors);
            response.setHeader("Content-Type", "application/json");
            response.getOutputStream().println(json);
            response.setStatus(HttpServletResponse.SC_OK);

        } else {
            response.getOutputStream().print("no data found ");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        String payload = buffer.toString();
        System.out.println("payload" + payload);
        int id = GSON.fromJson(payload, DoctorModule.class).getId();

        doctor = service.getById(id);
        if (doctor != null) {
            String json = GSON.toJson(doctor);
            //System.out.println("doctotr is "+json);
            response.setStatus(200);
            response.setHeader("Content-Type", "application/json");
            response.getOutputStream().println(json);
        } else {
            response.getOutputStream().print("no data found ");
        }

    }

}
