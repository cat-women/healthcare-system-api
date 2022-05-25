/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reports;

import Doctor.DoctorModule;
import Doctor.DoctorService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
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
@WebServlet(name = "EachReport", urlPatterns = {"/EachReport"})
public class EachReport extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    ReportServices service = new ReportServices();
    ReportModal report;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queryString = request.getQueryString();
        //System.out.println("query is " + queryString);
        String result = java.net.URLDecoder.decode(queryString, StandardCharsets.UTF_8.name());
        char t = result.charAt(result.length() - 1);

        int id = Character.getNumericValue(t);;

        try {
            report = service.getById(id);
        } catch (ParseException ex) {
            Logger.getLogger(EachReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        DoctorService ds = new DoctorService();

        DoctorModule doctor = ds.getById(report.getDoctor_id());

        if (report.getId() > 0) {

            String json1 = GSON.toJson(report);
            String json2 = GSON.toJson(doctor);
            String json = "[" + json1 + "," + json2 + "]";

            //  System.out.println("the ind is " + id + " the data froom server is " + json);          
            //System.out.println("json"+json);
            response.setHeader("Content-Type", "application/json");
            response.getOutputStream().println(json);
            response.setStatus(response.SC_OK);

        } else {
            response.setStatus(response.SC_NO_CONTENT);
        }

    }
}
