<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Librarian Home</title>
</head>

<body>
    <h1 style="color: green;">${pos}</h1>
    <h1 style="color: red;">${neg}</h1>
    <a href="/librarian/addbook"><button>Add Book</button></a>
    <a href="/librarian/fetchbooks"><button>View Book</button></a>
    <a><button>Book Records</button></a>
    <a><button>Update Stock</button></a>
</body>
</html>