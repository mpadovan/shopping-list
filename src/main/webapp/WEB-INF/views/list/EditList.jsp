<%-- 
    Document   : EditList
    Created on : 17-lug-2018, 13.35.47
    Author     : Giulia Peserico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared/" %>

<layouts:base pageTitle="Edit list">
    <jsp:attribute name="pageContent">
		<div class="container-fluid">	
			<div class="card new-list-card">
				<div class="card-body">
					<c:if test="${!empty list.errors}">
						<div class="alert alert-danger" role="alert">
							<h4 class="alert-heading">I dati inseriti non sono validi.</h4>
							<p>Controlla i campi sottostanti.</p>
						</div>
					</c:if>
					<div class="text-center mb-4">
						<h3 class="mb-3 font-weight-normal">Modifica lista</h3>
					</div>
					<form class="form-list" method="POST" action="EditList">
						<div>
							<label for="nameList">Nome lista</label>
							<input type="text"
								   class="form-control ${(list.getFieldErrors("name") != null ? "is-invalid" : "")}" 
								   id="nameList"
								   name="nameList" 
								   value="${requestScope.currentList.name}"
								   maxlength="40"
								   required />
							<div class="invalid-feedback">
								<shared:fieldErrors entity="${list}" field="name" />
							</div>
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
							<label for="description">Descrizione</label>
							<input type="text"
								   class="form-control ${(list.getFieldErrors("description") != null ? "is-invalid" : "")}"
								   id="description"
								   name="description"
								   value="${requestScope.currentList.description}"
								   maxlength="256"
								   required>
							<div class="invalid-feedback">
								<shared:fieldErrors entity="${list}" field="description" />
							</div>
						</div>
						<div id="sharedList">Condividi con:<br></div>
						<button type="button" class="btn btn-light" onclick="addEmail()">Aggiungi un'email</button>
						<div>
							<label for="image">Immagine</label>
							<div class="custom-file">
								<input type="file"
									   class="custom-file-input form-control"
									   id="image"
									   name="image"
									   aria-describedby="image"
									   value="${requestScope.currentList.image}">
								<label class="custom-file-label" for="image">Scegli file</label>
							</div>
						</div>
						<div class="float-right mt-3">
							<a href="${pageContext.servletContext.contextPath}/restricted/InfoList/${sessionScope.user.id}/${requestScope.currentList.id}" class="btn btn-light">Annulla</a>
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
			function addEmail() {
				var btn = document.createElement("INPUT");
				btn.setAttribute("type", "email");
				btn.setAttribute("name", "shared[]");
				btn.classList.add("form-control");
				document.getElementById("sharedList").appendChild(btn);
			}
		</script>
	</jsp:attribute>

</layouts:base>
