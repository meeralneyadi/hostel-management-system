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
    <title>Add Booking</title>
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
        <h2>Add Booking</h2>

        <form action="BookingServlet" method="post">
            Resident ID:
            <input type="number" name="resident_id" required>

            Room ID:
            <input type="number" name="room_id" required>

            Booking Date:
            <input type="date" name="booking_date" required>

            Status:
            <select name="status">
                <option>pending</option>
                <option>approved</option>
                <option>rejected</option>
                <option>cancelled</option>
            </select>

            <input class="btn" type="submit" value="Save Booking">
        </form>

        <br>
        <a class="btn" href="adminDashboard.jsp">Back</a>
    </div>
</div>

</body>
</html>