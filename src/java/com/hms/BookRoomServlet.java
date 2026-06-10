package com.hms;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/BookRoomServlet")
public class BookRoomServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int room_id = Integer.parseInt(request.getParameter("room_id"));
        int user_id = (int) session.getAttribute("user_id");
        String role = (String) session.getAttribute("role");

        if (!"resident".equals(role)) {
            response.getWriter().println("Only residents can book from this page.");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();

            String getResident = "SELECT resident_id, room_id FROM residents WHERE user_id=?";
            PreparedStatement psResident = con.prepareStatement(getResident);
            psResident.setInt(1, user_id);
            ResultSet rsResident = psResident.executeQuery();

            if (!rsResident.next()) {
                response.getWriter().println("No resident profile found for this account.");
                return;
            }

            int resident_id = rsResident.getInt("resident_id");
            int currentRoomId = rsResident.getInt("room_id");

            if (!rsResident.wasNull()) {
                response.getWriter().println("You already have an assigned room. Please contact the admin if you want to change rooms.");
                return;
            }

            String checkRoom = "SELECT status FROM rooms WHERE room_id=?";
            PreparedStatement psCheck = con.prepareStatement(checkRoom);
            psCheck.setInt(1, room_id);
            ResultSet roomRs = psCheck.executeQuery();

            if (!roomRs.next()) {
                response.getWriter().println("Room not found.");
                return;
            }

            if (!"available".equals(roomRs.getString("status"))) {
                response.getWriter().println("This room is no longer available.");
                return;
            }

            String insertBooking = "INSERT INTO bookings (resident_id, room_id, booking_date, status) VALUES (?, ?, ?, ?)";
            PreparedStatement psBooking = con.prepareStatement(insertBooking);
            psBooking.setInt(1, resident_id);
            psBooking.setInt(2, room_id);
            psBooking.setString(3, LocalDate.now().toString());
            psBooking.setString(4, "pending");
            psBooking.executeUpdate();

            String updateRoom = "UPDATE rooms SET status='pending' WHERE room_id=?";
            PreparedStatement psRoom = con.prepareStatement(updateRoom);
            psRoom.setInt(1, room_id);
            psRoom.executeUpdate();

            response.sendRedirect("ViewMyBookingsServlet");

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