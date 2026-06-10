package com.hms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/RequestServlet")
public class RequestServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String role = (String) session.getAttribute("role");

        if (!"resident".equals(role)) {
            response.getWriter().println("Only residents can submit support requests.");
            return;
        }

        int user_id = (int) session.getAttribute("user_id");

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String priority = request.getParameter("priority");
        String status = "open";

        try {
            Connection con = DBConnection.getConnection();

            String getResident = "SELECT resident_id FROM residents WHERE user_id=?";
            PreparedStatement psResident = con.prepareStatement(getResident);
            psResident.setInt(1, user_id);
            ResultSet rs = psResident.executeQuery();

            if (!rs.next()) {
                response.getWriter().println("No resident profile found for this account.");
                return;
            }

            int resident_id = rs.getInt("resident_id");

            String sql = "INSERT INTO support_requests (resident_id, title, description, priority, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, resident_id);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, priority);
            ps.setString(5, status);

            ps.executeUpdate();

            response.sendRedirect("residentDashboard.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}