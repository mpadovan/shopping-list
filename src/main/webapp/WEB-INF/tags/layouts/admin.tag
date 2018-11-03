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


		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assets/css/navbar_admin.css" />
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assets/css/admin_page.css" />
		<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
		<jsp:invoke fragment="customCss" />

		<title><c:out value="${pageTitle}" /></title>
	</head>
	<body>

		<%-- Container for page Content printed via JSP tag --%>
		<nav class="navbar navbar-expand-lg navbar-dark navbar-color">
			<a class="navbar-brand" style="color: white;">Amministratore</a>
			<a class="nav-link navbar-toggler" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</a>

			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav mr-auto">
					<li class="nav-item">
						<a id="productList" class="nav-link" href="${pageContext.servletContext.contextPath}/restricted/admin/PublicProductList">Prodotti</a>
					</li>
					<li class="nav-item">
						<a id="categoryProduct" class="nav-link" href="${pageContext.servletContext.contextPath}/restricted/admin/ProductsCategory">Categorie di prodotto</a>
					</li>
					<li class="nav-item">
						<a id="categoryList" class="nav-link" href="${pageContext.servletContext.contextPath}/restricted/admin/ListCategory">Categorie di lista</a>
					</li>
				</ul>
				<ul class="navbar-nav ml-auto">
					<li class="nav-item dropdown">
						<c:if test="${not empty sessionScope.user.image}">
							<img style="max-width: 40px; max-height: 40px; display: inline;"src="${pageContext.servletContext.contextPath}${sessionScope.user.image}" class="rounded-circle img-fluid user-image" alt="immagine profilo">
						</c:if>
						<c:if test="${empty sessionScope.user.image}">
							<img style="max-width: 40px; max-height: 40px; display: inline;"src="${pageContext.servletContext.contextPath}/assets/images/avatar2.png" class="rounded-circle img-fluid user-image" alt="immagine profilo">
						</c:if> 

						<a href="#" class="nav-link active dropdown-toggle" 
						   style="display: inline;"
						   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="color: white;">
							${sessionScope.user.name} ${sessionScope.user.lastname}
						</a>
						<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
							<a class="dropdown-item" href="${pageContext.servletContext.contextPath}/restricted/admin/InfoAdmin">Profilo</a>
							<a class="dropdown-item" href="${pageContext.servletContext.contextPath}/restricted/HomePageLogin/${sessionScope.user.id}">Vai al sito</a>
							<div class="dropdown-divider"></div>
							<a class="dropdown-item" href="${pageContext.servletContext.contextPath}/restricted/Logout">Logout</a>
						</div>
					</li>
				</ul>
			</div>

		</nav>
		<div class="container-fluid mt-3">
			<jsp:invoke fragment="pageContent" />
		</div>



		<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
		<!-- Popper.JS -->
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
		<!-- Bootstrap JS -->
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
		<!-- jQuery Custom Scroller CDN -->
		<!-- development version, includes helpful console warnings -->
		<script src="${pageContext.servletContext.contextPath}/assets/js/admin_page.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
		<jsp:invoke fragment="customJs" />

	</body>
</html>