package com.hms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AdminBookRoomPageServlet")
public class AdminBookRoomPageServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!AuthCheck.requireRole(request, response, "admin")) {
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Connection con = DBConnection.getConnection();

            String residentsSql =
                    "SELECT resident_id, full_name, student_id "
                    + "FROM residents "
                    + "WHERE room_id IS NULL "
                    + "ORDER BY full_name";

            PreparedStatement psResidents = con.prepareStatement(residentsSql);
            ResultSet residentsRs = psResidents.executeQuery();

            String roomsSql =
                    "SELECT r.room_id, r.room_number, r.room_type, r.capacity, r.floor, b.building_name "
                    + "FROM rooms r "
                    + "JOIN buildings b ON r.building_id = b.building_id "
                    + "WHERE r.status='available' "
                    + "ORDER BY b.building_name, r.floor, r.room_number";

            PreparedStatement psRooms = con.prepareStatement(roomsSql);
            ResultSet roomsRs = psRooms.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Book Room for Resident</title>");
            out.println("<link rel='stylesheet' href='" + request.getContextPath() + "/css/style.css'>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class='navbar'>");
            out.println("<h2>Hostel Management System</h2>");
            out.println("<div>");
            out.println("<a href='adminDashboard.jsp'>Dashboard</a>");
            out.println("<a href='LogoutServlet'>Logout</a>");
            out.println("</div>");
            out.println("</div>");

            out.println("<div class='container'>");

            out.println("<h1 class='page-title'>Book Room for Resident</h1>");
            out.println("<p class='section-subtitle'>Select a resident and an available room. The booking will be approved immediately.</p>");

            out.println("<div class='form-box'>");
            out.println("<h2>Booking Details</h2>");

            out.println("<form action='AdminBookRoomServlet' method='post'>");

            out.println("Resident:");
            out.println("<select name='resident_id' required>");
            out.println("<option value=''>Select Resident</option>");

            while (residentsRs.next()) {
                out.println("<option value='" + residentsRs.getInt("resident_id") + "'>");
                out.println(residentsRs.getString("full_name") + " - " + residentsRs.getString("student_id"));
                out.println("</option>");
            }

            out.println("</select>");

            out.println("Available Room:");
            out.println("<select name='room_id' required>");
            out.println("<option value=''>Select Room</option>");

            while (roomsRs.next()) {
                out.println("<option value='" + roomsRs.getInt("room_id") + "'>");
                out.println(roomsRs.getString("building_name")
                        + " - Room " + roomsRs.getString("room_number")
                        + " - " + roomsRs.getString("room_type")
                        + " - Capacity " + roomsRs.getInt("capacity")
                        + " - Floor " + roomsRs.getInt("floor"));
                out.println("</option>");
            }

            out.println("</select>");

            out.println("<input class='btn' type='submit' value='Book Room'>");

            out.println("</form>");
            out.println("</div>");

            out.println("<br><br>");

            out.println("<h2 class='page-title'>Available Rooms</h2>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Room ID</th>");
            out.println("<th>Building</th>");
            out.println("<th>Room Number</th>");
            out.println("<th>Type</th>");
            out.println("<th>Capacity</th>");
            out.println("<th>Floor</th>");
            out.println("</tr>");

            String roomsTableSql =
                    "SELECT r.room_id, r.room_number, r.room_type, r.capacity, r.floor, b.building_name "
                    + "FROM rooms r "
                    + "JOIN buildings b ON r.building_id = b.building_id "
                    + "WHERE r.status='available' "
                    + "ORDER BY b.building_name, r.floor, r.room_number";

            PreparedStatement psRoomsTable = con.prepareStatement(roomsTableSql);
            ResultSet roomsTableRs = psRoomsTable.executeQuery();

            boolean hasRooms = false;

            while (roomsTableRs.next()) {
                hasRooms = true;

                out.println("<tr>");
                out.println("<td>" + roomsTableRs.getInt("room_id") + "</td>");
                out.println("<td>" + roomsTableRs.getString("building_name") + "</td>");
                out.println("<td>" + roomsTableRs.getString("room_number") + "</td>");
                out.println("<td>" + roomsTableRs.getString("room_type") + "</td>");
                out.println("<td>" + roomsTableRs.getInt("capacity") + "</td>");
                out.println("<td>" + roomsTableRs.getInt("floor") + "</td>");
                out.println("</tr>");
            }

            if (!hasRooms) {
                out.println("<tr>");
                out.println("<td colspan='6' style='text-align:center; padding:25px;'>No available rooms found.</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<br><br>");

            out.println("<h2 class='page-title'>Residents Without Assigned Rooms</h2>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Resident ID</th>");
            out.println("<th>Full Name</th>");
            out.println("<th>Student ID</th>");
            out.println("</tr>");

            String residentsTableSql =
                    "SELECT resident_id, full_name, student_id "
                    + "FROM residents "
                    + "WHERE room_id IS NULL "
                    + "ORDER BY full_name";

            PreparedStatement psResidentsTable = con.prepareStatement(residentsTableSql);
            ResultSet residentsTableRs = psResidentsTable.executeQuery();

            boolean hasResidents = false;

            while (residentsTableRs.next()) {
                hasResidents = true;

                out.println("<tr>");
                out.println("<td>" + residentsTableRs.getInt("resident_id") + "</td>");
                out.println("<td>" + residentsTableRs.getString("full_name") + "</td>");
                out.println("<td>" + residentsTableRs.getString("student_id") + "</td>");
                out.println("</tr>");
            }

            if (!hasResidents) {
                out.println("<tr>");
                out.println("<td colspan='3' style='text-align:center; padding:25px;'>All residents are already assigned rooms.</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<br><br>");
            out.println("<a class='btn' href='adminDashboard.jsp'>Back to Dashboard</a>");

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error: " + e.toString());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}