<%-- 
    Document   : EditList
    Created on : 17-lug-2018, 13.35.47
    Author     : Giulia Peserico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:base pageTitle="Edit list">
    <jsp:attribute name="pageContent">
		<div class="container-fluid">	
			<div class="card new-list-card">
				<div class="card-body">
					<div class="text-center mb-4">
						<h1 class="h3 mb-3 font-weight-normal">Modifica lista</h1>
					</div>
					<form class="form-list" method="POST" action="EditList">
						<div>
							<label for="nameList">Nome lista</label>
							<input type="text"
								   class="form-control" 
								   id="nameList"
								   name="nameList" 
								   value="${requestScope.list.name}"
								   required />
						</div>
						<div>
							<label for="category">Categoria</label>
							<select class="select2 form-control py-3"
									name="category"
									id="category"
									name="category" 
									required
									>
								<option selected value="-1">Nessuna</option>
								<c:forEach var="c" items="${requestScope.listsCategory}">
									<option value="${c.id}">${c.name}</option>
								</c:forEach>
							</select>
						</div>
						<div>
							<label for="shared">Condividi con: </label>
							<select class="select2 js-example-basic-multiple form-control" name="shared" multiple="multiple">
								<option>giuliacarocari@gmail.com</option>
								<option>simonelever@gmail.com</option>
								<option>matteopadovan@gmail.com</option>
								<option>giu.peserico@gmail.com</option>
								<option>micheletessari@gmail.com</option>
							</select>
						</div>
						<div>
							<label for="description">Descrizione</label>
							<input type="text"
								   class="form-control"
								   id="description"
								   name="description"
								   value="${requestScope.list.description}"
								   required>
						</div>
						<div>
							<label for="image">Immagine</label>
							<div class="custom-file">
								<input type="file"
									   class="custom-file-input form-control"
									   id="image"
									   name="image"
									   aria-describedby="image"
									   value="${product.photography}">
								<label class="custom-file-label" for="image">Scegli file</label>
							</div>
						</div>
						<div class="float-right mt-3">
							<a href="${pageContext.servletContext.contextPath}/restricted/InfoList" class="btn btn-light">Annulla</a>
							<button class="btn btn-new ml-2" type="submit">Modifica</button>
						</div> 
					</form>
				</div>
			</div>
		</div>


	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link href="${pageContext.servletContext.contextPath}/assets/css/select2-bootstrap4.css" type="text/css" rel="stylesheet"/>
		<link href="${pageContext.servletContext.contextPath}/assets/css/listForm.css" type="text/css" rel="stylesheet"/>
	</jsp:attribute>
	<jsp:attribute name="customJs">
		<!--<script src="assets/js/landing_page.js"></script>-->
		<script>
			$(document).ready(function () {
				$(document).ready(function () {
				$('select').each(function () {
					$(this).select2({
						theme: 'bootstrap4'
					});
				});
				$('.js-example-basic-multiple').select2({

				});
			});
			});

		</script>
	</jsp:attribute>

</layouts:base>
