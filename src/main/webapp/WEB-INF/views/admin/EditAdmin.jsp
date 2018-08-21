<%-- 
    Document   : EditAdmin
    Created on : 21-ago-2018, 19.53.27
    Author     : giulia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:admin pageTitle="Profile">
    <jsp:attribute name="pageContent">
		<div class="card card-edit">
			<div class="card-body">
				<div class="row">
					<div class="col-3">
						<img style="max-width: 100px; max-height: 100px;" src="${pageContext.servletContext.contextPath}/assets/images/avatar.png" alt="Nome Cognome" title="Immagine profilo">
					</div>
					<div class="col">
						<h4 class="card-title text-center">Modifica utente ${sessionScope.user.name} ${sessionScope.user.lastname}</h4>
						<form action="EditAdmin">
							<div class="form-group row">
								<label for="name" class="col-sm-3 col-form-label">Nome</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="name" name="name" value="${sessionScope.user.name}">
								</div>
							</div>
							<div class="form-group row">
								<label for="lastName" class="col-sm-3 col-form-label">Cognome</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="lastName" name="lastName" value="${sessionScope.user.lastname}" required>
								</div>
							</div>
							<div class="form-group row">
								<label for="email" class="col-sm-3 col-form-label">Email</label>
								<div class="col-sm-9">
									<input type="email" class="form-control" id="email" name="email"value="${sessionScope.user.email}" required>
								</div>
							</div>
							<div class="form-group row">
								<label for="password" class="col-sm-3 col-form-label">Password attuale</label>
								<div class="col-sm-9">
									<input type="password" class="form-control" id="password" name="password" required>
								</div>
							</div>
							<div class="form-group row">
								<label for="password" class="col-sm-3 col-form-label">Nuova password </label>
								<div class="col-sm-9">
									<input type="password" class="form-control" id="password" name="password" required>
								</div>
							</div>
							<div class="form-group row">
								<label for="checkPassword" class="col-sm-3 col-form-label">Conferma nuova password</label>
								<div class="col-sm-9">
									<input type="password" class="form-control" id="checkPassword" name="checkPassword" required>
								</div>
							</div>
						</form>
						<a href="${pageContext.servletContext.contextPath}/restricted/admin/InfoAdmin" class="btn btn-light"><i class="fas fa-chevron-left"></i> Indietro</a>
						<button type="submit" class="btn btn-primary float-right mx-2">Conferma</button>
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
