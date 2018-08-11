<%--
    Document   : SignUp
    Created on : 10-lug-2018, 10.34.38
    Author     : giuliapeserico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>
<layouts:auth pageTitle="Sign up">
	<jsp:attribute name="pageContent">
		<div class="cointainer-fluid px-2">
			<div class="card signUp-card">
				<div class="card-title">
					<h3 class="text-center">Registrazione</h3>
				</div>

				<div class="card-body">
					<form action="SignUp" method="POST">
						<div class="form-row">
							<div class="form-group col-md-6">
								<label for="name">Nome</label>
								<input type="text" class="form-control" placeholder="Nome" name="name" required="required">
							</div>
							<div class="form-group col-md-6">
								<label for="name">Cognome</label>
								<input type="text" class="form-control" placeholder="Cognome" name="lastName" required="required">
							</div>
						</div>
						<div class="custom-file my-2">
							<label class="custom-file-label" for="image">Scegli avatar</label>
							<input type="file" class="custom-file-input" id="image" name="image">
						</div>
						<div class="form-group">
							<label for="email">Email</label>
							<input type="email" class="form-control" id="email" name="email" aria-describedby="emailHelp" placeholder="Enter email" required="required">
						</div>
						<div class="form-group">
							<label for="password">Password</label>
							<input type="password" class="form-control" id="password" name="password" placeholder="Password" required="required">
						</div>
						<div class="form-group">
							<label for="checkPassword">Password</label>
							<input type="password" class="form-control" id="checkPassword" name="checkPassword" placeholder="Password" required="required">
						</div>
						<div class="form-group">
							<div class="form-check">
								<input type="checkbox" class="form-check-input" id="privacy" name="privacy" required="required">
								<label class="form-check-label" for="privacy">Normativa privacy</label>
							</div>
						</div>
						<button type="submit" class="btn btn-outline-dark">Registrati</button>
						<div class="float-right">
							<!-- TODO change href -->
							<a href="" class="btn btn-outline-dark">Annulla</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</jsp:attribute>
</layouts:auth>
