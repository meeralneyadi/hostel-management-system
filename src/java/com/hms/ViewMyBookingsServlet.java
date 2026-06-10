package com.hms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ViewMyBookingsServlet")
public class ViewMyBookingsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String role = (String) session.getAttribute("role");

        if (!"resident".equals(role)) {
            response.getWriter().println("Access denied. Residents only.");
            return;
        }

        int user_id = (int) session.getAttribute("user_id");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT b.booking_id, b.booking_date, b.status, "
                    + "r.room_number, r.room_type, r.capacity, r.floor "
                    + "FROM bookings b "
                    + "JOIN rooms r ON b.room_id = r.room_id "
                    + "JOIN residents res ON b.resident_id = res.resident_id "
                    + "WHERE res.user_id=? "
                    + "ORDER BY b.booking_id DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);

            ResultSet rs = ps.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>My Bookings</title>");
            out.println("<link rel='stylesheet' href='" + request.getContextPath() + "/css/style.css'>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class='navbar'>");
            out.println("<h2>Hostel Management System</h2>");
            out.println("<div>");
            out.println("<a href='residentDashboard.jsp'>Dashboard</a>");
            out.println("<a href='LogoutServlet'>Logout</a>");
            out.println("</div>");
            out.println("</div>");

            out.println("<div class='container'>");
            out.println("<h1 class='page-title'>My Bookings</h1>");
            out.println("<p class='section-subtitle'>Track your room booking requests and approval status.</p>");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Booking ID</th>");
            out.println("<th>Room Number</th>");
            out.println("<th>Room Type</th>");
            out.println("<th>Capacity</th>");
            out.println("<th>Floor</th>");
            out.println("<th>Booking Date</th>");
            out.println("<th>Status</th>");
            out.println("</tr>");

            boolean hasRows = false;

            while (rs.next()) {
                hasRows = true;

                out.println("<tr>");
                out.println("<td>" + rs.getInt("booking_id") + "</td>");
                out.println("<td>" + rs.getString("room_number") + "</td>");
                out.println("<td>" + rs.getString("room_type") + "</td>");
                out.println("<td>" + rs.getInt("capacity") + "</td>");
                out.println("<td>" + rs.getInt("floor") + "</td>");
                out.println("<td>" + rs.getDate("booking_date") + "</td>");
                out.println("<td>" + rs.getString("status") + "</td>");
                out.println("</tr>");
            }

            if (!hasRows) {
                out.println("<tr>");
                out.println("<td colspan='7' style='text-align:center; padding:25px;'>You have not made any bookings yet.</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<br><br>");
            out.println("<a class='btn btn-success' href='ViewAvailableRoomsServlet'>Book Another Room</a>");
            out.println("<a class='btn' href='residentDashboard.jsp'>Back to Dashboard</a>");

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