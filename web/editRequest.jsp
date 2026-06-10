<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("user_id") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Support Request</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="navbar">
    <h2>Hostel Management System</h2>
    <div>
        <a href="staffDashboard.jsp">Dashboard</a>
        <a href="LogoutServlet">Logout</a>
    </div>
</div>

<div class="container">
    <div class="form-box">
        <h2>Edit Support Request</h2>

        <form action="UpdateRequestServlet" method="post">
            <input type="hidden" name="request_id" value="${request_id}">

            Resident ID:
            <input type="number" name="resident_id" value="${resident_id}" required>

            Title:
            <input type="text" name="title" value="${title}" required>

            Description:
            <textarea name="description" required>${description}</textarea>

            Priority:
            <select name="priority">
                <option value="low" ${priority == 'low' ? 'selected' : ''}>low</option>
                <option value="medium" ${priority == 'medium' ? 'selected' : ''}>medium</option>
                <option value="high" ${priority == 'high' ? 'selected' : ''}>high</option>
            </select>

            Status:
            <select name="status">
                <option value="open" ${status == 'open' ? 'selected' : ''}>open</option>
                <option value="in_progress" ${status == 'in_progress' ? 'selected' : ''}>in_progress</option>
                <option value="resolved" ${status == 'resolved' ? 'selected' : ''}>resolved</option>
                <option value="closed" ${status == 'closed' ? 'selected' : ''}>closed</option>
            </select>

            <input class="btn" type="submit" value="Update Request">
        </form>

        <br>
        <a class="btn" href="ViewRequestsServlet">Back</a>
    </div>
</div>

</body>
</html>