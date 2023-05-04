<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Login Page</title>
</head>
<body>
    <script>
         var message = '<%= request.getAttribute("message") %>';
         console.log(message);
    </script>
	<h1>Login Page</h1>
	<form action="myServlet" method="POST">
	    <input type="hidden" name="command" value="login">
		<label>Email:</label>
		<input type="email" name="email"><br><br>
		<label>Password:</label>
		<input type="password" name="password"><br><br>
		<input type="submit" value="Login">
	</form>
	<p>Don`t have an account? <a href="signup.jsp">Sign up here</a></p>
</body>
</html>
