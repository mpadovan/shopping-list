<%-- 
    Document   : EditAdmin
    Created on : 21-ago-2018, 19.53.27
    Author     : giulia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared/" %>

<layouts:base pageTitle="Profile">
    <jsp:attribute name="pageContent">
		<div class="cointainer-fluid px-2">
			<div class="card edit-user-card">
				<div class="card-body">
					<c:if test="${!empty user.errors}">
						<div class="alert alert-danger" role="alert">
							<h4 class="alert-heading">I dati inseriti non sono validi.</h4>
							<p>Controlla i campi sottostanti.</p>
						</div>
					</c:if>
					<div class="text-center mb-4">
						<h3 class="mb-3 font-weight-normal">Modifica profilo</h3>
					</div>
					<form class="form-user" action="EditUser" method="POST">
						<div class="form-group">
							<label for="name">Nome</label>
							<input type="text"  id="name" name="name" class="form-control ${(user.getFieldErrors("name") != null ? "is-invalid" : "")}"required>
							<div class="invalid-feedback">
								<shared:fieldErrors entity="${user}" field="name" />
							</div>
						</div>
						<div class="form-group">
							<label for="lastName">Cognome</label>
							<input type="text"  id="lastName" name="lastName" class="form-control ${(user.getFieldErrors("lastname") != null ? "is-invalid" : "")}"	 required>
							<div class="invalid-feedback">
								<shared:fieldErrors entity="${user}" field="lastname" />
							</div>
						</div>
						<div class="form-group">
							<label for="email">Email</label>
							<input type="email" readonly class="form-control" id="email" name="email"value="${sessionScope.user.email}" required>
						</div>
						<div id="blockPassword">
							<div class="form-group" style="display: none" id="divPassword">
								<label for="password">Password attuale</label>
								<input type="password" id="password" name="password" class="form-control" required>
								
							</div>
							<div class="form-group" style="display: none" id="divNewPassword">
								<label for="newPassword">Nuova password</label>
								<input type="password" id="newPassword" name="newPassword" class="form-control" required>
							</div>
							<div class="form-group" style="display: none" id="divCheckPassword">
								<label for="checkPassword">Nuova password</label>
								<input type="password" id="checkPassword" name="checkPassword" class="form-control" required>
							</div>
						</div>
						<button type="submit" class="btn btn-new float-right mx-2">Conferma</button>
						<button type="button" onclick="show()" class="btn btn-light	 float-right mx-2" id="btnLock">Modifica password <i class="fas fa-lock"></i></button>
						<button type="button" onclick="hide()" class="btn btn-light	 float-right mx-2" id="btnUnLock" style="display: none;"> <i class="fas fa-unlock"></i></button>
						<a href="${pageContext.servletContext.contextPath}/restricted/InfoUser" class="btn btn-light"><i class="fas fa-chevron-left"></i> Indietro</a>
					</form>
				</div>
			</div>
		</div>
	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link href="${pageContext.servletContext.contextPath}/assets/css/info_user.css" type="text/css" rel="stylesheet"/>
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

</layouts:base>
