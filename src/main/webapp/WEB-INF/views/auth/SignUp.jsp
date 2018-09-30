<%--
    Document   : SignUp
    Created on : 10-lug-2018, 10.34.38
    Author     : giuliapeserico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared/" %>
<layouts:auth pageTitle="Sign up">
	<jsp:attribute name="pageContent">
		<div class="cointainer-fluid px-2">
			<div class="card signUp-card">
				<div class="card-body">
					<c:if test="${!empty user.errors}">
						<div class="alert alert-danger" role="alert">
							<h4 class="alert-heading">I dati inseriti non sono validi.</h4>
							<p>Controlla i campi sottostanti.</p>
						</div>
					</c:if>
					<div class="text-center mb-4">
						<h1 class="h3 mb-3 font-weight-normal">Registrazione</h1>
					</div>
					<form class="form-signin" action="SignUp" method="POST" enctype='multipart/form-data'>
						<div class="form-label-group">
							<input type="text"  id="name" name="name" class="form-control ${(user.getFieldErrors("name") != null ? "is-invalid" : "")}" placeholder="Nome" required>
							<label for="name">Nome</label>
							<div class="invalid-feedback">
								<shared:fieldErrors entity="${user}" field="name" />
							</div>
						</div>
						<div class="form-label-group">
							<input type="text"  id="lastName" name="lastName" class="form-control ${(user.getFieldErrors("lastname") != null ? "is-invalid" : "")}" placeholder="Cognome" required>
							<label for="lastName">Cognome</label>
							<div class="invalid-feedback">
								<shared:fieldErrors entity="${user}" field="lastname" />
							</div>
						</div>
						<div class="form-label-group mt-3">
							<input type="email"  id="email" name="email" aria-describedby="emailHelp" class="form-control ${(user.getFieldErrors("email") != null ? "is-invalid" : "")}" placeholder="Email" required>
							<label for="email">Email</label>
							<div class="invalid-feedback">
								<shared:fieldErrors entity="${user}" field="email" />
							</div>
						</div>
						<div class="form-label-group">
							<input type="password" id="password" name="password" class="form-control ${(user.getFieldErrors("password") != null ? "is-invalid" : "")}" placeholder="Password" required>
							<label for="password">Password</label>
							<div class="invalid-feedback">
								<shared:fieldErrors entity="${user}" field="password" />
							</div>
						</div>
						<div class="form-label-group">
							<input type="password" id="checkPassword" name="checkPassword" class="form-control ${(user.getFieldErrors("checkpassword") != null ? "is-invalid" : "")}" placeholder="Conferma password" required>
							<label for="checkPassword">Conferma password</label>
							<div class="invalid-feedback">
								<shared:fieldErrors entity="${user}" field="checkpassword" />
							</div>
						</div>
						<div class="form-group">
							<div class="form-check">
								<input class="form-check-input ${ requestScope.privacy != null ? "is-invalid" : ""}"
									   type="checkbox" value="privacy" name="privacy" id="privacy" required >
								<label class="form-check-label" for="privacy">
									Normativa privacy
								</label>
								<div class="invalid-feedback">
									${requestScope.privacy}
								</div>
							</div>
						</div>
						<button type="submit" class="btn btn-lg btn-block form-signin-btn">Registrati</button>
						<div class="mt-3">
							<a href="${pageContext.servletContext.contextPath}/home">Indietro</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</jsp:attribute>
	<jsp:attribute name="customJs">
		<script>
			$(document).ready(function () {
				setTimeout(function () {
					var $Input = $('input:-webkit-autofill');
					$Input.next("label").addClass('active');
				}, 100);
			});

		</script>
	</jsp:attribute>
</layouts:auth>
