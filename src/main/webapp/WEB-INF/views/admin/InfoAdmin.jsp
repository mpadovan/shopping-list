<%-- 
    Document   : InfoAdmin
    Created on : 21-ago-2018, 19.38.22
    Author     : giulia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:admin pageTitle="Profile">
    <jsp:attribute name="pageContent">
		<div class="card card-info">
			<div class="card-body">
				<div class="row">
					<div class="col-3">
						<img style="max-width: 100px; max-height: 100px;" src="${pageContext.servletContext.contextPath}/assets/images/avatar.png" alt="Nome Cognome" title="Immagine profilo">
					</div>
					<div class="col">
						<h4 class="card-title text-center">Informazioni utente ${sessionScope.user.name} ${sessionScope.user.lastname}</h4>
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
						<a href="${pageContext.servletContext.contextPath}/restricted/admin/ProductList" class="btn btn-light"><i class="fas fa-chevron-left"></i> Indietro</a>
						<a href="${pageContext.servletContext.contextPath}/restricted/admin/EditAdmin" class="btn btn-light float-right mx-2"><i class="fas fa-pen-square"></i> Modifica</a>
					</div>
				</div>
			</div>
		</div>


	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link href="${pageContext.servletContext.contextPath}/assets/css/listForm.css" type="text/css" rel="stylesheet"/>
	</jsp:attribute>
	<jsp:attribute name="customJs">

	</jsp:attribute>

</layouts:admin>

