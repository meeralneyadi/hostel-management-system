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
    <title>Admin Dashboard</title>
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

    <h1>Welcome, <%= session.getAttribute("full_name") %></h1>
    <p>Manage hostel operations from one place.</p>

    <div class="grid">

        <div class="card">
            <h3>Room Management</h3>
            <p>View room details, add new rooms, and update room availability.</p>

            <a class="btn" href="ViewRoomsServlet">View Rooms</a>
            <a class="btn btn-success" href="addRoom.jsp">Add Room</a>
            <a class="btn" href="ChangeResidentRoomPageServlet">Change Room</a>
        </div>

        <div class="card">
            <h3>Resident Management</h3>
            <p>View registered residents and their current room assignments.</p>

            <a class="btn" href="ViewResidentsServlet">View Residents</a>
        </div>

        <div class="card">
            <h3>Booking Management</h3>
            <p>Manage room allocations, booking approvals, transfers, and check-out requests.</p>

            <a class="btn" href="AdminBookRoomPageServlet">Book Room</a>
            <a class="btn" href="ViewPendingBookingsServlet">Pending Requests</a>
            <a class="btn" href="ViewBookingsServlet">All Bookings</a>
            <a class="btn" href="ViewCheckoutRequestsServlet">Check-Out Requests</a>
        </div>

        <div class="card">
            <h3>Support Requests</h3>
            <p>Review and update resident concerns or maintenance requests.</p>

            <a class="btn" href="ViewRequestsServlet">Manage Requests</a>
        </div>

    </div>

</div>

</body>
</html>