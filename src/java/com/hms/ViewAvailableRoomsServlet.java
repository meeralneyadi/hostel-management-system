package com.hms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ViewAvailableRoomsServlet")
public class ViewAvailableRoomsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String role = (String) session.getAttribute("role");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT r.*, b.building_name "
                    + "FROM rooms r "
                    + "JOIN buildings b ON r.building_id = b.building_id "
                    + "WHERE r.status='available' "
                    + "ORDER BY b.building_name, r.floor, r.room_number";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            String dashboardLink = "login.jsp";

            if ("admin".equals(role)) {
                dashboardLink = "adminDashboard.jsp";
            } else if ("resident".equals(role)) {
                dashboardLink = "residentDashboard.jsp";
            }

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Available Rooms</title>");
            out.println("<link rel='stylesheet' href='" + request.getContextPath() + "/css/style.css'>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class='navbar'>");
            out.println("<h2>Hostel Management System</h2>");
            out.println("<div>");
            out.println("<a href='" + dashboardLink + "'>Dashboard</a>");
            out.println("<a href='LogoutServlet'>Logout</a>");
            out.println("</div>");
            out.println("</div>");

            out.println("<div class='container'>");
            out.println("<h1 class='page-title'>Available Rooms</h1>");
            out.println("<p class='section-subtitle'>Select an available room to submit a booking request.</p>");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Room ID</th>");
            out.println("<th>Building</th>");
            out.println("<th>Room Number</th>");
            out.println("<th>Type</th>");
            out.println("<th>Capacity</th>");
            out.println("<th>Floor</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");

            boolean hasRows = false;

            while (rs.next()) {
                hasRows = true;

                int roomId = rs.getInt("room_id");

                out.println("<tr>");
                out.println("<td>" + roomId + "</td>");
                out.println("<td>" + rs.getString("building_name") + "</td>");
                out.println("<td>" + rs.getString("room_number") + "</td>");
                out.println("<td>" + rs.getString("room_type") + "</td>");
                out.println("<td>" + rs.getInt("capacity") + "</td>");
                out.println("<td>" + rs.getInt("floor") + "</td>");
                out.println("<td>");

                if ("resident".equals(role)) {
                    out.println("<a class='btn btn-success' href='BookRoomServlet?room_id=" + roomId + "'>Book</a>");
                } else if ("admin".equals(role)) {
                    out.println("<a class='btn' href='AdminBookRoomPageServlet'>Book for Resident</a>");
                }

                out.println("</td>");
                out.println("</tr>");
            }

            if (!hasRows) {
                out.println("<tr>");
                out.println("<td colspan='7' style='text-align:center; padding:25px;'>No available rooms at the moment.</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<br><br>");
            out.println("<a class='btn' href='" + dashboardLink + "'>Back to Dashboard</a>");

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