<%-- 
    Document   : InfoList
    Created on : 16-lug-2018, 15.08.46
    Author     : giuliapeserico
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:base pageTitle="Informazioni lista">
    <jsp:attribute name="pageContent">
		<div class="card info-list-card mx-auto">
			<div id="carouselExampleControls" class="carousel slide" data-ride="carousel" style="">
				<div class="carousel-inner img-cat-list">
					<c:forEach var="i" varStatus="j" items="${requestScope.categoryImages}">
						<div style="background-color: #e0e0e0" class="carousel-item <c:if test="${j.index == 0}">active</c:if>">
							<img class="d-block img-cat-list" src="${pageContext.servletContext.contextPath}${i.image}" alt="Fotografia di categoria di Lista">
						</div>
					</c:forEach>
				</div>
				<a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
					<span class="carousel-control-prev-icon" aria-hidden="true"></span>
					<span class="sr-only">Previous</span>
				</a>
				<a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
					<span class="carousel-control-next-icon" aria-hidden="true"></span>
					<span class="sr-only">Next</span>
				</a>
			</div>
			<div class="card-body">
				<div class="row">
					<div class="col-sm-12 col-md-auto text-center">
						<c:if test="${!empty requestScope.currentList.image}">
							<img class="card-img-top" src="${pageContext.servletContext.contextPath}${requestScope.currentList.image}" alt="image" title="${requestScope.currentList.name}">
						</c:if>
						<c:if test="${empty requestScope.currentList.image}">
							<img class="card-img-top" src="${pageContext.servletContext.contextPath}/assets/images/list-default.png" alt="image" title="${requestScope.currentList.name}">
						</c:if>
					</div>
					<div class="col">
						<h5 class="card-title text-center">Informazioni lista "${requestScope.currentList.name}"</h5>
						<div class="table-responsive-md">
							<table class="table">
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
										<td>${requestScope.currentList.owner.name} ${requestScope.currentList.owner.lastname}</td>
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
						</div>
					</div>
				</div>

										<a href="${pageContext.servletContext.contextPath}/restricted/HomePageLogin/${sessionScope.user.hash}/${requestScope.currentList.hash}" class="btn btn-light" title="Torna alla lista"><i class="fas fa-chevron-left"></i> Indietro</a>

				<c:if test="${requestScope.hasDeletePermission}">
					<a href="#" class="btn btn-danger float-right" data-toggle="modal" data-target="#deleteList" title="Elimina"><i class="fas fa-trash"></i></a>
					</c:if>
					<c:if test="${requestScope.hasModifyPermission}">
					<a href="${pageContext.servletContext.contextPath}/restricted/EditList/${sessionScope.user.hash}/${requestScope.currentList.hash}" class="btn btn-light float-right mx-2" title="Modifica"><i class="fas fa-pen-square"></i></a>
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
						Sei sicuro di volere eliminare la lista ${requestScope.currentList.name}?
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-light" data-dismiss="modal">Annulla</button>
						<a href="${pageContext.servletContext.contextPath}/restricted/DeleteList/${sessionScope.user.hash}/${requestScope.currentList.hash}" class="btn btn-danger">Conferma</a>
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
