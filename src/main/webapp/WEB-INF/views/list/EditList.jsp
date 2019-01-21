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
					<c:if test="${!empty currentList.errors}">
						<div class="alert alert-danger" role="alert">
							<h4 class="alert-heading">I dati inseriti non sono validi.</h4>
							<p>Controlla i campi sottostanti.</p>
						</div>
					</c:if>
					<div class="text-center mb-4">
						<h3 class="mb-3 font-weight-normal">Modifica lista</h3>
					</div>
					<form class="form-list" method="POST" action="" enctype='multipart/form-data'>
						<div>
							<label for="nameList">Nome lista</label>
							<input type="text"
								   class="form-control ${(currentList.getFieldErrors("name") != null ? "is-invalid" : "")}" 
								   id="nameList"
								   name="nameList" 
								   value="${requestScope.currentList.name}"
								   maxlength="40"
								   required />
							<div class="invalid-feedback">
								<shared:fieldErrors entity="${currentList}" field="name" />
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
								<option selected value="${requestScope.currentList.category.id}">${requestScope.currentList.category.name}</option>
								<c:forEach var="c" items="${requestScope.listsCategory}">
									<c:if test="${requestScope.currentList.category.id != c.id}">
										<option value="${c.id}">${c.name}</option>
									</c:if>
								</c:forEach>
							</select>
						</div>
						<div>
							<label for="description">Descrizione</label>
							<input type="text"
								   class="form-control ${(currentList.getFieldErrors("description") != null ? "is-invalid" : "")}"
								   id="description"
								   name="description"
								   value="${requestScope.currentList.description}"
								   maxlength="256">
							<div class="invalid-feedback">
								<shared:fieldErrors entity="${currentList}" field="description" />
							</div>
						</div>
						<div id="app">
							<div id="sharedList">
								<label>Condividi con:</label>
								<div v-for="(field, index) in emailFields">
									<input type="email"
										   name="shared[]"
										   class="form-control ${(list.getFieldErrors("shared[]") != null ? "is-invalid" : "")}"
										   v-model="emailFields[index]">
									<label class="radio-inline" style="margin-right:5px;">
										<input style="margin-right: 3px;" type="radio" v-bind:name="'permission-' + emailFields[index]" value="view" checked>Visualizzazione
									</label>
									<label class="radio-inline" style="margin-right:5px;">
										<input style="margin-right: 3px;" type="radio" v-bind:name="'permission-' + emailFields[index]" value="basic">Base
									</label>
									<label class="radio-inline">
										<input style="margin-right: 3px;" type="radio" v-bind:name="'permission-' + emailFields[index]" value="full">Proprietario
									</label>
								</div>
								<div class="invalid-feedback">
									<shared:fieldErrors entity="${list}" field="shared[]" />
								</div>
							</div>
							<button disabled id="btn-add-email" type="button" class="btn btn-light" @click="addEmail()">Aggiungi un'email</button>
							<div class="alert alert-secondary" role="alert" style="margin: 10px 0 10px 0;">
								<b>Visualizzazione</b>: Può visualizzare la lista e accedere alla chat.<br>
								<b>Permessi base</b>: Può modificare i prodotti in lista.<br>
								<b>Permessi proprietario</b>: Pieno controllo sulla lista e i prodotti.<br>
							</div>
						</div>
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
							<a href="${pageContext.servletContext.contextPath}/restricted/InfoList/${sessionScope.user.hash}/${requestScope.currentList.hash}" class="btn btn-light">Annulla</a>
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
							
						});
					});
					$('.js-example-basic-multiple').select2({

					});
				});
			});
			var app = new Vue({
				el: '#app',
				data: {
					emailFields: ['']
				},
				watch: {
					emailFields: function () {
						var c = 0;
						for (var i = 0; i < this.emailFields.length; i++) {
							if (this.emailFields[i] == '' || this.emailFields[i] === null)
								c = 1;
						}
						if (c) {
							$('#btn-add-email').prop('disabled', true);
						} else {
							$('#btn-add-email').prop('disabled', false);
						}
						// console.log(this.emailFields);
					}
				},
				methods: {
					addEmail: function () {
						this.emailFields.push('');
					}
				}
			});
		</script>
	</jsp:attribute>

</layouts:base>
