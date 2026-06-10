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
    <title>Admin Book Room</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="navbar">
    <h2>Hostel Management System</h2>
    <div>
        <a href="adminDashboard.jsp">Dashboard</a>
        <a href="LogoutServlet">Logout</a>
    </div>
</div>

<div class="container">
    <div class="form-box">
        <h2>Book Room For Resident</h2>

        <form action="AdminBookRoomServlet" method="post">
            Resident ID:
            <input type="number" name="resident_id" required>

            Room ID:
            <input type="number" name="room_id" required>

            <input class="btn" type="submit" value="Book Room">
        </form>

        <br>
        <a class="btn" href="ViewAvailableRoomsServlet">View Available Rooms</a>
    </div>
</div>

</body>
</html>