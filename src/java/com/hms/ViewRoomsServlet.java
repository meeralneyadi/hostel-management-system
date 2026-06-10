package com.hms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ViewRoomsServlet")
public class ViewRoomsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!AuthCheck.requireRole(request, response, "admin")) {
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT r.*, b.building_name "
                    + "FROM rooms r "
                    + "JOIN buildings b ON r.building_id = b.building_id "
                    + "ORDER BY b.building_name, r.floor, r.room_number";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Room Management</title>");
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

            out.println("<h1 class='page-title'>Room Management</h1>");
            out.println("<p class='section-subtitle'>Manage hostel rooms, buildings, and availability.</p>");

            out.println("<table>");

            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Building</th>");
            out.println("<th>Room Number</th>");
            out.println("<th>Room Type</th>");
            out.println("<th>Capacity</th>");
            out.println("<th>Floor</th>");
            out.println("<th>Status</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");

            while (rs.next()) {

                int roomId = rs.getInt("room_id");

                out.println("<tr>");

                out.println("<td>" + roomId + "</td>");
                out.println("<td>" + rs.getString("building_name") + "</td>");
                out.println("<td>" + rs.getString("room_number") + "</td>");
                out.println("<td>" + rs.getString("room_type") + "</td>");
                out.println("<td>" + rs.getInt("capacity") + "</td>");
                out.println("<td>" + rs.getInt("floor") + "</td>");
                out.println("<td>" + rs.getString("status") + "</td>");

                out.println("<td>");
                out.println("<a class='btn btn-warning' href='EditRoomServlet?id=" + roomId + "'>Edit</a>");
                out.println("<a class='btn btn-danger' href='DeleteRoomServlet?id=" + roomId + "'>Delete</a>");
                out.println("</td>");

                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<br><br>");

            out.println("<a class='btn btn-success' href='addRoom.jsp'>Add New Room</a>");
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