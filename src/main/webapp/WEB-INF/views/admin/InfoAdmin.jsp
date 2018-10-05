<%-- 
    Document   : InfoAdmin
    Created on : 21-ago-2018, 19.38.22
    Author     : giulia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:admin pageTitle="Info user">
    <jsp:attribute name="pageContent">
		<div class="card card-info" id="user">
			<div class="card-body">
				<div class="media">
					<div class="my-auto user-image">
						<a class="user-link" href="${pageContext.servletContext.contextPath}/restricted/admin/ChangeImageAdmin">
							<div class="user-hover">
								<div class="user-hover-content">
									<p>Cambia immagine</p>
									<p><i class="fas fa-plus fa-2x"></i></p>
								</div>
							</div>
							<c:if test="${not empty sessionScope.user.image}">
								<img class="card-img-top img-fluid" src="${pageContext.servletContext.contextPath}${sessionScope.user.image}" alt="${sessionScope.user.name}" title="Cambia immagine">
							</c:if>
							<c:if test="${empty sessionScope.user.image}">
								<img class="card-img-top img-fluid" src="${pageContext.servletContext.contextPath}/assets/images/avatar2.png">
							</c:if>
						</a>
					</div>
					<div class="media-body">
						<h5 class="card-title text-center">Informazioni utente "${sessionScope.user.name} ${sessionScope.user.lastname}"</h5>
						<table class="table table-responsive-md">
							<tbody>
								<tr>
									<th scope="row">Nome</th>
									<td>${sessionScope.user.name}</td>
								</tr>
								<tr>
									<th scope="row">Cognome</th>
									<td>${sessionScope.user.lastname}</td>
								</tr>
								<tr>
									<th scope="row">Email</th>
									<td>${sessionScope.user.email}</td>
								</tr>
							</tbody>
						</table>
						<a href="${pageContext.servletContext.contextPath}/restricted/admin/PublicProductList" class="btn btn-light"><i class="fas fa-chevron-left"></i> Indietro</a>
						<a href="${pageContext.servletContext.contextPath}/restricted/admin/EditAdmin" class="btn btn-light float-right mx-2" title="Modifica"><i class="fas fa-pen-square"></i> Modifica</a>

					</div>
				</div>
			</div>
		</div>
		<div class="card info-user-card" id="user-responsive">
			<div class="mx-auto my-2 user-image text-center">
				<a class="user-link" href="${pageContext.servletContext.contextPath}/restricted/admin/ChangeImageAdmin">
					<div class="user-hover">
						<div class="user-hover-content">
							<i class="fas fa-plus fa-3x"></i>
						</div>
					</div>
					<c:if test="${not empty sessionScope.user.image}">
						<img class="card-img-top img-fluid" src="${pageContext.servletContext.contextPath}${sessionScope.user.image}" alt="${sessionScope.user.name}" title="Cambia immagine">
					</c:if>
					<c:if test="${empty sessionScope.user.image}">
						<img class="card-img-top img-fluid" src="${pageContext.servletContext.contextPath}/assets/images/avatar2.png">
					</c:if>
				</a>
			</div>
			<div class="card-body">
				<h5 class="card-title text-center">Informazioni utente "${sessionScope.user.name} ${sessionScope.user.lastname}"</h5>
				<table class="table table-responsive-md">
					<tbody>
						<tr>
							<th scope="row">Nome</th>
							<td>${sessionScope.user.name}</td>
						</tr>
						<tr>
							<th scope="row">Cognome</th>
							<td>${sessionScope.user.lastname}</td>
						</tr>
						<tr>
							<th scope="row">Email</th>
							<td>${sessionScope.user.email}</td>
						</tr>
					</tbody>
				</table>
				<a href="${pageContext.servletContext.contextPath}/restricted/admin/PublicProductList" class="btn btn-light"><i class="fas fa-chevron-left"></i> Indietro</a>
				<a href="${pageContext.servletContext.contextPath}/restricted/admin/EditAdmin" class="btn btn-light float-right mx-2" title="Modifica"><i class="fas fa-pen-square"></i> Modifica</a>
			</div>
		</div>


	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link href="${pageContext.servletContext.contextPath}/assets/css/info_admin.css" type="text/css" rel="stylesheet"/>
	</jsp:attribute>
	<jsp:attribute name="customJs">
		<!--<script src="assets/js/landing_page.js"></script>-->
		<script>

		</script>
	</jsp:attribute>
</layouts:admin>