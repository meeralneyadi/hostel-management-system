package com.hms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ViewMyRequestServlet")
public class ViewMyRequestServlet extends HttpServlet {

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
                    "SELECT sr.request_id, sr.title, sr.description, sr.priority, sr.status "
                    + "FROM support_requests sr "
                    + "JOIN residents r ON sr.resident_id = r.resident_id "
                    + "WHERE r.user_id=? "
                    + "ORDER BY sr.request_id DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);

            ResultSet rs = ps.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>My Requests</title>");
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
            out.println("<h1 class='page-title'>My Support Requests</h1>");
            out.println("<p class='section-subtitle'>Track your submitted concerns and their current status.</p>");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Title</th>");
            out.println("<th>Description</th>");
            out.println("<th>Priority</th>");
            out.println("<th>Status</th>");
            out.println("</tr>");

            boolean hasRows = false;

            while (rs.next()) {
                hasRows = true;

                out.println("<tr>");
                out.println("<td>" + rs.getInt("request_id") + "</td>");
                out.println("<td>" + rs.getString("title") + "</td>");
                out.println("<td>" + rs.getString("description") + "</td>");
                out.println("<td>" + rs.getString("priority") + "</td>");
                out.println("<td>" + rs.getString("status") + "</td>");
                out.println("</tr>");
            }

            if (!hasRows) {
                out.println("<tr>");
                out.println("<td colspan='5' style='text-align:center; padding:25px;'>You have not submitted any support requests yet.</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<br><br>");
            out.println("<a class='btn' href='addRequest.jsp'>Submit New Request</a>");
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