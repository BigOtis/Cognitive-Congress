<!DOCTYPE HTML><%@page import="java.util.List"%>
<%@page import="com.cogcong.stats.BillStats"%>
<%@page language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
	<% 
	String url = request.getRequestURL().toString();
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/"; 
	BillStats bs = new BillStats();
	List<String> topKeywords = bs.getTopWords();
	List<String> topSubjects = bs.getTopSubjects();
	%>
	<!--  Bootstrap -->
	<link href="<%=baseURL%>bootstrap/css/bootstrap.min.css" rel="stylesheet">
<title>BillSummary</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<div class="container-fluid">
		<div class="col-md-2"></div>
		<div class="col-md-8">
		<div><!-- Keyword table -->
			<h2> Top Watson Concepts </h2>
			<p> The top keywords/concepts across all proposed bills found by IBM Watson</p>
			<table class="table table-striped">
			    <thead>
			      <tr>
			        <th>Key Concept</th>
			        <th>Number of Bills</th>
			      </tr>
			    </thead>
			    <tbody>
			    <%for (int i = 0; i < 25 && i < topKeywords.size(); i++){ %>
			      <tr>
			        <td><%= topKeywords.get(i)%></td>
			        <td><%= bs.getWordCount(topKeywords.get(i)) %></td>
			      </tr>
			    <%} %>
			    </tbody>
			</table>
		</div><!-- Keyword table -->
		<hr></hr>
		<div><!-- Subjects table -->
			<h2> Top Bill Subjects </h2>
			<p> The top subjects of bills as assigned by the US Congress</p>
			<table class="table table-striped">
			    <thead>
			      <tr>
			        <th>Subject</th>
			        <th>Number of Bills</th>
			      </tr>
			    </thead>
			    <tbody>
			    <%for (int i = 0; i < 25 && i < topSubjects.size(); i++){ %>
			      <tr>
			        <td><%= topSubjects.get(i)%></td>
			        <td><%= bs.getSubjectCount(topSubjects.get(i)) %></td>
			      </tr>
			    <%} %>
			    </tbody>
			</table>
		</div><!-- Subjects table -->
		</div>
		<div class="col-md-2"></div>
	</div>
	<footer>
	<p>&copy; 2017, Phillip Lopez</p>
	</footer>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<%=baseURL%>bootstrap/js/bootstrap.min.js"></script>
</body>


</html>