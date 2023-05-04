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
<title>List of Payments</title>
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
    <h1>List of Payments</h1>
    <table>
        <thead>
            <tr>
                <th>Amount</th>
                <th>Name</th>
                <th>Publishing House</th>
                <th>Description</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
               <%
               List<Pair<Payment, Periodicals>> pairList = (List<Pair<Payment, Periodicals>>)session.getAttribute("myPayments");
               if (pairList != null) {
                       for (Pair<Payment, Periodicals> pair : pairList) {
                           Payment payment = pair.getKey();
                           Periodicals periodical = pair.getValue();
                   %>
                    <tr>
                       <td><%=payment.getAmount()%></td>
                       <td><%=periodical.getName()%></td>
                       <td><%=periodical.getPub()%></td>
                       <td><%=periodical.getInfo()%></td>
                       <td><a onclick="this.disabled = true;" href=<%="'myServlet?command=confirmPayment&payId="+payment.getId()+"'"%>>Confirm</a>&nbsp;&nbsp;
                       <a onclick="this.disabled = true;" href=<%="'myServlet?command=deletePayment&payId="+payment.getId()+"'"%>>Delete</a></td>
                    </tr>
               <% }}%>
        </tbody>
    </table>
</body>
</html>
