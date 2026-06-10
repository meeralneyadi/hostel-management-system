package com.hms;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/RejectBookingServlet")
public class RejectBookingServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!AuthCheck.requireRole(request, response, "admin")) {
            return;
        }
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String role = (String) session.getAttribute("role");

        if (!"admin".equals(role)) {
            response.getWriter().println("Only admin can reject bookings.");
            return;
        }

        int booking_id = Integer.parseInt(request.getParameter("id"));

        try {
            Connection con = DBConnection.getConnection();

            String getBooking = "SELECT room_id FROM bookings WHERE booking_id=? AND status='pending'";
            PreparedStatement psGet = con.prepareStatement(getBooking);
            psGet.setInt(1, booking_id);
            ResultSet rs = psGet.executeQuery();

            if (!rs.next()) {
                response.getWriter().println("Pending booking not found.");
                return;
            }

            int room_id = rs.getInt("room_id");

            String rejectBooking = "UPDATE bookings SET status='rejected' WHERE booking_id=?";
            PreparedStatement psReject = con.prepareStatement(rejectBooking);
            psReject.setInt(1, booking_id);
            psReject.executeUpdate();

            String makeRoomAvailable = "UPDATE rooms SET status='available' WHERE room_id=?";
            PreparedStatement psRoom = con.prepareStatement(makeRoomAvailable);
            psRoom.setInt(1, room_id);
            psRoom.executeUpdate();

            response.sendRedirect("ViewPendingBookingsServlet");

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
