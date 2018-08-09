<%--
    Document   : NewSharedList
    Created on : 16-lug-2018, 14.30.53
    Author     : giuliapeserico
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:base pageTitle="Add shered list">
    <jsp:attribute name="pageContent">
		<div class="container-fluid">	
			<div class="card new-list-card">
				<div class="card-body">
					<h4 class="text-center">Nuova lista condivisa</h4>
					<form class="form-signin" method="POST" action="login">
						<div class="form-label-group mb-2">
							<label for="name">Nome lista</label>
							<input type="text" class="form-control" id="nameList" name="nameList">
						</div>
						<div class="form-label-group my-2">
							<label for="description">Descrizione</label>
							<input type="text" class="form-control" id="description" name="name">
						</div>
						<br>
						<div class="custom-file my-2">
							<input type="file" class="custom-file-input" id="image" name="image">
							<label class="custom-file-label" for="image">Scegli immagine</label>
						</div>
						<br>
						<div class="my-2">
							<label for="class">Categoria</label>
							<!-- name="state"? -->
							<select class="select2 js-example-basic-single form-control py-3" name="state">
								<option value="Fa">Farmacia</option>
								<option value="Su">Supermercato</option>
								<option value="Fe">Ferramenta</option>
							</select>
						</div>
						<div>
							<label for="shared">Condividi con: </label>
							<select class="select2 js-example-basic-multiple form-control" name="states[]" multiple="multiple">
								<option>giuliacarocari@gmail.com</option>
								<option>simonelever@gmail.com</option>
								<option>matteopadovan@gmail.com</option>
								<option>giu.peserico@gmail.com</option>
								<option>micheletessari@gmail.com</option>
							</select>
						</div>
						<br>
						<div>
							<a href="HomePageLogin.jsp" class="btn btn-light">Aggiungi</a>
							<a href="HomePageLogin.jsp" class="btn btn-light">Annulla</a>
						</div>
					</form>
				</div>
			</div>
		</div>


	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link href="assets/css/select2-bootstrap4.css" type="text/css" rel="stylesheet"/>
		<link href="assets/css/listForm.css" type="text/css" rel="stylesheet"/>
	</jsp:attribute>
	<jsp:attribute name="customJs">
		<!--<script src="assets/js/landing_page.js"></script>-->
		<script>
			$(document).ready(function () {
				$('.js-example-basic-single').select2({
//					theme: 'bootstrap4'
				});
				$('.js-example-basic-multiple').select2({
//					theme: 'bootstrap4'
				});
			});

		</script>
	</jsp:attribute>

</layouts:base>


