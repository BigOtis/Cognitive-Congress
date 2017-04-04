<!DOCTYPE HTML><%@page import="com.cogcong.mongo.MongoFacade"%>
<%@page import="com.cogcong.model.Legislator"%>
<%@page import="java.util.List"%>
<%@page import="com.cogcong.stats.BillStats"%>
<%@page language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
	<% 
	String url = request.getRequestURL().toString();
	String id = request.getParameter("id");
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/"; 
	List<Legislator> legislators = MongoFacade.getInstance().queryAllLegislators();
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
					  <h1 class="display-3">Legislators Index</h1>
					  <p class="lead">An alphabetical index of all U.S. Senators from the 113th and 114th Congress</p>
					  <hr class="my-4">
				</div>
			</div>
			<div class="col-md-2"></div>
		</div>	
		</div>
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
			<table class="table table-striped table-hover table-bordered">
			    <thead>
			      <tr>
			      	<th>Number</th>
			        <th>Name</th>
			        <th>Party</th>
			        <th># Bills Sponsored</th>
			        <th>Top Issue</th>
			      </tr>
			    </thead>
			    <tbody>
			    <%for (int i = 0; i < legislators.size(); i++){ %>
			      <tr>
			      	<% Legislator l = legislators.get(i); %>
			        <td><%=i%></td>
			        <td><a href="<%=baseURL%>legislator<%="?id=" + l.getBioguide_id()%>"><%=l.getName()%></a></td>
					<td><%=l.getLatestPartySymbol()%></td>
					<td><%=l.getMainSponsoredBills().size()%></td>
					<td><%=l.getTopNKeywords(5).get(0)%></td>
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