package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.ReimburseDTO;
import com.revature.models.Reimbursement;
import com.revature.models.Role;
import com.revature.services.ReimburseService;
import com.revature.util.CorsFix;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class DenyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ReimburseService rs = new ReimburseService();
    // object to convert to JSON format
    private ObjectMapper om = new ObjectMapper();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
        CorsFix.addCorsHeader(req.getRequestURI(), res);
        res.addHeader("Content-Type", "application/json");
        InputStream reqBody = req.getInputStream();

        String pathInfo = req.getPathInfo();

        if (pathInfo == null) {
            res.sendError(400, "Must send an id of the reimbursement request.");
        } else {

            HttpSession session = req.getSession();
            int id = Integer.parseInt(pathInfo.substring(1));
            //Reimbursement newReimb = om.readValue(reqBody, Reimbursement.class);
            String status = om.writeValueAsString(reqBody);
            System.out.println(status);
            Reimbursement newReimb = rs.getById(id);
            newReimb.setResolved(new Date());

            //int resolver = Integer.parseInt((String)session.getAttribute("id"));
            //System.out.println(resolver);

            //bad practice, but this user is the only admin
            //did this as problem with getting id attribute from session attribute with casting/parsing. See Above.
            newReimb.setResolver(1);

            //set status to 2 since we are denying.
            newReimb.setStatusId(2);

            try {

                rs.acceptDeclineReimburse(newReimb);

                res.setStatus(200);
            } catch (Exception e) {
                res.sendError(400, "Unable to create new reimbursement request.");
                e.printStackTrace();
            }
        }
    }
}
