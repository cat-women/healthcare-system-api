/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reports;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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
@WebServlet(name = "UpdateReport", urlPatterns = {"/UpdateReport"})
public class UpdateReport extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    ReportServices service = new ReportServices();
    ReportModal report;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        report = new ReportModal();

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        String payload = buffer.toString();
        //System.out.println("update rerport payload"+payload );
        report = GSON.fromJson(payload, ReportModal.class);
        int result = service.updateRecord(report);
        if (result > 0) {
            response.setStatus(response.SC_OK);
            response.getOutputStream().print("data updated successfully ");
        } else {
            response.getOutputStream().print("Failed to update");

        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String queryString = request.getQueryString();
        String result = java.net.URLDecoder.decode(queryString, StandardCharsets.UTF_8.name());
        char t = result.charAt(result.length() - 1);

        int id = Character.getNumericValue(t);

        try {
            report = service.getByAppId(id);
        } catch (ParseException ex) {
            Logger.getLogger(UpdateReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (report.getId() > 0) {
            String json = GSON.toJson(report);
            System.out.println("report is " + json);

            response.setStatus(response.SC_OK);
            response.setHeader("Content-Type", "application/json");
            response.getOutputStream().println(json);
        } else {

            response.setStatus(response.SC_NO_CONTENT);
            response.getOutputStream().print("no data found ");
        }
    }

}
