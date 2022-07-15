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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReimburseServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ReimburseService rs = new ReimburseService();
    // object to convert to JSON format
    private ObjectMapper om = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        CorsFix.addCorsHeader(req.getRequestURI(), res);
        res.addHeader("Content-Type", "application/json");

        String pathInfo = req.getPathInfo();

        if (pathInfo == null) {

            HttpSession session = req.getSession();

            if (session.getAttribute("userRole") != null && session.getAttribute("userRole").equals(Role.ADMIN)) {
                List<Reimbursement> reimb = rs.getReimbursements();
                List<ReimburseDTO> reimbDTO = new ArrayList<>();

                reimb.forEach(r -> reimbDTO.add((new ReimburseDTO(r))));

                PrintWriter pw = res.getWriter();
                pw.write(om.writeValueAsString(reimbDTO));

                pw.close();
            } else {
                res.sendError(401, "Unauthorized request.");
            }
        } else {
            int author = Integer.parseInt(pathInfo.substring(1));
            List<Reimbursement> reimb = rs.getByAuthorId(author);
            List<ReimburseDTO> reimbDTO = new ArrayList<>();

            reimb.forEach(r -> reimbDTO.add((new ReimburseDTO(r))));

            PrintWriter pw = res.getWriter();
            pw.write(om.writeValueAsString(reimbDTO));

            pw.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        InputStream reqBody = req.getInputStream();
        //CorsFix.addCorsHeader(req.getRequestURI(), res);

        Reimbursement newReimb = om.readValue(reqBody, Reimbursement.class);
        newReimb.setSubmitted(new Date());
        newReimb.setResolver(1);
        System.out.println(newReimb);

        try {

            rs.createReimburse(newReimb);

            res.setStatus(201); //Status: Created
        } catch (Exception e) {
            res.sendError(400, "Unable to create new reimbursement request.");
            e.printStackTrace();
        }
    }

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

            //set status to 3 since we are accepting.
            newReimb.setStatusId(3);

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
