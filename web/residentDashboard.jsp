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
    <title>Resident Dashboard</title>
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

    <h1>Welcome, <%= session.getAttribute("full_name") %></h1>
    <p>Manage your room booking, support requests, and check-out requests.</p>

    <div class="grid">

        <div class="card">
            <h3>Room Booking</h3>
            <p>Browse available rooms and submit a booking request for admin approval.</p>

            <a class="btn" href="ViewAvailableRoomsServlet">View Available Rooms</a>
            <a class="btn" href="ViewMyBookingsServlet">My Bookings</a>
        </div>

        <div class="card">
            <h3>Support Requests</h3>
            <p>Submit hostel concerns or maintenance issues and track their status.</p>

            <a class="btn" href="addRequest.jsp">Submit Request</a>
            <a class="btn" href="ViewMyRequestServlet">My Requests</a>
        </div>

        <div class="card">
            <h3>Check-Out Requests</h3>
            <p>Submit a check-out request and view the admin approval status.</p>

            <a class="btn" href="requestCheckout.jsp">Request Check-Out</a>
            <a class="btn" href="ViewMyCheckoutRequestsServlet">My Check-Out Requests</a>
        </div>

    </div>

</div>

</body>
</html>