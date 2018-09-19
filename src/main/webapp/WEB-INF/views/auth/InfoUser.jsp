<%-- 
    Document   : User
    Created on : 17-lug-2018, 20.58.30
    Author     : Giulia Peserico
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:base pageTitle="Info user">
    <jsp:attribute name="pageContent">
		<div class="card info-user-card">
			<div class="card-body">
				<div class="row">
					<div class="col-3">
						<c:if test="${not empty sessionScope.user.image}">
							<img style="max-width: 100px; max-height: 100px;" src="${pageContext.servletContext.contextPath}${sessionScope.user.image}" alt="${sessionScope.user.name} ${sessionScope.user.lastname}" title="Immagine profilo">
						</c:if>
						<c:if test="${empty sessionScope.user.image}">
							<img style="max-width: 100px; max-height: 100px;" src="${pageContext.servletContext.contextPath}/assets/image/avatar2.png" alt="${sessionScope.user.name} ${sessionScope.user.lastname}" title="Immagine profilo">
						</c:if>
						<a href="${pageContext.servletContext.contextPath}/restricted/ChangeImageUser" class="btn btn-light mx-2 mt-3">Cambia immagine</a>
					</div>
					<div class="col">
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
						<a href="${pageContext.servletContext.contextPath}/restricted/HomePageLogin/${sessionScope.user.id}" class="btn btn-light"><i class="fas fa-chevron-left"></i> Indietro</a>
						<a href="${pageContext.servletContext.contextPath}/restricted/EditUser" class="btn btn-light float-right mx-2" title="Modifica"><i class="fas fa-pen-square"></i> Modifica</a>
					</div>
				</div>
			</div>
		</div>


	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link href="${pageContext.servletContext.contextPath}/assets/css/info_user.css" type="text/css" rel="stylesheet"/>
	</jsp:attribute>
	<jsp:attribute name="customJs">
		<!--<script src="assets/js/landing_page.js"></script>-->
		<script>

		</script>
	</jsp:attribute>

</layouts:base>
