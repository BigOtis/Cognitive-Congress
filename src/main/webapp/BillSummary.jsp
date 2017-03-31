<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
	<% 
	String url = request.getRequestURL().toString();
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/"; 
	%>
	<!--  Bootstrap -->
	<link href="<%=baseURL%>bootstrap/css/bootstrap.min.css" rel="stylesheet">
<title>BillSummary</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<div class="container-fluid">
		<h1> Hello </h1>
	</div>
	<footer>
	<p>&copy; 2017, Phillip Lopez</p>
	</footer>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<%=baseURL%>js/bootstrap.min.js"></script>
</body>


</html>