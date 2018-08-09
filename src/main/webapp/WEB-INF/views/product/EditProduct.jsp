<%-- 
    Document   : EditProduct
    Created on : 8-ago-2018, 12.43.06
    Author     : giulia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:base pageTitle="Edit product">
    <jsp:attribute name="pageContent">
		<div class="container-fluid">	
			<div class="card new-list-card">
				<div class="card-body">
					<h4 class="text-center">Modifica prodotto</h4>
					<form class="form-signin" method="POST" action="login">
						<div class="form-label-group mb-2">
							<label for="name">Nome prodotto</label>
							<input type="text" class="form-control" id="name" name="name">
						</div>
						<div class="form-label-group my-2">
							<label for="note">Note</label>
							<input type="text" class="form-control" id="note" name="note">
						</div>
						<br>
						<div class="custom-file my-2">
							<input type="file" class="custom-file-input" id="image" name="image">
							<label class="custom-file-label" for="image">Scegli immagine</label>
						</div>
						<br>
						<div class="my-2">
							<label for="class">Categoria</label>
							<select class="select2 js-example-basic-single form-control py-3" name="class">
								<option value="La">Latticini</option>
								<option value="Ve">Verdura</option>
								<option value="Pa">Panetteria</option>
							</select>
						</div>
						<br>
						<div>
							<a href="HomePageLogin.jsp" class="btn btn-light">Conferma</a>
							<a href="HomePageLogin.jsp" class="btn btn-light">Annulla</a>
						</div>
					</form>
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