<%@ page import="java.util.*" %>
<%
    List<Map<String, String>> bookings =
        (List<Map<String, String>>) request.getAttribute("bookings");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Bookings List</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container">
    <h1 class="page-title">Bookings List</h1>

    <table>
        <tr>
            <th>ID</th>
            <th>Resident ID</th>
            <th>Room ID</th>
            <th>Booking Date</th>
            <th>Status</th>
            <th>Action</th>
        </tr>

        <%
            if (bookings != null) {
                for (Map<String, String> booking : bookings) {
        %>
        <tr>
            <td><%= booking.get("booking_id") %></td>
            <td><%= booking.get("resident_id") %></td>
            <td><%= booking.get("room_id") %></td>
            <td><%= booking.get("booking_date") %></td>
            <td><%= booking.get("status") %></td>
            <td>
                <a href="EditBookingServlet?id=<%= booking.get("booking_id") %>">Edit</a>
                |
                <a href="DeleteBookingServlet?id=<%= booking.get("booking_id") %>">Delete</a>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>

    <br>
    <a href="addBooking.jsp">Add New Booking</a>
    <br>
    <a href="adminDashboard.jsp">Back to Dashboard</a>
</div>

</body>
</html>