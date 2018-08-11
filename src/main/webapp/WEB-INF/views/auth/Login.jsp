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
				<div class="card-title">
					<h3 class="text-center">Login</h3>
				</div>
				<div class="card-body">
					<form action="Login" method="POST">
						<div class="form-group">
							<label for="email">Email</label>
							<input type="email" class="form-control" id="email" name="email" aria-describedby="emailHelp" placeholder="Enter email" required="required">

						</div>
						<div class="form-group">
							<label for="password">Password</label>
							<input type="password" class="form-control" id="password" name="password" placeholder="Password" required="required">
						</div>
						<div class="form-check">
							<input type="checkbox" class="form-check-input" id="remember" name="remember">
							<label class="form-check-label" for="remember">Ricordami</label>
						</div>
						<div>
							<a href="#">Password dimenticata?</a><br>
						</div>
						<div class="float-right">
							<a href="HomePageLogin.jsp" class="btn btn-outline-dark">Annulla</a>
						</div>
						<button type="submit" class="btn btn-outline-dark">Accedi</button>
						<!-- <a href="HomePageLogin.jsp" class="btn btn-outline-dark">Accedi</a> -->
					</form>
				</div>
			</div>
		</div>
	</jsp:attribute>
</layouts:auth>



