<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("email")==null){
        response.sendRedirect("login.jsp");
    }
%>
<%@ page import="entities.*" %>
<%@ page import="java.util.List" %>
<%@ page import="utils.Pair" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>List of Periodicals</title>
</head>
<body>
    <header>
		<nav>
			<ul>
				<li><a href="myServlet?command=gotocart">Shopping Cart</a></li>
				<li><a href="myServlet?command=gotomain">Main</a></li>
				<li><a href="myServlet?command=gotolibrary">Library</a></li>
				<li><a href="myServlet?command=logout">Logout</a></li>
			</ul>
		</nav>
	</header>
    <h1>List of My Periodicals</h1>
    <table>
        <thead>
            <tr>
                <th>Name</th>
                <th>Publishing House</th>
                <th>Description</th>
            </tr>
        </thead>
        <tbody>
               <%
               List<Pair<Payment, Periodicals>> pairList = (List<Pair<Payment, Periodicals>>)session.getAttribute("completedPayments");
               if (pairList != null) {
                       for (Pair<Payment, Periodicals> pair : pairList) {
                           Payment payment = pair.getKey();
                           Periodicals periodical = pair.getValue();
                   %>
                    <tr>
                       <td><%=periodical.getName()%></td>
                       <td><%=periodical.getPub()%></td>
                       <td><%=periodical.getInfo()%></td>
                    </tr>
               <% }}%>
        </tbody>
    </table>
</body>
</html>
