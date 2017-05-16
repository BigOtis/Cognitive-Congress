<!DOCTYPE HTML><%@page import="org.apache.commons.lang3.text.WordUtils"%>
<%@page import="com.cogcong.mongo.MongoFacade"%>
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
	<link rel="icon" href="<%=baseURL%>img/seal.png">
	<title>Legislator Index</title>
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
              <li class="active"><a href="./">Senators <span class="sr-only">(current)</span></a></li>
              <li><a href="<%=baseURL%>bills/index">Bills</a></li>
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
			        <td><%=(i+1)%></td>
			        <td><a href="<%=baseURL%>legislator<%="?id=" + l.getBioguide_id()%>"><%=l.getName()%></a></td>
					<td><%=l.getLatestPartySymbol()%></td>
					<td><%=l.getMainSponsoredBills().size()%></td>
					<td><%=WordUtils.capitalize(l.getTopNKeywords(5).get(0))%></td>
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