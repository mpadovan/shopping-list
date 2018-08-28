<%-- 
    Document   : SetNewPassword
    Created on : 18-ago-2018, 11.51.54
    Author     : simon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:auth pageTitle="Change Password">
	<jsp:attribute name="pageContent">
		<div class="container-fluid px-2">
			<div class="card login-card">
				<div class="card-body">
					<div class="text-center mb-4">
							<h1 class="h3 mb-3 font-weight-normal">Change Password</h1>
						</div>
					<form class="form-signin" action="SetNewPassword" method="POST">
						<input type="hidden" name="id" value="${param.id}">
						<input type="hidden" name="token" value="${param.token}">
						<div class="form-label-group">
							<input type="password"  id="password" name="password" class="form-control" placeholder="Password" required="required">
							<label for="password">Password</label>
						</div>
						<div class="form-label-group">
							<input type="password"  id="checkPassword" name="checkPassword" class="form-control" placeholder="Conferma password" required="required">
							<label for="checkPassword">Conferma password</label>
						</div>
						<button type="submit" class="btn btn-lg btn-block form-signin-btn">Continua</button>
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