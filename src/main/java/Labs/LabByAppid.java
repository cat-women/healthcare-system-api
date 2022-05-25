/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Labs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.ResultSet;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
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
@WebServlet(name = "LabByAppid", urlPatterns = {"/LabByAppid"})
public class LabByAppid extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    LabServices service = new LabServices();
    LabModal lab;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queryString = request.getQueryString();
        //System.out.println("query is " + queryString);
        String result = java.net.URLDecoder.decode(queryString, StandardCharsets.UTF_8.name());
        char t = result.charAt(result.length() - 1);
        int id = Character.getNumericValue(t);
        System.out.println("the result od url paramater " + result + "   " + id);

        List<LabModal> lab = service.getByAppId(id);

        if (lab != null) {
            String json = GSON.toJson(lab);
            //System.out.println("json is " + json);
            response.setHeader("Content-Type", "application/json");

            response.getOutputStream().println(json);
            response.setStatus(response.SC_OK);
        } else {
            response.setStatus(response.SC_NO_CONTENT);

        }

    }

}
