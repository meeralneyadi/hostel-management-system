package com.hms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ViewRequestsServlet")
public class ViewRequestsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!AuthCheck.requireAdminOrStaff(request, response)) {
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        String role = (String) session.getAttribute("role");

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT sr.request_id, sr.title, sr.description, sr.priority, sr.status, "
                    + "r.full_name, r.student_id "
                    + "FROM support_requests sr "
                    + "JOIN residents r ON sr.resident_id = r.resident_id "
                    + "ORDER BY sr.request_id DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Support Requests</title>");
            out.println("<link rel='stylesheet' href='" + request.getContextPath() + "/css/style.css'>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class='navbar'>");
            out.println("<h2>Hostel Management System</h2>");
            out.println("<div>");

            if ("admin".equals(role)) {
                out.println("<a href='adminDashboard.jsp'>Dashboard</a>");
            } else {
                out.println("<a href='staffDashboard.jsp'>Dashboard</a>");
            }

            out.println("<a href='LogoutServlet'>Logout</a>");
            out.println("</div>");
            out.println("</div>");

            out.println("<div class='container'>");
            out.println("<h1 class='page-title'>Support Requests</h1>");
            out.println("<p class='section-subtitle'>Manage resident concerns by updating their request status.</p>");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Resident</th>");
            out.println("<th>Student ID</th>");
            out.println("<th>Title</th>");
            out.println("<th>Description</th>");
            out.println("<th>Priority</th>");
            out.println("<th>Status</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");

            boolean hasRows = false;

            while (rs.next()) {
                hasRows = true;

                int requestId = rs.getInt("request_id");
                String status = rs.getString("status");

                out.println("<tr>");
                out.println("<td>" + requestId + "</td>");
                out.println("<td>" + rs.getString("full_name") + "</td>");
                out.println("<td>" + rs.getString("student_id") + "</td>");
                out.println("<td>" + rs.getString("title") + "</td>");
                out.println("<td>" + rs.getString("description") + "</td>");
                out.println("<td>" + rs.getString("priority") + "</td>");
                out.println("<td>" + status + "</td>");

                out.println("<td>");

                if ("open".equals(status)) {
                    out.println("<a class='btn btn-warning' href='UpdateRequestStatusServlet?id=" + requestId + "&status=in_progress'>Start Progress</a>");
                } else if ("in_progress".equals(status)) {
                    out.println("<a class='btn btn-success' href='UpdateRequestStatusServlet?id=" + requestId + "&status=resolved'>Mark Resolved</a>");
                } else if ("resolved".equals(status)) {
                    out.println("<a class='btn' href='UpdateRequestStatusServlet?id=" + requestId + "&status=closed'>Close</a>");
                } else if ("closed".equals(status)) {
                    out.println("Closed");
                }

                if ("admin".equals(role)) {
                    out.println(" ");
                    out.println("<a class='btn btn-danger' href='DeleteRequestServlet?id=" + requestId + "'>Delete</a>");
                }

                out.println("</td>");
                out.println("</tr>");
            }

            if (!hasRows) {
                out.println("<tr>");
                out.println("<td colspan='8' style='text-align:center; padding:25px;'>No support requests found.</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<br><br>");

            if ("admin".equals(role)) {
                out.println("<a class='btn' href='adminDashboard.jsp'>Back to Dashboard</a>");
            } else {
                out.println("<a class='btn' href='staffDashboard.jsp'>Back to Dashboard</a>");
            }

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