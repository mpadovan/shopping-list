<%-- 
    Document   : EditAdmin
    Created on : 21-ago-2018, 19.53.27
    Author     : giulia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:admin pageTitle="Profile">
    <jsp:attribute name="pageContent">
		<div class="card card-edit">
			<div class="card-body">
				<div class="row">
					<div class="col-3">
						<c:if test="${not empty sessionScope.user.image}">
							<img style="max-width: 100px; max-height: 100px;" src="${pageContext.servletContext.contextPath}${user.image}" alt="${sessionScope.user.name} ${sessionScope.user.lastname}" title="Immagine profilo">
						</c:if>
						<c:if test="${empty sessionScope.user.image}">
							<img style="max-width: 100px; max-height: 100px;" src="${pageContext.servletContext.contextPath}/assets/image/avatar2.png" alt="${sessionScope.user.name} ${sessionScope.user.lastname}" title="Immagine profilo">
						</c:if>
					</div>
					<div class="col">
						<h4 class="card-title text-center">Modifica utente ${sessionScope.user.name} ${sessionScope.user.lastname}</h4>
						<form action="EditAdmin" method="POST">
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
									<input type="email" readonly class="form-control" id="email" name="email"value="${sessionScope.user.email}" required>
								</div>
							</div>
							<div class="form-group row" style="display: none" id="divPassword">
								<label for="password" class="col-sm-3 col-form-label">Password attuale</label>
								<div class="col-sm-9">
									<input type="password" class="form-control" id="password" name="password">
								</div>
							</div>
							<div class="form-group row" style="display: none" id="divNewPassword">
								<label for="newPassword" class="col-sm-3 col-form-label">Nuova password </label>
								<div class="col-sm-9">
									<input type="password" class="form-control" id="newPassword" name="newPassword">
								</div>
							</div>
							<div class="form-group row" style="display: none" id="divCheckPassword">
								<label for="checkPassword" class="col-sm-3 col-form-label">Conferma nuova password</label>
								<div class="col-sm-9">
									<input type="password" class="form-control" id="checkPassword" name="checkPassword">
								</div>
							</div>
							<button type="submit" class="btn btn-primary float-right mx-2">Conferma</button>
							<button type="button" onclick="show()" class="btn btn-light	 float-right mx-2" id="btnLock">Modifica password <i class="fas fa-lock"></i></button>
							<button type="button" onclick="hide()" class="btn btn-light	 float-right mx-2" id="btnUnLock" style="display: none;"> <i class="fas fa-unlock"></i></button>
						</form>
						<a href="${pageContext.servletContext.contextPath}/restricted/admin/InfoAdmin" class="btn btn-light"><i class="fas fa-chevron-left"></i> Indietro</a>
					</div>
				</div>
			</div>
		</div>
	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link href="${pageContext.servletContext.contextPath}/assets/css/listForm.css" type="text/css" rel="stylesheet"/>
	</jsp:attribute>
	<jsp:attribute name="customJs">
		<script>
			function show() {
				document.getElementById("divPassword").style.display = "";
				document.getElementById("divNewPassword").style.display = "";
				document.getElementById("divCheckPassword").style.display = "";
				document.getElementById("btnLock").style.display = "none";
				document.getElementById("btnUnLock").style.display = "";
			}
			function hide() {
				document.getElementById("divPassword").style.display = "none";
				document.getElementById("divNewPassword").style.display = "none";
				document.getElementById("divCheckPassword").style.display = "none";
				document.getElementById("btnLock").style.display = "";
				document.getElementById("btnUnLock").style.display = "none";
			}
		</script>
	</jsp:attribute>

</layouts:admin>
