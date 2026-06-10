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
    <title>Add Room</title>
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
        <h2>Add Room</h2>

        <form action="RoomServlet" method="post">

            Building:
            <select name="building_id" required>
                <option value="">Select Building</option>
                <option value="1">Building A - North Wing</option>
                <option value="2">Building B - South Wing</option>
                <option value="3">Building C - East Wing</option>
                <option value="4">Building D - West Wing</option>
                <option value="5">Building E - Central Wing</option>
            </select>

            Room Number:
            <input type="text" name="room_number" required>

            Room Type:
            <select name="room_type" required>
                <option value="Single">Single</option>
                <option value="Double">Double</option>
                <option value="Suite">Suite</option>
            </select>

            Capacity:
            <input type="number" name="capacity" required>

            Floor:
            <input type="number" name="floor" required>

            Status:
            <select name="status" required>
                <option value="available">available</option>
                <option value="occupied">occupied</option>
                <option value="maintenance">maintenance</option>
                <option value="pending">pending</option>
            </select>

            <input class="btn" type="submit" value="Save Room">
        </form>

        <br>
        <a class="btn" href="ViewRoomsServlet">Back to Rooms</a>
    </div>
</div>

</body>
</html>