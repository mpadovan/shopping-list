<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shared" tagdir="/WEB-INF/tags/shared/" %>
<%@tag description="This is the admin template of the application" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="pageTitle" %>
<%@attribute name="pageContent" fragment="true" required="true" %>
<%@attribute name="customCss" fragment="true" %>
<%@attribute name="customJs" fragment="true" %>

<%-- any content can be specified here e.g.: --%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<!-- Bootstrap CSS -->
		<link rel="stylesheet"
			  href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
			  integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">

		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css" integrity="sha384-3AB7yXWz4OeoZcPbieVW64vVXEwADiYyAEhwilzWsLw+9FgqpyjjStpPnpBO8o8S" crossorigin="anonymous">
		<script defer src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js" integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ" crossorigin="anonymous"></script>
		<script defer src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js" integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY" crossorigin="anonymous"></script>
		
		<!--<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assets/css/application.css" />-->
		
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assets/css/info_product.css" />
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assets/css/navbar.css" />

		<jsp:invoke fragment="customCss" />

		<title><c:out value="${pageTitle}" />Admin page</title>
	</head>
	<body>

		<%-- Container for page Content printed via JSP tag --%>
		<nav class="navbar navbar-expand-lg navbar-color mb-2">

			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav mr-auto">
					<li class="nav-item">
						<a class="nav-link btn btn-outline-light mr-2 active" href="ProductList.jsp">Prodotti</a>
					</li>
					<li class="nav-item">
						<a class="nav-link btn btn-outline-light mr-2" href="ClassProduct.html">Categorie di prodotto</a>
					</li>
					<li class="nav-item">
						<a class="nav-link btn btn-outline-light" href="ClassList.html">Categorie di lista</a>
					</li>
				</ul>
			</div>
			<div class="btn-group">
				<img src="assets/images/avatar.png" class="float-right rounded-circle" alt="..." style="max-width: 40px; max-height: 40px;">

				<a class="nav-link active dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="color: white;">
					Giulia Peserico
				</a>
				<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
					<a class="dropdown-item" href="#">Profilo</a>
					<a class="dropdown-item" href="#">Logout</a>
				</div>
			</div>
		</nav>
		<div class="container-fluid">
			<jsp:invoke fragment="pageContent" />
		</div>
		
		



		<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
		<!-- Popper.JS -->
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
		<!-- Bootstrap JS -->
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
		<!-- jQuery Custom Scroller CDN -->
		<!-- development version, includes helpful console warnings -->
		<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.10/lodash.min.js"></script>
		<jsp:invoke fragment="customJs" />
	</body>
</html>