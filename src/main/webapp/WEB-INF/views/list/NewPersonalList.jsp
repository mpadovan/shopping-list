<%-- 
    Document   : NewPersonalList
    Created on : 11-lug-2018, 14.31.38
    Author     : giuliapeserico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:base pageTitle="Add personal list">
    <jsp:attribute name="pageContent">
		<div class="container-fluid px-2">
			<div class="card new-list-card">
				<div class="card-body">
					<h4 class="text-center">Nuova lista personale</h4>
					<form class="form-signin" method="POST" action="login">
						<div class="form-label-group mb-2">
							<label for="name">Nome lista</label>
							<input type="text" class="form-control" id="nameList" name="nameList">
						</div>
						<div class="form-label-group my-2">
							<label for="description">Descrizione</label>
							<input type="text" class="form-control" id="description" name="description">
						</div>
						<div class="custom-file my-2">
							<input type="file" class="custom-file-input" id="image" name="image">
							<label class="custom-file-label" for="image">Scegli file</label>
						</div>
						<div class="my-2">
							<label for="class">Categoria</label>
							<!-- name="state" si può cambiare? -->
							<select class="select2 js-example-basic-single form-control" name="state">
								<option value="Fa">Farmacia</option>
								<option value="Su">Supermercato</option>
								<option value="Fe">Ferramenta</option>
							</select>
						</div>
						<br>
						<div>
							<a href="HomePage.jsp" class="btn btn-light">Aggiungi</a>
							<a href="HomePage.jsp" class="btn btn-light">Annulla</a>
						</div>
					</form>
				</div>
			</div>
		</div>


	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link href="${pageContext.servletContext.contextPath}/assets/css/listForm.css" type="text/css" rel="stylesheet"/>
	</jsp:attribute>
	<jsp:attribute name="customJs">
		<!--<script src="assets/js/landing_page.js"></script>-->
		<script>
			$(document).ready(function () {
				$('.js-example-basic-single').select2({
					//theme: 'bootstrap4'
				});
			});
		</script>
	</jsp:attribute>

</layouts:base>

