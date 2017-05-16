<!DOCTYPE HTML><%@page import="org.apache.commons.lang3.text.WordUtils"%>
<%@page import="com.cogcong.mongo.MongoFacade"%>
<%@page import="com.cogcong.model.Bill"%>
<%@page import="com.cogcong.model.Legislator"%>
<%@page import="java.util.List"%>
<%@page import="com.cogcong.stats.BillStats"%>
<%@page language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
	<% 
	int count = 15;
	String url = request.getRequestURL().toString();
	String id = request.getParameter("id");
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/"; 
	List<Bill> bills = MongoFacade.getInstance().queryAllBills(count);
	%>
	<!--  Bootstrap -->
	<link href="<%=baseURL%>bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link rel="icon" href="<%=baseURL%>img/seal.png">
	<title>Bills</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
      <!-- Static navbar -->
      <nav class="navbar navbar-default">
        <div class="container">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><img src="<%=baseURL%>img/flagsmall1.JPG	" alt="US Congress Seal" style="width:50px;height:25px"></a>
            <a class="navbar-brand" href="<%=baseURL%>">Explore the U.S. Senate</a>
          </div>
          <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
              <li><a href="<%=baseURL%>legislator/index">Senators</a></li>
              <li class="active"><a href="<%=baseURL%>bills/index">Bills<span class="sr-only">(current)</span></a></li>
              <li><a href="<%=baseURL%>bills/summary/text">Summary</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
    </nav>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<div>
					  <h1 class="display-3">Recent Bills</h1>
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
			        <th class="col-md-2">Top Keyword</th>
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
			        <td><%= WordUtils.capitalize(bill.getKeywords().get(0)) %></td>
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