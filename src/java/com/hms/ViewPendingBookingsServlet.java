package com.hms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ViewPendingBookingsServlet")
public class ViewPendingBookingsServlet extends HttpServlet {

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
                    "SELECT b.booking_id, b.booking_date, b.status, "
                    + "res.full_name, res.student_id, "
                    + "r.room_number, r.room_type, r.floor "
                    + "FROM bookings b "
                    + "JOIN residents res ON b.resident_id = res.resident_id "
                    + "JOIN rooms r ON b.room_id = r.room_id "
                    + "WHERE b.status='pending'";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Pending Bookings</title>");
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
            out.println("<h1 class='page-title'>Pending Bookings</h1>");
            out.println("<p class='section-subtitle'>Review resident booking requests and approve or reject them.</p>");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Booking ID</th>");
            out.println("<th>Resident</th>");
            out.println("<th>Student ID</th>");
            out.println("<th>Room</th>");
            out.println("<th>Type</th>");
            out.println("<th>Floor</th>");
            out.println("<th>Date</th>");
            out.println("<th>Status</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");

            boolean hasRows = false;

            while (rs.next()) {
                hasRows = true;

                int bookingId = rs.getInt("booking_id");

                out.println("<tr>");
                out.println("<td>" + bookingId + "</td>");
                out.println("<td>" + rs.getString("full_name") + "</td>");
                out.println("<td>" + rs.getString("student_id") + "</td>");
                out.println("<td>" + rs.getString("room_number") + "</td>");
                out.println("<td>" + rs.getString("room_type") + "</td>");
                out.println("<td>" + rs.getInt("floor") + "</td>");
                out.println("<td>" + rs.getDate("booking_date") + "</td>");
                out.println("<td>" + rs.getString("status") + "</td>");

                out.println("<td>");
                out.println("<a class='btn btn-success' href='ApproveBookingServlet?id=" + bookingId + "'>Approve</a>");
                out.println("<a class='btn btn-danger' href='RejectBookingServlet?id=" + bookingId + "'>Reject</a>");
                out.println("</td>");

                out.println("</tr>");
            }

            if (!hasRows) {
                out.println("<tr>");
                out.println("<td colspan='9' style='text-align:center; padding:25px;'>No pending bookings at the moment.</td>");
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