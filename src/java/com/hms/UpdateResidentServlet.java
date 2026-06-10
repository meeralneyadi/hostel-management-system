package com.hms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/UpdateResidentServlet")
public class UpdateResidentServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!AuthCheck.requireRole(request, response, "admin")) {
            return;
        }

        int resident_id = Integer.parseInt(request.getParameter("resident_id"));
        String full_name = request.getParameter("full_name");
        String student_id = request.getParameter("student_id");
        String phone = request.getParameter("phone");
        String room_id_text = request.getParameter("room_id");

        try {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE residents SET full_name=?, student_id=?, phone=?, room_id=? WHERE resident_id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, full_name);
            ps.setString(2, student_id);
            ps.setString(3, phone);

            if (room_id_text == null || room_id_text.trim().isEmpty()) {
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(4, Integer.parseInt(room_id_text));
            }

            ps.setInt(5, resident_id);

            ps.executeUpdate();

            response.sendRedirect("ViewResidentsServlet");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
