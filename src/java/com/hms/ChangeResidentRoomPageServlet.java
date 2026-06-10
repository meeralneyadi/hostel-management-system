package com.hms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ChangeResidentRoomPageServlet")
public class ChangeResidentRoomPageServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!AuthCheck.requireRole(request, response, "admin")) {
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement psResidents = con.prepareStatement(
                "SELECT res.resident_id, res.full_name, res.student_id, r.room_number " +
                "FROM residents res JOIN rooms r ON res.room_id = r.room_id " +
                "ORDER BY res.full_name"
            );
            ResultSet residents = psResidents.executeQuery();

            PreparedStatement psRooms = con.prepareStatement(
                "SELECT room_id, room_number, room_type, capacity, floor " +
                "FROM rooms WHERE status='available' ORDER BY floor, room_number"
            );
            ResultSet rooms = psRooms.executeQuery();

            out.println("<!DOCTYPE html><html><head>");
            out.println("<title>Change Resident Room</title>");
            out.println("<link rel='stylesheet' href='" + request.getContextPath() + "/css/style.css'>");
            out.println("</head><body>");

            out.println("<div class='navbar'>");
            out.println("<h2>Hostel Management System</h2>");
            out.println("<div><a href='adminDashboard.jsp'>Dashboard</a><a href='LogoutServlet'>Logout</a></div>");
            out.println("</div>");

            out.println("<div class='container'>");
            out.println("<h1 class='page-title'>Change Resident Room</h1>");
            out.println("<p class='section-subtitle'>Move a resident from their current room to another available room.</p>");

            out.println("<div class='form-box'>");
            out.println("<form action='ChangeResidentRoomServlet' method='post'>");

            out.println("Resident:");
            out.println("<select name='resident_id' required>");
            out.println("<option value=''>Select Resident</option>");

            while (residents.next()) {
                out.println("<option value='" + residents.getInt("resident_id") + "'>");
                out.println(residents.getString("full_name") + " - " +
                        residents.getString("student_id") + " - Current Room " +
                        residents.getString("room_number"));
                out.println("</option>");
            }

            out.println("</select>");

            out.println("New Available Room:");
            out.println("<select name='new_room_id' required>");
            out.println("<option value=''>Select New Room</option>");

            while (rooms.next()) {
                out.println("<option value='" + rooms.getInt("room_id") + "'>");
                out.println("Room " + rooms.getString("room_number") + " - " +
                        rooms.getString("room_type") + " - Capacity " +
                        rooms.getInt("capacity") + " - Floor " +
                        rooms.getInt("floor"));
                out.println("</option>");
            }

            out.println("</select>");

            out.println("<input class='btn' type='submit' value='Change Room'>");
            out.println("</form>");
            out.println("</div>");

            out.println("<br><a class='btn' href='adminDashboard.jsp'>Back to Dashboard</a>");
            out.println("</div></body></html>");

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