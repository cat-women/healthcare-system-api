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
@WebServlet(name = "ReportController", urlPatterns = {"/ReportController"})
public class ReportController extends HttpServlet {
    

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

        report = GSON.fromJson(payload, ReportModal.class);
        int result = service.insertRecord(report);
        if (result > 0) {
            response.getOutputStream().print("data added successfully ");
        } else {
            response.getOutputStream().print("failed to add ");

        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<ReportModal> reports = new ArrayList<ReportModal>();
        reports = service.getAll();
        if (reports != null) {
            String json = GSON.toJson(reports);

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
        int id = Integer.parseInt(uri.substring("/Api/report/".length()));
        System.out.println("user id "+id);
        if (service.containsKey(id)) {
            
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String payload = buffer.toString();
            System.out.println("payload " + payload);

            report = GSON.fromJson(payload, ReportModal.class);

            if (service.updateRecord(report, id) > 0) {
                response.getOutputStream().print("  data updated  ");
            } else {
                response.getOutputStream().print("Error while updating  ");

            }
        } else {
            response.getOutputStream().print("data doesn't exit ");
        }
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        //System.out.println("uri"+uri);
        int id = Integer.parseInt(uri.substring("/Api/report/".length()));
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
