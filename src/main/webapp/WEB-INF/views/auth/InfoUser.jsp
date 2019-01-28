<%-- 
    Document   : User
    Created on : 17-lug-2018, 20.58.30
    Author     : Giulia Peserico
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:base pageTitle="Informazione utente">
    <jsp:attribute name="pageContent">
		<div class="card info-user-card mx-auto" id="user">
			<div class="card-body">
				<div class="row">
					<div class="col-sm-12 col-md-auto text-center">
						<div class="user-image">
							<a class="user-link" href="${pageContext.servletContext.contextPath}/restricted/ChangeImageUser">
								<div class="user-hover">
									<div class="user-hover-content">
										<p>Cambia immagine</p>
										<p><i class="fas fa-plus fa-2x"></i></p>
									</div>
								</div>
								<c:if test="${not empty sessionScope.user.image}">
									<img class="card-img-top" src="${pageContext.servletContext.contextPath}${sessionScope.user.image}" alt="${sessionScope.user.name}" title="Cambia immagine">
								</c:if>
								<c:if test="${empty sessionScope.user.image}">
									<img class="card-img-top" src="${pageContext.servletContext.contextPath}/assets/images/avatar2.png">
								</c:if>
							</a>
						</div>
					</div>
					<div class="col">
						<h5 class="card-title text-center">Informazioni utente "${sessionScope.user.name} ${sessionScope.user.lastname}"</h5>
						<div class="table-responsive-md">
							<table class="table">
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
						</div>
					</div>
				</div>
				<a href="${pageContext.servletContext.contextPath}/restricted/HomePageLogin/${sessionScope.user.hash}" class="btn btn-light"><i class="fas fa-chevron-left"></i> Indietro</a>
				<a href="${pageContext.servletContext.contextPath}/restricted/EditUser" class="btn btn-light float-right mx-2" title="Modifica"><i class="fas fa-pen-square"></i> Modifica</a>
			</div>
		</div>


	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link href="${pageContext.servletContext.contextPath}/assets/css/info_user.css" type="text/css" rel="stylesheet"/>
	</jsp:attribute>
	<jsp:attribute name="customJs">

	</jsp:attribute>

</layouts:base>
