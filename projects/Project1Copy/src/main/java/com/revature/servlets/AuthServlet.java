package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.UserDTO;
import com.revature.exceptions.LoginException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.UserService;
import com.revature.util.CorsFix;

public class AuthServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private AuthService as = new AuthService();
    private UserService us = new UserService();
    private ObjectMapper om = new ObjectMapper();

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        CorsFix.addCorsHeader(req.getRequestURI(), res);

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            //User principal = as.login(username, password);
            User principal = us.getByUsername(username);

            HttpSession session = req.getSession();
            session.setAttribute("userId", principal.getUserId());
            //session.setAttribute("userRole", principal.getRole());


            UserDTO principalDTO = new UserDTO(principal);
            try (PrintWriter pw = res.getWriter()) {
                pw.write(om.writeValueAsString(principalDTO));
                res.setStatus(200);
            }

        } catch ( Exception e){///*UserNotFoundException |*/LoginException e) {
            res.sendError(400, "Login unsuccessful.");
            e.printStackTrace();
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        CorsFix.addCorsHeader(req.getRequestURI(), res);

        HttpSession session = req.getSession();

        session.invalidate();
    }
}
