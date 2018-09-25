<%-- 
    Document   : ResetPassword
    Created on : 17-ago-2018, 21.43.22
    Author     : simon
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<layouts:auth pageTitle="ResetPassword">
	<jsp:attribute name="pageContent">
		<div class="container-fluid px-2">
			<div class="card login-card">
				<div class="card-body">
					<div class="text-center mb-4">
							<h1 class="h3 mb-3 font-weight-normal">Reset Password</h1>
							<div>Inserisci l'indirizzo e-mail associato al tuo account per ripristinare la password</div>
						</div>
					<form class="form-signin" action="ResetPassword" method="POST">
						<div class="form-label-group">
							<input type="email"  
								   id="email"
								   name="email"
								   aria-describedby="emailHelp"
								   class="form-control ${requestScope.emailnotfound ? "is-invalid" : ""}"
								   placeholder="Email" 
								   required="required">
							<label for="email">Email</label>
							<div class="invalid-feedback">
								Non esiste nessun account associato a questa email
							</div>
							<button type="submit" class="btn btn-lg btn-block form-signin-btn mt-3">Continua</button>
							<div class="mt-3">
								<a href="${pageContext.servletContext.contextPath}/Login">Indietro</a>
							</div>
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