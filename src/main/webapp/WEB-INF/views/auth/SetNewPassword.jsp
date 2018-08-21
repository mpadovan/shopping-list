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
				<div class="card-title">
					<h3 class="text-center">Change Password</h3>
				</div>
				<div class="card-body">
					<form action="SetNewPassword" method="POST">
						<input type="hidden" name="id" value="${param.id}">
						<input type="hidden" name="token" value="${param.token}">
						<div class="form-group">
							<label for="password">Password</label>
							<input type="password" class="form-control" id="password" name="password" placeholder="Password" required="required">
						</div>
						<div class="form-group">
							<label for="checkPassword">Confirm password</label>
							<input type="password" class="form-control" id="checkPassword" name="checkPassword" placeholder="Password" required="required">
						</div>
						<button type="submit" class="btn btn-outline-dark">Prosegui</button>
					</form>
				</div>
			</div>
		</div>
	</jsp:attribute>
</layouts:auth>