<%@page import="org.jsp.library.dto.Book"%>
<%@page import="org.apache.tomcat.util.codec.binary.Base64"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Librarian View Book</title>
</head>
<body>
<h1 style="color: red;">${neg}</h1>
	<%
	List<Book> books = (List<Book>) request.getAttribute("books");
	%>
	<h1>${neg}</h1>
	<form action="/librarian/fetchbook" method="post">
	<input type="text" name="name">
	<button>Search</button>
	</form><br>

    <a href="/librarian/fetchbooks"><button>View All Books</button></a>
	<table border="1">
		<tr>
			<th>Book Id</th>
			<th>Book Picture</th>
			<th>Book Name</th>
			<th>Book Author</th>
			<th>Book Price</th>
			<th>Stock</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
		<%for(Book book:books) {%>
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
			<th><%=book.getPrice() %></th>
			<th><%=book.getQuantity() %></th>
			<th><a href="/librarian/editbook/id/<%=book.getId()%>"><button>Edit</button></a></th>
			<th><a href="/librarian/book/delete/<%=book.getId()%>"><button>Delete</button></a></th>
		</tr>
		<%} %>
	</table>
</body>
</html>