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
@WebServlet(name = "DoctorController", urlPatterns = {"/DoctorController"})
public class DoctorController extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    DoctorService service = new DoctorService();
    DoctorModule doctor;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //doctor = new DoctorModule();

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        String payload = buffer.toString();
        System.out.println("payload" + payload);
        doctor = GSON.fromJson(payload, DoctorModule.class);

        int result = service.insertRecord(doctor);
        if (result == -1) {
            response.getOutputStream().print("Doctor with this NMC no already exit ");
        } else if (result > 0) {
            response.getOutputStream().print("data added successfully ");
            response.setStatus(HttpServletResponse.SC_OK);

        } else {
            response.getOutputStream().print("failed to add ");
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);

        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String queryString = request.getQueryString();
        //System.out.println("query is " + queryString);

        String result = java.net.URLDecoder.decode(queryString, StandardCharsets.UTF_8.name());
        char f = result.charAt(9);
        int start = Character.getNumericValue(f);

        char t = result.charAt(result.length() - 1);
        int end = Character.getNumericValue(t);
        
        start  = 0;
        end =10;
        List<DoctorModule> doctors = new ArrayList<DoctorModule>();
        doctors = service.getAll(start,end);
        if (doctors != null) {
            String json = GSON.toJson(doctors);

            response.setStatus(200);
            response.setHeader("Content-Type", "application/json");
            response.getOutputStream().println(json);
        } else {
            response.getOutputStream().print("no data found ");
        }
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        int id = Integer.parseInt(uri.substring("/Api/doctor/".length()));
        System.out.println("doctor id " + id);
        if (service.containsKey(id)) {

            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String payload = buffer.toString();
            System.out.println("payload " + payload);

            doctor = GSON.fromJson(payload, DoctorModule.class);

            if (service.updateRecord(doctor, id) > 0) {
                response.getOutputStream().print(" doctor data updated  ");
            } else {
                response.getOutputStream().print("Error while updating  ");

            }
        } else {
            response.getOutputStream().print("doctor  doesn't exit ");
        }
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        //System.out.println("uri"+uri);
        int id = Integer.parseInt(uri.substring("/Api/doctor/".length()));
        //System.out.println("url id "+id);

        if (service.containsKey(id)) {
            if (service.deleteRecord(id) != 0) {
                response.getOutputStream().print("Data deleted successfully  ");
            } else {
                response.getOutputStream().print("Error while deleting this data ");
            }

        } else {
            response.getOutputStream().print("no data found ");
        }

    }

}
