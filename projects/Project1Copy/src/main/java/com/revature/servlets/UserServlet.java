package com.revature.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.UserDTO;
import com.revature.exceptions.UserNotCreatedException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.util.CorsFix;

/*-
 * Handles all of the requests made to /users(/...)
 */
public class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserService us = new UserService();
    // object to convert to JSON format
    private ObjectMapper om = new ObjectMapper();

    /*-
     * All GET request made to the /users endpoint are funneled to this doGet method
     * 		- /users
     * 			- returns all users
     * 		- /users/{id}
     * 			- returns a user of a specific id
     * Have to determine which behavior to execute based on the URL request
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        CorsFix.addCorsHeader(req.getRequestURI(), res);
        res.addHeader("Content-Type", "application/json");

        String pathInfo = req.getPathInfo();

        if(pathInfo == null){

            HttpSession session = req.getSession();

            if(session.getAttribute("userRole") != null && session.getAttribute("userRole").equals(Role.ADMIN)){
                List<User> users = us.getUsers();
                List<UserDTO> usersDTO = new ArrayList<>();

                users.forEach(u -> usersDTO.add(new UserDTO(u)));

                PrintWriter pw = res.getWriter();
                pw.write(om.writeValueAsString(usersDTO));

                pw.close();
            } else{
                res.sendError(401, "Unauthorized request.");
            }
        }else {
            int id = Integer.parseInt(pathInfo.substring(1));
            User user = us.getUserById(id);
            UserDTO userDTO = new UserDTO(user);

            PrintWriter pw = res.getWriter();
            pw.write(om.writeValueAsString(userDTO));
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        InputStream reqBody = req.getInputStream();

        User newUser = om.readValue(reqBody, User.class);

        try {
            us.createUser(newUser);

            res.setStatus(201); // Status: Created
        } catch (Exception e){//UserNotCreatedException e) {
//			res.setStatus(400);
            res.sendError(400, "Unable to create new user.");
            e.printStackTrace();
        }
    }
}
