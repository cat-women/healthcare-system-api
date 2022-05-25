/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Labs;

import Doctor.DoctorModule;
import Doctor.DoctorService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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
@WebServlet(name = "EachLab", urlPatterns = {"/EachLab"})
public class EachLab extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    LabServices service = new LabServices();
    LabModal lab;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String queryString = request.getQueryString();
//        //System.out.println("query is " + queryString);
//        String result = java.net.URLDecoder.decode(queryString, StandardCharsets.UTF_8.name());
//        char t = result.charAt(result.length() - 1);
//
//        int id = Character.getNumericValue(t);;
        List<LabModal> labs;
        labs = service.getAll1();

        if (labs != null) {

            String json = GSON.toJson(labs);
            response.setHeader("Content-Type", "application/json");
            response.getOutputStream().println(json);
            response.setStatus(response.SC_OK);

        } else {
            response.setStatus(response.SC_NO_CONTENT);
        }

    }
}
