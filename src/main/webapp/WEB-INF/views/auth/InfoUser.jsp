<%-- 
    Document   : User
    Created on : 17-lug-2018, 20.58.30
    Author     : Giulia Peserico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:base pageTitle="Info user">
    <jsp:attribute name="pageContent">
		<div class="card info-list-card">
			<div class="card-body">
				<div class="row">
					<div class="col-3">
						<a href="#"><img style="max-width: 100px; max-height: 100px;" src="${pageContext.servletContext.contextPath}/assets/images/avatar.png" alt="Nome Cognome" title="Cambia immagine"></a>
					</div>
					<div class="col">
						<h5 class="card-title text-center">Informazioni utente "Michele Tessari"</h5>
						<table class="table table-hover">
							<tbody>
								<tr>
									<th scope="row">Nome</th>
									<td>Michele</td>
								</tr>
								<tr>
									<th scope="row">Cognome</th>
									<td>Tessari</td>
								</tr>
								<tr>
									<th scope="row">Email</th>
									<td>michele@gmail.com</td>
								</tr>
								<tr>
									<th scope="row">Password</th>
									<td>
										****
									</td>
								</tr>
							</tbody>
						</table>
						<a href="HomePageLogin.jsp" class="btn btn-light"><i class="fas fa-chevron-left"></i> Indietro</a>
						<a href="#" class="btn btn-light float-right mx-2" title="Modifica"><i class="fas fa-pen-square"></i> Modifica</a>
					</div>
				</div>
			</div>
		</div>


	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link href="assets/css/listForm.css" type="text/css" rel="stylesheet"/>
	</jsp:attribute>
	<jsp:attribute name="customJs">
		<!--<script src="assets/js/landing_page.js"></script>-->
		<script>

		</script>
	</jsp:attribute>

</layouts:base>
