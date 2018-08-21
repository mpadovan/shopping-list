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
				<div class="card-title text-center">
					<h3>Reset Password</h3>
					<div>Inserisci l'indirizzo e-mail associato al tuo account per ripristinare la password</div>
				</div>
				<div class="card-body">
					<form action="ResetPassword" method="POST">
						<div class="form-group mb-6">
							<label for="email">Email</label>
							<input type="email" class="form-control mb-2" id="email" name="email" aria-describedby="emailHelp" placeholder="Enter email" required="required">
							<c:if test='${requestScope.emailnotfound}'><div style="color: #cc0000">Non esiste nessun account associato a questa email</div></c:if>
						</div>
						<div class="float-right">
							<a href="${pageContext.servletContext.contextPath}/Login" class="btn btn-outline-dark">Annulla</a>
						</div>
						<button type="submit" class="btn btn-outline-dark">Continua</button>
					</form>
				</div>
			</div>
		</div>
	</jsp:attribute>
</layouts:auth>