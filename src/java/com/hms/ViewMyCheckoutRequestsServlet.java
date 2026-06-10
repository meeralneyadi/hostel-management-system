package com.hms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ViewMyCheckoutRequestsServlet")
public class ViewMyCheckoutRequestsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (!"resident".equals(session.getAttribute("role"))) {
            response.getWriter().println("Access denied. Residents only.");
            return;
        }

        int user_id = (int) session.getAttribute("user_id");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT cr.checkout_id, cr.request_date, cr.reason, cr.status, r.room_number "
                    + "FROM checkout_requests cr "
                    + "JOIN residents res ON cr.resident_id = res.resident_id "
                    + "JOIN rooms r ON cr.room_id = r.room_id "
                    + "WHERE res.user_id=? "
                    + "ORDER BY cr.checkout_id DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();

            out.println("<!DOCTYPE html><html><head>");
            out.println("<title>My Check-Out Requests</title>");
            out.println("<link rel='stylesheet' href='" + request.getContextPath() + "/css/style.css'>");
            out.println("</head><body>");

            out.println("<div class='navbar'>");
            out.println("<h2>Hostel Management System</h2>");
            out.println("<div><a href='residentDashboard.jsp'>Dashboard</a><a href='LogoutServlet'>Logout</a></div>");
            out.println("</div>");

            out.println("<div class='container'>");
            out.println("<h1 class='page-title'>My Check-Out Requests</h1>");
            out.println("<p class='section-subtitle'>Track your check-out request status.</p>");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Room</th>");
            out.println("<th>Date</th>");
            out.println("<th>Reason</th>");
            out.println("<th>Status</th>");
            out.println("</tr>");

            boolean hasRows = false;

            while (rs.next()) {
                hasRows = true;

                out.println("<tr>");
                out.println("<td>" + rs.getInt("checkout_id") + "</td>");
                out.println("<td>" + rs.getString("room_number") + "</td>");
                out.println("<td>" + rs.getDate("request_date") + "</td>");
                out.println("<td>" + rs.getString("reason") + "</td>");
                out.println("<td>" + rs.getString("status") + "</td>");
                out.println("</tr>");
            }

            if (!hasRows) {
                out.println("<tr><td colspan='5' style='text-align:center; padding:25px;'>No check-out requests found.</td></tr>");
            }

            out.println("</table>");
            out.println("<br><br>");
            out.println("<a class='btn' href='requestCheckout.jsp'>Submit New Request</a>");
            out.println("<a class='btn' href='residentDashboard.jsp'>Back to Dashboard</a>");
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