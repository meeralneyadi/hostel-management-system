<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>HMS Login</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="login-container">
    <div class="login-card">
        <span class="badge">Hostel Portal</span>
        <h1>Welcome Back</h1>
        <p>Login to access the Hostel Management System</p>

        <form action="LoginServlet" method="post">
            Email
            <input type="email" name="email" required>

            Password
            <input type="password" name="password" required>

            <input class="btn" type="submit" value="Login">
        </form>

        <p style="color:red;">${error}</p>
    </div>
</div>

</body>
</html>