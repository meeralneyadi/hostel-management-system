<%@ page import="javax.servlet.http.HttpSession" %>
<%
    HttpSession currentSession = request.getSession(false);

    if (currentSession == null || currentSession.getAttribute("role") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String role = (String) currentSession.getAttribute("role");

    if ("admin".equals(role)) {
        response.sendRedirect("adminDashboard.jsp");
    } else if ("resident".equals(role)) {
        response.sendRedirect("residentDashboard.jsp");
    } else if ("staff".equals(role)) {
        response.sendRedirect("staffDashboard.jsp");
    } else {
        response.sendRedirect("login.jsp");
    }
%>