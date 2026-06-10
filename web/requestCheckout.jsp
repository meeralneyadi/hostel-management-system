<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("user_id") == null || !"resident".equals(session.getAttribute("role"))) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Request Check-Out</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="navbar">
    <h2>Hostel Management System</h2>
    <div>
        <a href="residentDashboard.jsp">Dashboard</a>
        <a href="LogoutServlet">Logout</a>
    </div>
</div>

<div class="container">
    <div class="form-box">
        <h2>Request Check-Out</h2>

        <form action="RequestCheckoutServlet" method="post">
            Reason:
            <textarea name="reason" required></textarea>

            <input class="btn" type="submit" value="Submit Check-Out Request">
        </form>

        <br>
        <a class="btn" href="residentDashboard.jsp">Back to Dashboard</a>
    </div>
</div>

</body>
</html>