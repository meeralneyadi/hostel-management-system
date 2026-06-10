package com.hms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/DeleteRoomServlet")
public class DeleteRoomServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        if (!AuthCheck.requireRole(request, response, "admin")) {
            return;
        }
        int room_id
                = Integer.parseInt(request.getParameter("id"));

        try {

            Connection con = DBConnection.getConnection();

            String sql
                    = "DELETE FROM rooms WHERE room_id=?";

            PreparedStatement ps
                    = con.prepareStatement(sql);

            ps.setInt(1, room_id);

            ps.executeUpdate();

            response.sendRedirect("ViewRoomsServlet");

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "Error: " + e.toString());

        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);

    }
}
