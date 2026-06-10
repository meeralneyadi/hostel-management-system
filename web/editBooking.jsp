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
    <title>Edit Booking</title>
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
        <h2>Edit Booking</h2>

        <form action="UpdateBookingServlet" method="post">
            <input type="hidden" name="booking_id" value="${booking_id}">

            Resident ID:
            <input type="number" name="resident_id" value="${resident_id}" required>

            Room ID:
            <input type="number" name="room_id" value="${room_id}" required>

            Booking Date:
            <input type="date" name="booking_date" value="${booking_date}" required>

            Status:
            <select name="status">
                <option value="pending" ${status == 'pending' ? 'selected' : ''}>pending</option>
                <option value="approved" ${status == 'approved' ? 'selected' : ''}>approved</option>
                <option value="rejected" ${status == 'rejected' ? 'selected' : ''}>rejected</option>
                <option value="cancelled" ${status == 'cancelled' ? 'selected' : ''}>cancelled</option>
            </select>

            <input class="btn" type="submit" value="Update Booking">
        </form>

        <br>
        <a class="btn" href="ViewBookingsServlet">Back</a>
    </div>
</div>

</body>
</html>