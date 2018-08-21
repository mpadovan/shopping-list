<%-- 
    Document   : ConfirmChangePassword
    Created on : 21-ago-2018, 19.02.37
    Author     : simon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>


<layouts:auth pageTitle="ResetPassword">
	<jsp:attribute name="pageContent">
		<div class="container-fluid px-2">
			<div class="card login-card">
				<div class="card-title text-center">
					<h3>Confirm changed password</h3>
				</div>
				<div class="card-body">
					<div class="alert alert-success" role="alert">
						<h5>Hai modificato correttamente la tua password. Torna al <a href="${pageContext.servletContext.contextPath}/Login" class="badge badge-light">Login</a></h5>
					</div>
				</div>
			</div>
		</div>
	</jsp:attribute>
</layouts:auth>
