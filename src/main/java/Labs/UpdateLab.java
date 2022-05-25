/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Labs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dell
 */
@WebServlet(name = "UpdateLab", urlPatterns = {"/UpdateLab"})
public class UpdateLab extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    LabServices service = new LabServices();
    LabModal lab;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queryString = request.getQueryString();
        //System.out.println("query is " + queryString);
        String result = java.net.URLDecoder.decode(queryString, StandardCharsets.UTF_8.name());
        char t = result.charAt(result.length()-1);
        
        int id = Character.getNumericValue(t);

        lab = service.getByApp_id(id);

        if (lab.getId() != 0) {
           
            String json = GSON.toJson(lab);
           //  System.out.println("the ind is " + id + " the data froom server is " + json);          
            
            //System.out.println("json"+json);
            response.setHeader("Content-Type", "application/json");
            response.setStatus(200);
            response.getOutputStream().println(json);
            response.setStatus(response.SC_OK);
            
            
        } else {
            response.setStatus(response.SC_NO_CONTENT);
        }

    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        lab = new LabModal();

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        String payload = buffer.toString();
           System.out.println("payload od lab"+payload);
        lab = GSON.fromJson(payload, LabModal.class);
        int result = service.updateRecord(lab);
        if (result > 0) {
            response.setStatus(response.SC_OK);
            response.getOutputStream().print("data updated successfully");
        } else {
             response.setStatus(response.SC_SERVICE_UNAVAILABLE);
             //503

        }
    }

}
