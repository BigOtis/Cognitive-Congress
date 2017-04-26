<!DOCTYPE HTML><%@page import="com.cogcong.model.Bill"%>
<%@page import="com.cogcong.model.Legislator"%>
<%@page import="java.util.List"%>
<%@page import="com.cogcong.stats.BillStats"%>
<%@page language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
	<% 
	int count = 5;
	String url = request.getRequestURL().toString();
	String id = request.getParameter("id");
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/"; 
	Bill bill = new Bill(id);
	List<String> bills = bill.getSimilarBills();
	Legislator sponsor = bill.getSponsor();
	List<String> keywords = bill.getKeywords();
	%>
	<!--  Bootstrap -->
	<link href="<%=baseURL%>bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<title><%= bill.getBillID() %></title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<div>
				  <h1 class="display-3">Bill Overview: <%= bill.getBillID()%>
				  </h1>
				  <hr></hr>
				  <div class="row">
				  <div class="col-md-6">
					  <h3>
					  Subject: <%= bill.getTopSubject() %>
					  </h3>
				  </div>
				  <div class="col-md-6 text-right">
					  <h3>
					  	  Sponsored by: 
						  <a href="<%=baseURL%>legislator<%="?id="+sponsor.getBioguide_id()%>">
						  <%= sponsor.getShortName() + " " + sponsor.getLatestPartySymbol()%></a>					
					  </h3>
				  </div>
				  </div>
				  <hr></hr>
				  <h4><p class="lead"><%= bill.getTitle() %></p></h4>
				</div>
			</div>
			<div class="col-md-2"></div>
		</div>	
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
			<h1> Top <%= count %> Watson Keywords </h1>
			<table class="table table-striped table-hover table-bordered">
			    <thead>
			      <tr>
			        <th>Keyword</th>
			        <th>Confidence</th>
			      </tr>
			    </thead>
			    <tbody>
			    <%for (int i = 0; i < count; i++){ %>
			      <tr>
			        <td><%= keywords.get(i)%></td>
			        <td><%= bill.getKeywordConfidence(keywords.get(i))%></td>
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
				<h1> Co-Sponsors </h1>
				<hr></hr>
				<% List<Legislator> cosponsors = bill.getCosponsors(); %>
				<h3>There are <%= cosponsors.size() %> co-sponsors of this bill</h3>
				<h4><%= bill.getNumRepublicanSponsors()%> Republicans and <%= bill.getNumDemocraticSponsors() %> Democrats</h4>
			    <%for (Legislator co : cosponsors){ %>
			    	<a href="<%=baseURL%>legislator<%="?id="+co.getBioguide_id()%>">
					<%= co.getShortName() + " " + co.getLatestPartySymbol()%></a>, 
			    <%} %>
			</div>
			<div class="col-md-2"></div>
		</div>
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
			<h1> Similar Bills <button class="btn" data-toggle="collapse" data-target="#bills">Show</button> </h1>
			<hr></hr>
			<div id="bills" class="collapse">
			<table class="table table-striped table-hover table-bordered">
			    <thead>
			      <tr>
			        <th class="col-md-1">Bill ID</th>
			        <th class="col-md-2">Sponsor</th>
			        <th>Official Title</th>
			        <th class="col-md-2">Top Keyword</th>
			      </tr>
			    </thead>
			    <tbody>
			    <%for (int i = 0; i < count && i < bills.size(); i++){ %>
			      <tr>
			      	<% Bill rbill = new Bill(bills.get(i)); %>
			        <% Legislator leg = rbill.getSponsor(); %>
			        <td><a href="<%=baseURL%>bill<%="?id="+rbill.getBillID()%>"><%= rbill.getBillID()%></a></td>
			        <td><%= leg.getShortName() + " " + leg.getLatestPartySymbol()%></td>
			        <td><%= rbill.getTitle()%></td>
			        <td><%= rbill.getKeywords().get(0) %></td>
			      </tr>
			    <%} %>
			    </tbody>
			</table>
			</div>
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