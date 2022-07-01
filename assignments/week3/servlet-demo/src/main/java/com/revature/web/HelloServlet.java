package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*-
 * Tomcat will map /hello requests to this Servlet
 * 		- doGet method with handle GET HTTP requests
 */
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        // behavior to handle GET request

        // Object to write to HTTP response body
//		PrintWriter pw = resp.getWriter();
//		pw.write("<html><body><h1>This is in an h1</h1></body></html>");
//		pw.close();

        req.getRequestDispatcher("index.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        // behavior to handle POST request
    }

}