<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entities.*" %>
<%@ page import="java.util.List" %>

<%
    if (session.getAttribute("email")==null){
        response.sendRedirect("login.jsp");
    }
%>

<html>
<head>
	<meta charset="UTF-8">
	<title>Periodicals Site</title>
	<!-- CSS and JavaScript imports go here -->
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
	<main>
		    <h1>List of All Periodicals</h1>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Publishing House</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Add to cart</th>
                    </tr>
                </thead>
                <tbody>
                       <%
                       List<Periodicals> periodicals = (List<Periodicals>)session.getAttribute("allPeriodicals");
                       List<Long> periodicalsIds = (List<Long>)session.getAttribute("myPeriodicalsId");
                       if (periodicals != null) {
                               for (Periodicals periodical: periodicals) {
                                    boolean idExists = false;
                                    for (Long periodicalId : periodicalsIds) {
                                        if (periodical.getId() == periodicalId) {
                                            idExists = true;
                                            break;
                                        }
                                    }
                                    String str;
                                    if (idExists){
                                        str = "type='hidden'";
                                    }else{
                                        str = "href='myServlet?command=addtocart&perId="+periodical.getId()+"'";
                                    }
                           %>
                            <tr>
                               <td><%=periodical.getName()%></td>
                               <td><%=periodical.getPub()%></td>
                               <td><%=periodical.getInfo()%></td>
                               <td><%=periodical.getPrice()%></td>
                               <td><a onclick="this.disabled = true;" <%=str %> >Add to Cart</a></td>
                            </tr>
                       <% }}%>




                </tbody>
            </table>
	</main>
	<footer>
		<p>Stanislav Stefyuk KI-03</p>
	</footer>
</body>
</html>
