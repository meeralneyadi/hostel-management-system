package com.hms;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ChangeResidentRoomServlet")
public class ChangeResidentRoomServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!AuthCheck.requireRole(request, response, "admin")) {
            return;
        }

        int resident_id = Integer.parseInt(request.getParameter("resident_id"));
        int new_room_id = Integer.parseInt(request.getParameter("new_room_id"));

        try {
            Connection con = DBConnection.getConnection();

            String getOldRoom = "SELECT room_id FROM residents WHERE resident_id=?";
            PreparedStatement psOld = con.prepareStatement(getOldRoom);
            psOld.setInt(1, resident_id);
            ResultSet rsOld = psOld.executeQuery();

            if (!rsOld.next()) {
                response.getWriter().println("Resident not found.");
                return;
            }

            int old_room_id = rsOld.getInt("room_id");

            if (rsOld.wasNull()) {
                response.getWriter().println("This resident does not currently have a room. Use Book Room instead.");
                return;
            }

            if (old_room_id == new_room_id) {
                response.getWriter().println("The selected room is already assigned to this resident.");
                return;
            }

            String checkNewRoom = "SELECT status FROM rooms WHERE room_id=?";
            PreparedStatement psCheck = con.prepareStatement(checkNewRoom);
            psCheck.setInt(1, new_room_id);
            ResultSet rsRoom = psCheck.executeQuery();

            if (!rsRoom.next()) {
                response.getWriter().println("New room not found.");
                return;
            }

            if (!"available".equals(rsRoom.getString("status"))) {
                response.getWriter().println("Selected room is not available.");
                return;
            }

            String freeOldRoom = "UPDATE rooms SET status='available' WHERE room_id=?";
            PreparedStatement psFree = con.prepareStatement(freeOldRoom);
            psFree.setInt(1, old_room_id);
            psFree.executeUpdate();

            String occupyNewRoom = "UPDATE rooms SET status='occupied' WHERE room_id=?";
            PreparedStatement psOccupy = con.prepareStatement(occupyNewRoom);
            psOccupy.setInt(1, new_room_id);
            psOccupy.executeUpdate();

            String updateResident = "UPDATE residents SET room_id=? WHERE resident_id=?";
            PreparedStatement psResident = con.prepareStatement(updateResident);
            psResident.setInt(1, new_room_id);
            psResident.setInt(2, resident_id);
            psResident.executeUpdate();

            String insertBooking =
                    "INSERT INTO bookings (resident_id, room_id, booking_date, status) VALUES (?, ?, ?, ?)";
            PreparedStatement psBooking = con.prepareStatement(insertBooking);
            psBooking.setInt(1, resident_id);
            psBooking.setInt(2, new_room_id);
            psBooking.setString(3, LocalDate.now().toString());
            psBooking.setString(4, "approved");
            psBooking.executeUpdate();

            response.sendRedirect("ViewResidentsServlet");

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