<%@page import="org.apache.tomcat.util.codec.binary.Base64"%>
<%@page import="org.jsp.library.dto.Book"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Book</title>
</head>
<body>
<a href="/librarian/fetchbooks"><button>View All Books</button></a>
<% Book book = (Book) request.getAttribute("book");%>
<form action="/librarian/editbook" method="post">
	<table border="1">
		<tr>
			<th>Book Id</th>
			<th>Book Picture</th>
			<th>Book Name</th>
			<th>Book Author</th>
			<th>Book Price</th>
			<th>Stock</th>
			<th>Edit</th>
		</tr>
			<tr>
			<th><%=book.getId() %></th>
			<th>
			<%
			String base64=Base64.encodeBase64String(book.getPicture());
			%>
			<img height="100px" width="100px" alt="unknown" src="data:image/jpeg;base64,<%=base64%>">
			</th>
			<th><%=book.getName() %></th>
			<th><%=book.getAuthor() %></th>
			<th><input type="text" name="price" value="<%=book.getPrice()%>"></th>
			<th><input type="text" name="quantity" value="<%=book.getQuantity() %>"></th>
			<th><button>Edit</button></th>
		</tr>
	</table>
</form>
</body>
</html>