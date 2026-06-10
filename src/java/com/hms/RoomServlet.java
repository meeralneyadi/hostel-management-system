package com.hms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/RoomServlet")
public class RoomServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!AuthCheck.requireRole(request, response, "admin")) {
            return;
        }

        int building_id = Integer.parseInt(request.getParameter("building_id"));
        String room_number = request.getParameter("room_number");
        String room_type = request.getParameter("room_type");
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        int floor = Integer.parseInt(request.getParameter("floor"));
        String status = request.getParameter("status");

        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO rooms (building_id, room_number, room_type, capacity, floor, status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, building_id);
            ps.setString(2, room_number);
            ps.setString(3, room_type);
            ps.setInt(4, capacity);
            ps.setInt(5, floor);
            ps.setString(6, status);

            ps.executeUpdate();

            response.sendRedirect("ViewRoomsServlet");

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