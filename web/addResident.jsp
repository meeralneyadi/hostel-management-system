<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h2>Add Resident</h2>

<form action="ResidentServlet" method="post">

    Full Name:
    <input type="text" name="full_name" required><br><br>

    Student ID:
    <input type="text" name="student_id" required><br><br>

    Phone:
    <input type="text" name="phone"><br><br>

    Room ID:
    <input type="number" name="room_id"><br><br>

    <input type="submit" value="Save Resident">

</form>

<br>
<a href="dashboard.jsp">Back Dashboard</a>