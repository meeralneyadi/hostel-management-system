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
    <title>Submit Support Request</title>
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
        <h2>Submit Support Request</h2>

        <form action="RequestServlet" method="post">

            Title:
            <input type="text" name="title" required>

            Description:
            <textarea name="description" required></textarea>

            Priority:
            <select name="priority">
                <option value="low">low</option>
                <option value="medium">medium</option>
                <option value="high">high</option>
            </select>

            <input class="btn" type="submit" value="Submit Request">

        </form>

        <br>
        <a class="btn" href="residentDashboard.jsp">Back to Dashboard</a>
    </div>
</div>

</body>
</html>