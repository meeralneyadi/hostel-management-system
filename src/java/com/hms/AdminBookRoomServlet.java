package com.hms;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AdminBookRoomServlet")
public class AdminBookRoomServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!AuthCheck.requireRole(request, response, "admin")) {
            return;
        }

        int resident_id = Integer.parseInt(request.getParameter("resident_id"));
        int room_id = Integer.parseInt(request.getParameter("room_id"));

        try {
            Connection con = DBConnection.getConnection();

            String checkResident = "SELECT room_id FROM residents WHERE resident_id=?";
            PreparedStatement psResidentCheck = con.prepareStatement(checkResident);
            psResidentCheck.setInt(1, resident_id);
            ResultSet rsResident = psResidentCheck.executeQuery();

            if (!rsResident.next()) {
                response.getWriter().println("Resident not found.");
                return;
            }

            int currentRoomId = rsResident.getInt("room_id");

            if (!rsResident.wasNull()) {
                response.getWriter().println("This resident already has an assigned room. Use Change Room instead.");
                return;
            }

            String checkRoom = "SELECT status FROM rooms WHERE room_id=?";
            PreparedStatement psCheck = con.prepareStatement(checkRoom);
            psCheck.setInt(1, room_id);
            ResultSet rsRoom = psCheck.executeQuery();

            if (!rsRoom.next()) {
                response.getWriter().println("Room not found.");
                return;
            }

            if (!"available".equals(rsRoom.getString("status"))) {
                response.getWriter().println("This room is not available.");
                return;
            }

            String insertBooking = "INSERT INTO bookings (resident_id, room_id, booking_date, status) VALUES (?, ?, ?, ?)";
            PreparedStatement psBooking = con.prepareStatement(insertBooking);
            psBooking.setInt(1, resident_id);
            psBooking.setInt(2, room_id);
            psBooking.setString(3, LocalDate.now().toString());
            psBooking.setString(4, "approved");
            psBooking.executeUpdate();

            String updateRoom = "UPDATE rooms SET status='occupied' WHERE room_id=?";
            PreparedStatement psRoom = con.prepareStatement(updateRoom);
            psRoom.setInt(1, room_id);
            psRoom.executeUpdate();

            String updateResident = "UPDATE residents SET room_id=? WHERE resident_id=?";
            PreparedStatement psResident = con.prepareStatement(updateResident);
            psResident.setInt(1, room_id);
            psResident.setInt(2, resident_id);
            psResident.executeUpdate();

            response.sendRedirect("ViewBookingsServlet");

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