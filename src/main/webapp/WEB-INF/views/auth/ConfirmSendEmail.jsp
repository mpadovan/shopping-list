<%-- 
    Document   : ConfirmSendEmail
    Created on : 18-ago-2018, 11.40.28
    Author     : simon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>


<layouts:auth pageTitle="ResetPassword">
	<jsp:attribute name="pageContent">
		<div class="container-fluid px-2">
			<div class="card login-card">
				<div class="card-title text-center">
					<h3>Reset Password</h3>
				</div>
				<div class="card-body">
					<h4>è stata inviata un'email al tuo indirizzo per resettare la tua password, apri il link e procedi se vuoi continuare</h4>
				</div>
			</div>
		</div>
	</jsp:attribute>
</layouts:auth>
