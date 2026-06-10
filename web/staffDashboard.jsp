<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("user_id") == null || !"staff".equals(session.getAttribute("role"))) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Staff Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="navbar">
    <h2>Hostel Management System</h2>
    <div>
        <a href="staffDashboard.jsp">Dashboard</a>
        <a href="LogoutServlet">Logout</a>
    </div>
</div>

<div class="container">
    <h1>Welcome, <%= session.getAttribute("full_name") %></h1>
    <p>Support request management</p>

    <div class="card">
        <h3>Support Requests</h3>
        <p>View, update, and manage resident concerns.</p>
        <a class="btn" href="ViewRequestsServlet">Manage Requests</a>
    </div>
</div>

</body>
</html>