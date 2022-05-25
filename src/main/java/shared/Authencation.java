/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import Users.UserModal;
import Users.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dell
 */
@WebServlet(name = "Autheticattion", urlPatterns = {"/Autheticattion"})
public class Authencation extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    UserService service = new UserService();
    UserModal user = new UserModal();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // super.doPost(req, resp); //To change body of generated methods, choose Tools | Templates.
        HttpSession session = request.getSession();

        String uri = request.getRequestURI();
        //String username = Integer.parseInt(uri.substring("/Api/user/".length()));

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        String payload = buffer.toString();
        String email = GSON.fromJson(payload, UserModal.class).getEmail();
        String password = GSON.fromJson(payload, UserModal.class).getPassword();

        System.out.println("user name " + email + password);
        if (email.equals(null) || password.equals(null)) {
            System.out.println("empty values");
            return;
        }
        try {
            user = service.authenticate(email);
            System.out.println("userfrom server" + user.getName());

            if (user != null) {
                //System.out.println("username \t" + user.getUsername() + "\n pwd \t" + user.getPassword());
                if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                    Random rnd = new Random();
                    int n = 100000 + rnd.nextInt(9000);
                    user.setToken(n);

                    Mailer.send(user.getEmail(), "Varification code", "your varification code is \t" + n);

                    response.getOutputStream().print(GSON.toJson(user));
                    response.setStatus(HttpServletResponse.SC_OK);

                } else {
                    response.getOutputStream().print("login failed ");
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);

                }
            } else {
                response.getOutputStream().print(404);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Authencation.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp); //To change body of generated methods, choose Tools | Templates.
//        HttpSession session = req.getSession();
//        session.invalidate();
//        resp.setStatus(HttpServletResponse.SC_ACCEPTED);

    }

}
