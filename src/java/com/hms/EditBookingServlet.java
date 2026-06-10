package com.hms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/EditBookingServlet")
public class EditBookingServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int booking_id = Integer.parseInt(request.getParameter("id"));

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM bookings WHERE booking_id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, booking_id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                request.setAttribute("booking_id",
                        rs.getInt("booking_id"));

                request.setAttribute("resident_id",
                        rs.getInt("resident_id"));

                request.setAttribute("room_id",
                        rs.getInt("room_id"));

                request.setAttribute("booking_date",
                        rs.getDate("booking_date"));

                request.setAttribute("status",
                        rs.getString("status"));

                request.getRequestDispatcher("editBooking.jsp")
                        .forward(request, response);

            } else {

                response.getWriter().println("Booking not found.");

            }

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