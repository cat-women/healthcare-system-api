/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reports;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
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
@WebServlet(name = "ReportByAppid", urlPatterns = {"/ReportByAppid"})
public class ReportByAppid extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    ReportServices service = new ReportServices();
    ReportModal report;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queryString = request.getQueryString();
        String result = java.net.URLDecoder.decode(queryString, "UTF-8");

        List<ReportModal> reports;
        try {
            reports = service.getByAppId2(result);

            if (reports != null) {

                String json = GSON.toJson(reports);
                System.out.println("the json value of repprt " + json);
                System.out.println("the report ko sjson is "+json);
                response.setHeader("Content-Type", "application/json");

                response.getOutputStream().println(json);
                response.setStatus(response.SC_OK);
            } else {
                response.setStatus(response.SC_NO_CONTENT);

            }
        } catch (ParseException ex) {
            Logger.getLogger(ReportByAppid.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
