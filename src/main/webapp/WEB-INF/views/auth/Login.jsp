<%-- 
    Document   : Login
    Created on : 19-giu-2018, 10.52.56
    Author     : giuliapeserico
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:auth pageTitle="Login">
	<jsp:attribute name="pageContent">
		<div class="container-fluid px-2">
			<div class="card login-card">
				<div class="card-body">
					<div class="text-center mb-4">
							<h1 class="h3 mb-3 font-weight-normal">Login</h1>
						</div>
					<form class="form-signin" action="Login" method="POST">
						<div class="form-label-group">
							<input type="email"  id="email" name="email" aria-describedby="emailHelp" class="form-control" placeholder="Email" required="required">
							<label for="email">Email</label>
						</div>

						<div class="form-label-group">
							<input type="password" id="password" name="password" class="form-control" placeholder="Password" required="required">
							<label for="password">Password</label>
						</div>
						<div class="checkbox mb-2">
							<label>
								<input type="checkbox" id="remember" name="remember"> Ricordami
							</label>
						</div>
						<button type="submit" class="btn btn-lg btn-block form-signin-btn">Accedi</button>
						<div class="mt-3">
							<a href="${pageContext.servletContext.contextPath}/home">Indietro</a>
							<a href="${pageContext.servletContext.contextPath}/ResetPassword"class="float-right">Password dimenticata?</a>
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



