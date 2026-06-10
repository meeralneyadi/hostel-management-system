package com.hms;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ApproveBookingServlet")
public class ApproveBookingServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!AuthCheck.requireRole(request, response, "admin")) {
            return;
        }

        int booking_id = Integer.parseInt(request.getParameter("id"));

        try {
            Connection con = DBConnection.getConnection();

            String getBooking = "SELECT resident_id, room_id FROM bookings WHERE booking_id=? AND status='pending'";
            PreparedStatement psGet = con.prepareStatement(getBooking);
            psGet.setInt(1, booking_id);
            ResultSet rs = psGet.executeQuery();

            if (!rs.next()) {
                response.getWriter().println("Pending booking not found.");
                return;
            }

            int resident_id = rs.getInt("resident_id");
            int room_id = rs.getInt("room_id");

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
            PreparedStatement psRoomCheck = con.prepareStatement(checkRoom);
            psRoomCheck.setInt(1, room_id);
            ResultSet rsRoom = psRoomCheck.executeQuery();

            if (!rsRoom.next()) {
                response.getWriter().println("Room not found.");
                return;
            }

            String roomStatus = rsRoom.getString("status");

            if (!"pending".equals(roomStatus) && !"available".equals(roomStatus)) {
                response.getWriter().println("This room cannot be approved because it is not available.");
                return;
            }

            String approveBooking = "UPDATE bookings SET status='approved' WHERE booking_id=?";
            PreparedStatement psApprove = con.prepareStatement(approveBooking);
            psApprove.setInt(1, booking_id);
            psApprove.executeUpdate();

            String occupyRoom = "UPDATE rooms SET status='occupied' WHERE room_id=?";
            PreparedStatement psRoom = con.prepareStatement(occupyRoom);
            psRoom.setInt(1, room_id);
            psRoom.executeUpdate();

            String assignResident = "UPDATE residents SET room_id=? WHERE resident_id=?";
            PreparedStatement psResident = con.prepareStatement(assignResident);
            psResident.setInt(1, room_id);
            psResident.setInt(2, resident_id);
            psResident.executeUpdate();

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