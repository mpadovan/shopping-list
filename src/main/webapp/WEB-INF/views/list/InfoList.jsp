<%-- 
    Document   : InfoList
    Created on : 16-lug-2018, 15.08.46
    Author     : giuliapeserico
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:base pageTitle="Info list">
    <jsp:attribute name="pageContent">
		<div class="card info-list-card">
			<c:if test="!${empty sessionScope.user.image}">
				<img class="card-img-top" src="${requestScope.currentList.image}" alt="image" title="${requestScope.currentList.name}">
			</c:if>
			<c:if test="${empty sessionScope.user.image}">
				<img class="card-img-top" src="${pageContext.servletContext.contextPath}/assets/images/list-default.png" alt="image" title="${requestScope.currentList.name}">
			</c:if>
			<img class="card-img-top" src="${requestScope.currentList.image}" alt="image" title="${requestScope.currentList.name}">
			<div class="card-body">
				<h5 class="card-title text-center">Informazioni lista "${requestScope.currentList.name}"</h5>
				<table class="table table-responsive-md">
					<tbody>
						<tr>
							<th scope="row">Nome</th>
							<td>${requestScope.currentList.name}</td>
						</tr>
						<tr>
							<th scope="row">Descrizione</th>
							<td>${requestScope.currentList.description}</td>
						</tr>
						<tr>
							<th scope="row">Proprietario</th>
							<td>${requestScope.currentList.owner.email}</td>
						</tr>
						<c:if test="${!empty requestScope.sharedUsers}">
							<tr>
								<th scope="column">Condivisa con</th>

								<td>
									<c:forEach items="${requestScope.sharedUsers}" var="user">
										${user.email}
										<br>
									</c:forEach>
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>

				<a href="${pageContext.servletContext.contextPath}/restricted/HomePageLogin/${sessionScope.user.id}" class="btn btn-light"><i class="fas fa-chevron-left"></i> Indietro</a>

				<c:if test="${requestScope.hasDeletePermission}">
					<a href="#" class="btn btn-danger float-right" data-toggle="modal" data-target="#deleteList" title="Elimina"><i class="fas fa-trash"></i></a>
				</c:if>
				<c:if test="${requestScope.hasModifyPermission}">
					<a href="${pageContext.servletContext.contextPath}/restricted/permission/EditList" class="btn btn-light float-right mx-2" title="Modifica"><i class="fas fa-pen-square"></i></a>
				</c:if>
			</div>
		</div>
		<!--Modal-->
		<div class="modal fade" id="deleteList" tabindex="-1" role="dialog" aria-labelledby="deleteList" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">Elimina lista</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						Sei sicuro di volere eliminare la lista "NomeLista"?
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-light" data-dismiss="modal">Annulla</button>
						<a href="#" class="btn btn-danger">Conferma</a>
					</div>
				</div>
			</div>
		</div>


	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link href="${pageContext.servletContext.contextPath}/assets/css/info_list.css" type="text/css" rel="stylesheet"/>
	</jsp:attribute>
	<jsp:attribute name="customJs">
		<!--<script src="assets/js/landing_page.js"></script>-->
		<script>

		</script>
	</jsp:attribute>

</layouts:base>
