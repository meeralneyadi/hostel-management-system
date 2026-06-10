package com.hms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/UpdateBookingServlet")
public class UpdateBookingServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int booking_id =
                Integer.parseInt(
                        request.getParameter("booking_id"));

        int resident_id =
                Integer.parseInt(
                        request.getParameter("resident_id"));

        int room_id =
                Integer.parseInt(
                        request.getParameter("room_id"));

        String booking_date =
                request.getParameter("booking_date");

        String status =
                request.getParameter("status");

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "UPDATE bookings SET resident_id=?, room_id=?, booking_date=?, status=? WHERE booking_id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, resident_id);
            ps.setInt(2, room_id);
            ps.setString(3, booking_date);
            ps.setString(4, status);
            ps.setInt(5, booking_id);

            ps.executeUpdate();

            response.sendRedirect("ViewBookingsServlet");

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "Error: " + e.toString());

        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);

    }
}