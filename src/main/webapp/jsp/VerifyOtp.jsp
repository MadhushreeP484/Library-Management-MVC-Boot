<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>OTP Verification</title>
</head>
<body>
<h1 style="color: green;">${pos}</h1>
<h1 style="color: red;">${neg}</h1>
<form action="/librarian/signup/${id}" method="post">
Enter OTP :<input type="text" name="otp">
<button type="submit">Submit</button>
</form>
</body>
</html>