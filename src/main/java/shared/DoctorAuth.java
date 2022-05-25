/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import Doctor.DoctorModule;
import Doctor.DoctorService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dell
 */
@WebServlet(name = "DoctorAuth", urlPatterns = {"/DoctorAuth"})
public class DoctorAuth extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();
    DoctorModule doctor;
    DoctorService service = new DoctorService();

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

        String email = GSON.fromJson(payload, DoctorModule.class).getEmail();
        int nmc_no = GSON.fromJson(payload, DoctorModule.class).getNmc_no();

        doctor = service.getById(nmc_no);
       // System.out.println("userfrom server" + doctor);
        if (doctor != null) {
            System.out.println("email \t" + doctor.getEmail() + "\n nmc \t" + doctor.getNmc_no());
            if (email.equals(doctor.getEmail()) && nmc_no == doctor.getNmc_no()) {
                Random rnd = new Random();
                int n = 100000 + rnd.nextInt(9000);
                doctor.setToken(n);
                
                Mailer.send(doctor.getEmail(), "Varification code", "your varification code is \t"+n );  
                
                response.getOutputStream().print(GSON.toJson(doctor));
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
