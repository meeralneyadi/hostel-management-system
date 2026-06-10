<%@ page import="java.util.*" %>
<%
    List<Map<String, String>> residents =
        (List<Map<String, String>>) request.getAttribute("residents");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Residents List</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container">
    <h1 class="page-title">Residents List</h1>

    <table>
        <tr>
            <th>ID</th>
            <th>Full Name</th>
            <th>Student ID</th>
            <th>Phone</th>
            <th>Room ID</th>
            <th>Action</th>
        </tr>

        <%
            if (residents != null) {
                for (Map<String, String> resident : residents) {
        %>
        <tr>
            <td><%= resident.get("resident_id") %></td>
            <td><%= resident.get("full_name") %></td>
            <td><%= resident.get("student_id") %></td>
            <td><%= resident.get("phone") %></td>
            <td><%= resident.get("room_id") %></td>
            <td>
                <a href="EditResidentServlet?id=<%= resident.get("resident_id") %>">Edit</a>
                |
                <a href="DeleteResidentServlet?id=<%= resident.get("resident_id") %>">Delete</a>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>

    <br>
    <a href="addResident.jsp">Add New Resident</a>
    <br>
    <a href="adminDashboard.jsp">Back to Dashboard</a>
</div>

</body>
</html>