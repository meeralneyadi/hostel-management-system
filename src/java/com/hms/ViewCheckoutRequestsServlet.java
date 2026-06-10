package com.hms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ViewCheckoutRequestsServlet")
public class ViewCheckoutRequestsServlet extends HttpServlet {

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
                    "SELECT cr.checkout_id, cr.request_date, cr.reason, cr.status, "
                    + "res.full_name, res.student_id, r.room_number "
                    + "FROM checkout_requests cr "
                    + "JOIN residents res ON cr.resident_id = res.resident_id "
                    + "JOIN rooms r ON cr.room_id = r.room_id "
                    + "ORDER BY cr.checkout_id DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            out.println("<!DOCTYPE html><html><head>");
            out.println("<title>Check-Out Requests</title>");
            out.println("<link rel='stylesheet' href='" + request.getContextPath() + "/css/style.css'>");
            out.println("</head><body>");

            out.println("<div class='navbar'>");
            out.println("<h2>Hostel Management System</h2>");
            out.println("<div><a href='adminDashboard.jsp'>Dashboard</a><a href='LogoutServlet'>Logout</a></div>");
            out.println("</div>");

            out.println("<div class='container'>");
            out.println("<h1 class='page-title'>Check-Out Requests</h1>");
            out.println("<p class='section-subtitle'>Review resident check-out requests and approve or reject them.</p>");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Resident</th>");
            out.println("<th>Student ID</th>");
            out.println("<th>Room</th>");
            out.println("<th>Date</th>");
            out.println("<th>Reason</th>");
            out.println("<th>Status</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");

            boolean hasRows = false;

            while (rs.next()) {
                hasRows = true;

                int checkoutId = rs.getInt("checkout_id");
                String status = rs.getString("status");

                out.println("<tr>");
                out.println("<td>" + checkoutId + "</td>");
                out.println("<td>" + rs.getString("full_name") + "</td>");
                out.println("<td>" + rs.getString("student_id") + "</td>");
                out.println("<td>" + rs.getString("room_number") + "</td>");
                out.println("<td>" + rs.getDate("request_date") + "</td>");
                out.println("<td>" + rs.getString("reason") + "</td>");
                out.println("<td>" + status + "</td>");
                out.println("<td>");

                if ("pending".equals(status)) {
                    out.println("<a class='btn btn-success' href='ApproveCheckoutServlet?id=" + checkoutId + "'>Approve</a>");
                    out.println("<a class='btn btn-danger' href='RejectCheckoutServlet?id=" + checkoutId + "'>Reject</a>");
                } else {
                    out.println(status);
                }

                out.println("</td>");
                out.println("</tr>");
            }

            if (!hasRows) {
                out.println("<tr><td colspan='8' style='text-align:center; padding:25px;'>No check-out requests found.</td></tr>");
            }

            out.println("</table>");
            out.println("<br><br>");
            out.println("<a class='btn' href='adminDashboard.jsp'>Back to Dashboard</a>");
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