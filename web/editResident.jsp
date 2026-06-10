<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("user_id") == null || !"admin".equals(session.getAttribute("role"))) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Resident</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="navbar">
    <h2>HMS Admin</h2>
    <div>
        <a href="adminDashboard.jsp">Dashboard</a>
        <a href="LogoutServlet">Logout</a>
    </div>
</div>

<div class="container">
    <div class="form-box">
        <h2>Edit Resident</h2>

        <form action="UpdateResidentServlet" method="post">
            <input type="hidden" name="resident_id" value="${resident_id}">

            Full Name:
            <input type="text" name="full_name" value="${full_name}" required>

            Student ID:
            <input type="text" name="student_id" value="${student_id}" required>

            Phone:
            <input type="text" name="phone" value="${phone}">

            Room ID:
            <input type="number" name="room_id" value="${room_id}">

            User ID:
            <input type="number" name="user_id" value="${user_id}">

            <input class="btn" type="submit" value="Update Resident">
        </form>

        <br>
        <a class="btn" href="ViewResidentsServlet">Back</a>
    </div>
</div>

</body>
</html>