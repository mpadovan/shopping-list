<%-- 
    Document   : InfoList
    Created on : 16-lug-2018, 15.08.46
    Author     : giuliapeserico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:base pageTitle="Info list">
    <jsp:attribute name="pageContent">
		<div class="card info-list-card">
			<img class="card-img-top" src="assets/images/eurospar.jpg" alt="image" title="Supermercato">
			<div class="card-body">
				<h5 class="card-title text-center">Informazioni lista "Supermercato"</h5>
				<table class="table table-hover">
					<tbody>
						<tr>
							<th scope="row">Nome</th>
							<td>Supermercato</td>
						</tr>
						<tr>
							<th scope="row">Descrizione</th>
							<td>Spesa appartamento</td>
						</tr>
						<tr>
							<th scope="row">Proprietario</th>
							<td>Simone Lever</td>
						</tr>
						<tr>
							<th scope="row">Condivisa con</th>
							<td>
								giulia@gmail.com
							</td>

						</tr>
					</tbody>
				</table>

				<a href="HomePageLogin.jsp" class="btn btn-light"><i class="fas fa-chevron-left"></i> Indietro</a>
				<a href="#" class="btn btn-light float-right" data-toggle="modal" data-target="#deleteList" title="Elimina"><i class="fas fa-trash"></i></a>
				<a href="EditList.jsp" class="btn btn-light float-right mx-2" title="Modifica"><i class="fas fa-pen-square"></i></a>
			</div>
		</div>
		<!--Modal-->
		<div class="modal fade" id="deleteList" tabindex="-1" role="dialog" aria-labelledby="deleteList" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">Elimina lista</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						Sei sicuro di volere eliminare la lista "NomeLista"?
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-light" data-dismiss="modal">Annulla</button>
						<a href="NoListPage.jsp" class="btn btn-light">Conferma</a>
					</div>
				</div>
			</div>
		</div>


	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link href="assets/css/listForm.css" type="text/css" rel="stylesheet"/>
		<link href="assets/css/info_list.css" type="text/css" rel="stylesheet"/>
	</jsp:attribute>
	<jsp:attribute name="customJs">
		<!--<script src="assets/js/landing_page.js"></script>-->
		<script>

		</script>
	</jsp:attribute>

</layouts:base>
