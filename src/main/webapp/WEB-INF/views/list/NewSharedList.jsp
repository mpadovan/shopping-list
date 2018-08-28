<%--
    Document   : NewSharedList
    Created on : 16-lug-2018, 14.30.53
    Author     : giuliapeserico
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<layouts:base pageTitle="Add shered list">
    <jsp:attribute name="pageContent">	
		<div class="container-fluid">	
			<div class="card new-list-card">
				<div class="card-body">
					<div class="text-center mb-4">
						<h1 class="h3 mb-3 font-weight-normal">Nuova lista</h1>
					</div>
					<form class="form-list" action="NewSharedList" method="POST" enctype='multipart/form-data'>
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
				$('select').each(function () {
					$(this).select2({
						theme: 'bootstrap4'
					});
				});
				$('.js-example-basic-multiple').select2({

				});
			});

		</script>
	</jsp:attribute>

</layouts:base>


