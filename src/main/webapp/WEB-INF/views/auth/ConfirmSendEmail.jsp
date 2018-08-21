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
				<div class="card-body">
					<div class="text-center mb-4">
							<h1 class="h3 mb-3 font-weight-normal">Reset Password</h1>
						</div>
					<div class="alert alert-success" role="alert">
						<div>è stata inviata un'email al tuo indirizzo per resettare la tua password, apri il link e procedi se vuoi continuare</div>
					</div>
				</div>
			</div>
		</div>
	</jsp:attribute>
</layouts:auth>