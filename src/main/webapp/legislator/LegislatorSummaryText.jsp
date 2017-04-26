<!DOCTYPE HTML><%@page import="com.cogcong.model.Legislator"%>
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
	Legislator legislator = new Legislator(id);
	List<String> keywords = legislator.getTopNKeywords(count);
	List<String> subjects = legislator.getTopNSubjects(count);
	List<String> cobills = legislator.getSponsoredBills();
	List<String> bills = legislator.getMainSponsoredBills();
	%>
	<!--  Bootstrap -->
	<link href="<%=baseURL%>bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<title><%=legislator.getName()%></title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">

      // Load the Visualization API and the corechart package.
      google.charts.load('current', {'packages':['corechart']});
	
      // Set a callback to run when the Google Visualization API is loaded.
      google.charts.setOnLoadCallback(drawChart);

      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawChart() {

        // Create the data table.
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Keyword');
        data.addColumn('number', '# Of Bills Sponsored');
        data.addRows([
        <% 	int j = 0;
        	for(String keyword: keywords){ %>
        	<%= "['" + keyword + "', " + 
          		legislator.getKeywordCount(keywords.get(j++))  +  "],"%>
        <% } %>
        ]);

        // Set chart options
        var options = {'title': 'Top <%= count %> Watson Keywords',
                       'width':1000,
                       'height': 400};

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
    </script>
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
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
			<div id="chart_div"></div>		
			<hr></hr>
			<h1> Comparison to Other Legislators <button class="btn" data-toggle="collapse" data-target="#sim">Show</button></h1>
			<div id="sim" class="collapse">
			<h3> Most Similar </h3>
			<p>
			<%for(Legislator leg : legislator.getTop10Similiar()){%>
				<a href="<%=baseURL%>legislator<%="?id="+leg.getBioguide_id()%>">
				<%=leg.getName() + " " + leg.getLatestPartySymbol() + ": " + legislator.distanceTo(leg)%>
				</a>, 
			<%}%>
			</p>
			<h3> Least Similar </h3>
			<p>
			<%for(Legislator leg : legislator.getTop10Different()){%>
				<a href="<%=baseURL%>legislator<%="?id="+leg.getBioguide_id()%>">
				<%=leg.getName() + " " + leg.getLatestPartySymbol() + ": " + legislator.distanceTo(leg)%>
				</a>, 
			<%}%>
			</p>
			</div>
			
			<hr></hr>
			<h1> Sponsored Bills <button class="btn" data-toggle="collapse" data-target="#bills">Show</button> </h1>
			<h4> <%=legislator.getLastName()%> has sponsored <%=bills.size()%> bills during the 113th and 114th Congress.</h4>
			<div id="bills" class="collapse">
			<p>
			<%for(String bill : bills){%>
				<a href="<%=baseURL%>bill<%="?id="+bill%>"><%=bill%></a>, 
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