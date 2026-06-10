<%@ page import="java.util.*" %>
<%
    List<Map<String, String>> requests =
        (List<Map<String, String>>) request.getAttribute("requests");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Support Requests</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container">
    <h1 class="page-title">Support Requests List</h1>

    <table>
        <tr>
            <th>ID</th>
            <th>Resident ID</th>
            <th>Title</th>
            <th>Description</th>
            <th>Priority</th>
            <th>Status</th>
            <th>Action</th>
        </tr>

        <%
            if (requests != null) {
                for (Map<String, String> supportRequest : requests) {
        %>
        <tr>
            <td><%= supportRequest.get("request_id") %></td>
            <td><%= supportRequest.get("resident_id") %></td>
            <td><%= supportRequest.get("title") %></td>
            <td><%= supportRequest.get("description") %></td>
            <td><%= supportRequest.get("priority") %></td>
            <td><%= supportRequest.get("status") %></td>
            <td>
                <a href="EditRequestServlet?id=<%= supportRequest.get("request_id") %>">Edit</a>
                |
                <a href="DeleteRequestServlet?id=<%= supportRequest.get("request_id") %>">Delete</a>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>

    <br>
    <a href="addRequest.jsp">Add New Request</a>
    <br>
    <a href="dashboard.jsp">Back to Dashboard</a>
</div>

</body>
</html>