package com.hms;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/RejectCheckoutServlet")
public class RejectCheckoutServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!AuthCheck.requireRole(request, response, "admin")) {
            return;
        }

        int checkout_id = Integer.parseInt(request.getParameter("id"));

        try {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE checkout_requests SET status='rejected' WHERE checkout_id=? AND status='pending'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, checkout_id);
            ps.executeUpdate();

            response.sendRedirect("ViewCheckoutRequestsServlet");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.toString());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}