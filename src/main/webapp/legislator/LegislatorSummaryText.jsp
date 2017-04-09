<!DOCTYPE HTML><%@page import="com.cogcong.model.Legislator"%>
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
	Legislator legislator = new Legislator(id);
	List<String> keywords = legislator.getTopNKeywords(count);
	List<String> subjects = legislator.getTopNSubjects(count);
	List<String> cobills = legislator.getSponsoredBills();
	List<String> bills = legislator.getMainSponsoredBills();
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
					  <h1 class="display-3"><%= legislator.getName() + " (" + legislator.getLatestParty().substring(0, 1) + ")" %></h1>
					  <p class="lead">Legislator Overview</p>
					  <hr class="my-4">
				</div>
			</div>
			<div class="col-md-2"></div>
		</div>	
		</div>
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
			<h1> Top <%= count %> Issues </h1>
			<h4> <%=legislator.getLastName()%> has sponsored/co-sponsored <%=cobills.size()%> bills during the 113th and 114th Congress. These are the top issues they addressed.</h4>
			<table class="table table-striped table-hover table-bordered">
			    <thead>
			      <tr>
			        <th>Issue</th>
			        <th># Bills Co-Sponsored</th>
			      </tr>
			    </thead>
			    <tbody>
			    <%for (int i = 0; i < count; i++){ %>
			      <tr>
			        <td><%= keywords.get(i)%></td>
			        <td><%= legislator.getKeywordCount(keywords.get(i)) %></td>
			      </tr>
			    <%} %>
			    </tbody>
			</table>
			<hr></hr>
			<h1> Top <%= count %> Categories </h1>
			<h4> Here are the top categories <%=legislator.getLastName()%>'s bills addressed </h4>
			<table class="table table-striped table-hover table-bordered">
			    <thead>
			      <tr>
			        <th>Category</th>
			        <th># Bills Co-Sponsored</th>
			      </tr>
			    </thead>
			    <tbody>
			    <%for (int i = 0; i < count; i++){ %>
			      <tr>
			        <td><%= subjects.get(i)%></td>
			        <td><%= legislator.getSubjectCount(subjects.get(i)) %></td>
			      </tr>
			    <%} %>
			    </tbody>
			</table>
			<hr></hr>
			<h1> Comparison to Other Legislators <button class="btn" data-toggle="collapse" data-target="#demo">Show</button></h1>
			<div id="demo" class="collapse">
			<h3> Most Similar </h3>
			<p>
			<%for(Legislator leg : legislator.getTop10Similiar()){%>
				<%=leg.getName() + " " + leg.getLatestPartySymbol() + ": " + legislator.distanceTo(leg)%>, 
			<%}%>
			</p>
			<h3> Least Similar </h3>
			<p>
			<%for(Legislator leg : legislator.getTop10Different()){%>
				<%=leg.getName() + " " + leg.getLatestPartySymbol() + ": " + legislator.distanceTo(leg)%>, 
			<%}%>
			</p>
			</div>
			
			<hr></hr>
			<h1> Sponsored Bills <button class="btn" data-toggle="collapse" data-target="#demo">Show</button> </h1>
			<h4> <%=legislator.getLastName()%> has sponsored <%=bills.size()%> bills during the 113th and 114th Congress.</h4>
			<div id="demo" class="collapse">
			<p>
			<%for(String bill : bills){%>
				<%=bill%>, 
			<%}%>
			</p>
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