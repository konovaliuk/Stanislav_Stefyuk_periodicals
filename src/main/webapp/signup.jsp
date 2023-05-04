<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Signup Page</title>
</head>
<body>
    <script>
         var message = '<%= request.getAttribute("message") %>';
         console.log(message);
    </script>
	<h1>Signup Page</h1>
	<form action="myServlet" method="POST">
	    <input type="hidden" name="command" value="signup">
		<label>Username:</label>
		<input type="text" name="name"><br><br>
		<label>Password:</label>
		<input type="password" name="password"><br><br>
		<label>Email:</label>
		<input type="email" name="email"><br><br>
		<input type="submit" value="Sign up">
	</form>
	<p>Already have an account? <a href="myServlet?command=gotologin">Login here</a></p>
</body>
</html>