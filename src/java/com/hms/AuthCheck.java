package com.hms;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthCheck {

    public static boolean requireRole(HttpServletRequest request, HttpServletResponse response, String requiredRole)
            throws IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp");
            return false;
        }

        String role = (String) session.getAttribute("role");

        if (!requiredRole.equals(role)) {
            response.getWriter().println("Access denied. You are not allowed to view this page.");
            return false;
        }

        return true;
    }

    public static boolean requireAdminOrStaff(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp");
            return false;
        }

        String role = (String) session.getAttribute("role");

        if (!"admin".equals(role) && !"staff".equals(role)) {
            response.getWriter().println("Access denied. Admin or Staff only.");
            return false;
        }

        return true;
    }
}