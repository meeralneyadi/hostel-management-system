package com.hms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ViewResidentsServlet")
public class ViewResidentsServlet extends HttpServlet {

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
                    "SELECT res.resident_id, res.full_name, res.student_id, res.phone, "
                    + "r.room_number, r.room_type, r.floor "
                    + "FROM residents res "
                    + "LEFT JOIN rooms r ON res.room_id = r.room_id "
                    + "ORDER BY res.resident_id";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Residents List</title>");
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
            out.println("<h1 class='page-title'>Resident Management</h1>");
            out.println("<p class='section-subtitle'>View registered residents and their current room assignments.</p>");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Full Name</th>");
            out.println("<th>Student ID</th>");
            out.println("<th>Phone</th>");
            out.println("<th>Room Assignment</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");

            while (rs.next()) {
                int residentId = rs.getInt("resident_id");
                String roomNumber = rs.getString("room_number");
                String roomType = rs.getString("room_type");
                int floor = rs.getInt("floor");

                out.println("<tr>");
                out.println("<td>" + residentId + "</td>");
                out.println("<td>" + rs.getString("full_name") + "</td>");
                out.println("<td>" + rs.getString("student_id") + "</td>");
                out.println("<td>" + rs.getString("phone") + "</td>");

                if (roomNumber == null) {
                    out.println("<td>Not Assigned</td>");
                } else {
                    out.println("<td>Room " + roomNumber + " - " + roomType + " - Floor " + floor + "</td>");
                }

                out.println("<td>");
                out.println("<a class='btn btn-warning' href='EditResidentServlet?id=" + residentId + "'>Edit</a>");
                out.println("<a class='btn btn-danger' href='DeleteResidentServlet?id=" + residentId + "'>Delete</a>");
                out.println("</td>");

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