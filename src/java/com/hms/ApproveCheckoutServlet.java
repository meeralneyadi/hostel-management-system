package com.hms;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ApproveCheckoutServlet")
public class ApproveCheckoutServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!AuthCheck.requireRole(request, response, "admin")) {
            return;
        }

        int checkout_id = Integer.parseInt(request.getParameter("id"));

        try {
            Connection con = DBConnection.getConnection();

            String getRequest = "SELECT resident_id, room_id FROM checkout_requests WHERE checkout_id=? AND status='pending'";
            PreparedStatement psGet = con.prepareStatement(getRequest);
            psGet.setInt(1, checkout_id);
            ResultSet rs = psGet.executeQuery();

            if (!rs.next()) {
                response.getWriter().println("Pending check-out request not found.");
                return;
            }

            int resident_id = rs.getInt("resident_id");
            int room_id = rs.getInt("room_id");

            String updateRequest = "UPDATE checkout_requests SET status='approved' WHERE checkout_id=?";
            PreparedStatement psRequest = con.prepareStatement(updateRequest);
            psRequest.setInt(1, checkout_id);
            psRequest.executeUpdate();

            String releaseResident = "UPDATE residents SET room_id=NULL WHERE resident_id=?";
            PreparedStatement psResident = con.prepareStatement(releaseResident);
            psResident.setInt(1, resident_id);
            psResident.executeUpdate();

            String releaseRoom = "UPDATE rooms SET status='available' WHERE room_id=?";
            PreparedStatement psRoom = con.prepareStatement(releaseRoom);
            psRoom.setInt(1, room_id);
            psRoom.executeUpdate();

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