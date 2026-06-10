<%@ page import="java.util.*" %>
<%
    List<Map<String, String>> rooms =
        (List<Map<String, String>>) request.getAttribute("rooms");
%>

<!DOCTYPE html>
<html>
<head>
    <title>View Rooms</title>
    <link rel="stylesheet" href="style.css">
</head>

<body>

<div class="container">
    <h1 class="page-title">Room Management</h1>
    <p class="section-subtitle">Manage hostel rooms and availability.</p>

    <table>
        <tr>
            <th>Room ID</th>
            <th>Room Number</th>
            <th>Room Type</th>
            <th>Capacity</th>
            <th>Floor</th>
            <th>Status</th>
            <th>Action</th>
        </tr>

        <%
            if (rooms != null) {
                for (Map<String, String> room : rooms) {
        %>
        <tr>
            <td><%= room.get("room_id") %></td>
            <td><%= room.get("room_number") %></td>
            <td><%= room.get("room_type") %></td>
            <td><%= room.get("capacity") %></td>
            <td><%= room.get("floor") %></td>
            <td><%= room.get("status") %></td>
            <td>
                <a href="EditRoomServlet?id=<%= room.get("room_id") %>">Edit</a>
                |
                <a href="DeleteRoomServlet?id=<%= room.get("room_id") %>">Delete</a>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>

    <br>
    <a href="adminDashboard.jsp">Back to Dashboard</a>
</div>

</body>
</html>