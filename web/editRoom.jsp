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
    <title>Edit Room</title>
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
        <h2>Edit Room</h2>

        <form action="UpdateRoomServlet" method="post">

            <input type="hidden" name="room_id" value="${room_id}">

            Building:
            <select name="building_id" required>
                <option value="1" ${building_id == 1 ? 'selected' : ''}>Building A - North Wing</option>
                <option value="2" ${building_id == 2 ? 'selected' : ''}>Building B - South Wing</option>
                <option value="3" ${building_id == 3 ? 'selected' : ''}>Building C - East Wing</option>
                <option value="4" ${building_id == 4 ? 'selected' : ''}>Building D - West Wing</option>
                <option value="5" ${building_id == 5 ? 'selected' : ''}>Building E - Central Wing</option>
            </select>

            Room Number:
            <input type="text" name="room_number" value="${room_number}" required>

            Room Type:
            <select name="room_type" required>
                <option value="Single" ${room_type == 'Single' ? 'selected' : ''}>Single</option>
                <option value="Double" ${room_type == 'Double' ? 'selected' : ''}>Double</option>
                <option value="Suite" ${room_type == 'Suite' ? 'selected' : ''}>Suite</option>
            </select>

            Capacity:
            <input type="number" name="capacity" value="${capacity}" required>

            Floor:
            <input type="number" name="floor" value="${floor}" required>

            Status:
            <select name="status" required>
                <option value="available" ${status == 'available' ? 'selected' : ''}>available</option>
                <option value="pending" ${status == 'pending' ? 'selected' : ''}>pending</option>
                <option value="occupied" ${status == 'occupied' ? 'selected' : ''}>occupied</option>
                <option value="maintenance" ${status == 'maintenance' ? 'selected' : ''}>maintenance</option>
            </select>

            <input class="btn" type="submit" value="Update Room">

        </form>

        <br>
        <a class="btn" href="ViewRoomsServlet">Back to Rooms</a>
    </div>
</div>

</body>
</html>