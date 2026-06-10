package com.hms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/EditResidentServlet")
public class EditResidentServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!AuthCheck.requireRole(request, response, "admin")) {
            return;
        }
        int resident_id = Integer.parseInt(request.getParameter("id"));

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM residents WHERE resident_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, resident_id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                request.setAttribute("resident_id", rs.getInt("resident_id"));
                request.setAttribute("full_name", rs.getString("full_name"));
                request.setAttribute("student_id", rs.getString("student_id"));
                request.setAttribute("phone", rs.getString("phone"));
                request.setAttribute("room_id", rs.getString("room_id"));

                request.getRequestDispatcher("editResident.jsp").forward(request, response);
            } else {
                response.getWriter().println("Resident not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.toString());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
