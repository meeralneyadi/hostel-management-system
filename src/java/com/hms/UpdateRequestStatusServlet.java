package com.hms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/UpdateRequestStatusServlet")
public class UpdateRequestStatusServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!AuthCheck.requireAdminOrStaff(request, response)) {
            return;
        }

        int request_id = Integer.parseInt(request.getParameter("id"));
        String status = request.getParameter("status");

        try {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE support_requests SET status=? WHERE request_id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, status);
            ps.setInt(2, request_id);

            ps.executeUpdate();

            response.sendRedirect("ViewRequestsServlet");

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