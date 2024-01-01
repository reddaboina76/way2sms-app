<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<font color="red">${msg}</font> </br> 
<form action="sendSmsByJson" method="post">
Mobile Number : <input type="text" name="mobile"/></br></br></br>
Text : <textarea rows="10" cols="20" name="text"></textarea></br></br>
<input type="submit" value="Send Sms">
</form>
</body>
</html>