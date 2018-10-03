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
						<c:if test="${requestScope.duplicateName}">
							<div style="color: #cc0000">Hai già una lista con questo nome, cambialo per evitare di confonderti</div>
						</c:if>
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
						<div id="app">
							<div id="sharedList">Condividi con:<br>
								<input type="email" name="shared[]" class="form-control" v-for="(field, index) in emailFields" v-model="emailFields[index]">
							</div>
							<button id="btn-add-email" type="button" class="btn btn-light" @click="addEmail()">Aggiungi un'email</button>
						</div>
						<div>
							<label for="description">Descrizione</label>
							<input type="text"
								   class="form-control"
								   id="description"
								   name="description"
								   >
						</div>
						<div>
							<label for="image">Immagine</label>
							<div class="custom-file">
								<input type="file"
									   class="custom-file-input form-control"
									   id="image"
									   name="image"
									   accept="image/*"
									   aria-describedby="image">
								<label class="custom-file-label" for="image">Scegli file</label>
							</div>
						</div>
						<div class="float-right mt-3">
							<a href="${pageContext.servletContext.contextPath}/restricted/HomePageLogin/${sessionScope.user.id}" class="btn btn-light">Annulla</a>
							<button class="btn btn-new ml-2" type="submit">Crea</button>
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
			var app = new Vue({
				el: '#app',
				data: {
					emailFields: ['']
				},
				watch: {
					emailFields: function () {
						var c = 0;
						for (var i = 0; i < this.emailFields.length; i++) {
							if (this.emailFields[i] == '')
								c = 1;
						}
						if (c) {
							$('#btn-add-email').prop('disabled', true);
						} else {
							$('#btn-add-email').prop('disabled', false);
						}
						console.log(this.emailFields);
					}
				},
				methods: {
					addEmail: function() {
						this.emailFields.push('');
					}
				}
			});
		</script>
	</jsp:attribute>

</layouts:base>
