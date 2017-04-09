<!DOCTYPE HTML><%@page import="com.cogcong.mongo.MongoFacade"%>
<%@page import="com.cogcong.model.Bill"%>
<%@page import="com.cogcong.model.Legislator"%>
<%@page import="java.util.List"%>
<%@page import="com.cogcong.stats.BillStats"%>
<%@page language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
	<% 
	int count = 10;
	String url = request.getRequestURL().toString();
	String id = request.getParameter("id");
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/"; 
	List<Bill> bills = MongoFacade.getInstance().queryAllBills(count);
	%>
	<!--  Bootstrap -->
	<link href="<%=baseURL%>bootstrap/css/bootstrap.min.css" rel="stylesheet">
<title>Legislator</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<div>
					  <h1 class="display-3">Index of All Bills</h1>
					  <hr class="my-4">
				</div>
			</div>
			<div class="col-md-2"></div>
		</div>	
		</div>
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
			<h1> Showing <%= count %> Most Recent Bills</h1>
			<table class="table table-striped table-hover table-bordered">
			    <thead>
			      <tr>
			        <th class="col-md-1">Bill ID</th>
			        <th class="col-md-2">Sponsor</th>
			        <th>Official Title</th>
			        <th>Popular Title</th>
			      </tr>
			    </thead>
			    <tbody>
			    <%for (int i = 0; i < count; i++){ %>
			      <tr>
			      	<% Bill bill = bills.get(i); %>
			        <% Legislator leg = bill.getSponsor(); %>
			        <td><a href="<%=baseURL%>bill<%="?id="+bill.getBillID()%>"><%= bill.getBillID()%></a></td>
			        <td><%= leg.getShortName() + " " + leg.getLatestPartySymbol()%></td>
			        <td><%= bill.getTitle()%></td>
			        <td><%= bill.getPopularTitle() %></td>
			      </tr>
			    <%} %>
			    </tbody>
			</table>
			</div>
			<div class="col-md-2"></div>
		</div>
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<br></br><br></br>
				<footer>
				<p>&copy; 2017, Phillip Lopez - pgl5711@rit.edu</p>
				</footer>
			</div>
			<div class="col-md-2"></div>
		</div>
	</div>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<%=baseURL%>bootstrap/js/bootstrap.min.js"></script>
</body>


</html>