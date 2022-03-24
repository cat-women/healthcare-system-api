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
@WebServlet(name = "LabController", urlPatterns = {"/LabController"})
public class LabController extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    LabServices service = new LabServices();
    LabModal lab;

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

        lab = GSON.fromJson(payload, LabModal.class);
        int result = service.insertRecord(lab);
        if (result > 0) {
            response.getOutputStream().print("data added successfully ");
        } else {
            response.getOutputStream().print("failed to add ");

        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<LabModal> labs = new ArrayList<LabModal>();
        labs = service.getAll();
        if (labs != null) {
            String json = GSON.toJson(labs);

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
        int id = Integer.parseInt(uri.substring("/Api/lab/".length()));
        System.out.println("user id " + id);
        if (service.containsKey(id)) {

            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String payload = buffer.toString();
            System.out.println("payload " + payload);

            lab = GSON.fromJson(payload, LabModal.class);

            if (service.updateRecord(lab, id) > 0) {
                response.getOutputStream().print(" user data updated  ");
            } else {
                response.getOutputStream().print("Error while updating  ");

            }
        } else {
            response.getOutputStream().print("user doesn't exit ");
        }
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        //System.out.println("uri"+uri);
        int id = Integer.parseInt(uri.substring("/Api/lab/".length()));
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
