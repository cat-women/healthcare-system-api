/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Users;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dell
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    UserService service = new UserService();
    UserModal user;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        response.addHeader("Access-Control-Allow-Origin", " http://localhost:3000");
//        
//      response.addHeader("strict-origin-when-cross-origin", "http://localhost:3000");
//      
//        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
//        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
//        response.addHeader("Access-Control-Max-Age", "1728000");
        UserModal User = new UserModal();

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        String payload = buffer.toString();
        System.out.println("payload " + payload);

        user = GSON.fromJson(payload, UserModal.class);

        if (service.insertRecord(user) > 0) {
            response.getOutputStream().print("new user added ");
        } else {
            response.getOutputStream().print("no data found ");

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


        ArrayList<UserModal> userlist = new ArrayList<UserModal>();
        userlist = service.getAll(start, end);
        if (userlist != null) {
            String json = GSON.toJson(userlist);

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
        int id = Integer.parseInt(uri.substring("/Api/user/".length()));
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

            user = GSON.fromJson(payload, UserModal.class);

            if (service.updateRecord(user, id) > 0) {
                response.getOutputStream().print(" user data updated  ");
            } else {
                response.getOutputStream().print("Error while updating  ");

            }
        } else {
            response.getOutputStream().print("user doesn't exit ");
        }
    }

//    public void doDelete(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        int id = Integer.parseInt(request.getParameter("id"));
//
//        // System.out.println("the id in user delete is " + Integer.parseInt(id));
//        if (service.containsKey(id)) {
//            if (service.deleteRecord(id) > 0) {
//                response.getOutputStream().print("Data deleted successfully  ");
//            } else {
//                response.getOutputStream().print("Error while deleting this data ");
//            }
//
//        } else {
//            response.getOutputStream().print("no data found ");
//        }
//    }
}
