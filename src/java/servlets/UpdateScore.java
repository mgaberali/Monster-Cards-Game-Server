/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Beans.User;
import DBUtilty.UserDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Hala
 */
@WebServlet(name = "UpdateScore", urlPatterns = {"/UpdateScore"})
public class UpdateScore extends HttpServlet {

        static final UserDAO user = UserDAO.getInstance();
    User userObj;
    PrintWriter out;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        out = response.getWriter();
        Gson gson = new Gson();
        HashMap<String, String> resp = new HashMap<>();
        String result = "";

        StringBuilder jsonData = new StringBuilder();

        // get json data
        BufferedReader reader = request.getReader();

        String line = null;
        while ((line = reader.readLine()) != null) {
            jsonData.append(line);
        }

        // create json parser
        JsonParser parser = new JsonParser();

        // convert json string to json objects
        JsonObject json = (JsonObject) parser.parse(jsonData.toString());

        // get request parameters
        //String name = json.get("name").getAsString();
        //String password = json.get("password").getAsString();
        String email = json.get("email").getAsString();
        int score = json.get("score").getAsInt();
        //String image = json.get("image").getAsString();

        // create user with data
        userObj = new User();
        //userObj.setName(name);
        //userObj.setPassword(password);
        userObj.setEmail(email);
        userObj.setScore(score);
        //userObj.setScore(Integer.parseInt(score));

        try {

            user.updateUserScore(userObj);

            // prepare response data
            resp.put("status", "success");

        } catch (SQLException ex) {

            // prepare response data
            resp.put("status", "fail");

        } finally {

            // convert response data to json string
            result = gson.toJson(resp);

            // write json string on response
            out.print(result);

        }

    }

}
