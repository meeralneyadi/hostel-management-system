package com.hms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/EditRoomServlet")
public class EditRoomServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!AuthCheck.requireRole(request, response, "admin")) {
            return;
        }

        int room_id = Integer.parseInt(request.getParameter("id"));

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM rooms WHERE room_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, room_id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                request.setAttribute("room_id", rs.getInt("room_id"));
                request.setAttribute("building_id", rs.getInt("building_id"));
                request.setAttribute("room_number", rs.getString("room_number"));
                request.setAttribute("room_type", rs.getString("room_type"));
                request.setAttribute("capacity", rs.getInt("capacity"));
                request.setAttribute("floor", rs.getInt("floor"));
                request.setAttribute("status", rs.getString("status"));

                request.getRequestDispatcher("editRoom.jsp").forward(request, response);

            } else {
                response.getWriter().println("Room not found.");
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