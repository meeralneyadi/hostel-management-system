package com.hms;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/RequestCheckoutServlet")
public class RequestCheckoutServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (!"resident".equals(session.getAttribute("role"))) {
            response.getWriter().println("Access denied. Residents only.");
            return;
        }

        int user_id = (int) session.getAttribute("user_id");
        String reason = request.getParameter("reason");

        try {
            Connection con = DBConnection.getConnection();

            String getResident = "SELECT resident_id, room_id FROM residents WHERE user_id=?";
            PreparedStatement psResident = con.prepareStatement(getResident);
            psResident.setInt(1, user_id);
            ResultSet rs = psResident.executeQuery();

            if (!rs.next()) {
                response.getWriter().println("No resident profile found.");
                return;
            }

            int resident_id = rs.getInt("resident_id");
            int room_id = rs.getInt("room_id");

            if (rs.wasNull()) {
                response.getWriter().println("You are not currently assigned to a room.");
                return;
            }

            String checkPending = "SELECT * FROM checkout_requests WHERE resident_id=? AND status='pending'";
            PreparedStatement psCheck = con.prepareStatement(checkPending);
            psCheck.setInt(1, resident_id);
            ResultSet pending = psCheck.executeQuery();

            if (pending.next()) {
                response.getWriter().println("You already have a pending check-out request.");
                return;
            }

            String sql = "INSERT INTO checkout_requests (resident_id, room_id, request_date, reason, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, resident_id);
            ps.setInt(2, room_id);
            ps.setString(3, LocalDate.now().toString());
            ps.setString(4, reason);
            ps.setString(5, "pending");

            ps.executeUpdate();

            response.sendRedirect("ViewMyCheckoutRequestsServlet");

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