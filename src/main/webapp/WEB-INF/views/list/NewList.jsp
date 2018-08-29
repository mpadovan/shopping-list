<%-- 
    Document   : NewList
    Created on : 28-ago-2018, 21.56.13
    Author     : giulia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<layouts:base pageTitle="New list">
    <jsp:attribute name="pageContent">
		<div class="container-fluid">	
			<div class="card new-list-card">
				<div class="card-body">
					<div class="text-center mb-4">
						<h1 class="h3 mb-3 font-weight-normal">Nuova lista</h1>
					</div>
					<form class="form-list" method="POST" action="NewList" enctype='multipart/form-data'>
						<div>
							<label for="nameList">Nome lista</label>
							<input type="text"
								   class="form-control" 
								   id="nameList"
								   name="nameList" 
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
								<c:forEach var="c" items="${requestScope.listsCategory}">
									<option value="${c.id}">${c.name}</option>
								</c:forEach>
							</select>
						</div>
						<div id="sharedList">
							<label for="shared">Condividi con: </label>
							<input type="email"
								   class="form-control" 
								   name="shared" 
								   />
						</div>
						<button type="button" class="btn btn-light" onclick="aggiungiEmail()">Condividi con un'altra email</button>
						<div>
							<label for="description">Descrizione</label>
							<input type="text"
								   class="form-control"
								   id="description"
								   name="description"
								   required>
						</div>
						<div>
							<label for="image">Immagine</label>
							<div class="custom-file">
								<input type="file"
									   class="custom-file-input form-control"
									   id="image"
									   name="image"
									   aria-describedby="image">
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
			function aggiungiEmail() {
				var btn = document.createElement("INPUT");
				btn.setAttribute("type", "email");
				btn.setAttribute("name", "shared");
				btn.classList.add("form-control");
				document.getElementById("sharedList").appendChild(btn);
			}
		</script>
	</jsp:attribute>

</layouts:base>
