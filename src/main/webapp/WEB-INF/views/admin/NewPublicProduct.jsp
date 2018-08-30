<%-- 
    Document   : NewPublicProduct
    Created on : 16-ago-2018, 21.28.47
    Author     : giulia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:admin pageTitle="New Public Product">
	<jsp:attribute name="pageContent">
		<div class="card card-new">
			<div class="card-body">
				<h1 class="card-title">Nuovo prodotto</h1>
				<form method="POST" action="NewPublicProduct" enctype='multipart/form-data'>
					<div>
						<label for="name">Nome prodotto</label>
						<input type="text"
							   class="form-control" 
							   id="name"
							   name="name" 
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
							<c:forEach var="c" items="${requestScope.productsCategory}">
								<option value="${c.id}">${c.name}</option>
							</c:forEach>
						</select>
					</div>
					<div>
						<label for="note">Note</label>
						<input type="text"
							   class="form-control"
							   id="note"
							   name="note" 
							   >
					</div>
					<div>
						<label for="logo">Logo</label>
						<div class="custom-file">
							<input type="file"
								   class="custom-file-input form-control"
								   id="logo"
								   name="logo"
								   aria-describedby="logo">
							<label class="custom-file-label" for="logo">Scegli file</label>
						</div>
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
						<a href="${pageContext.servletContext.contextPath}/restricted/admin/PublicProductList" class="btn btn-light">Annulla</a>
						<button class="btn btn-new ml-2" type="submit">Crea</button>
					</div>
				</form>
			</div>
		</div>



	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assets/css/select2-bootstrap4.css">
	</jsp:attribute>
	<jsp:attribute name="customJs">
		<script>
			$(document).ready(function () {
				$('select').each(function () {
					$(this).select2({
						theme: 'bootstrap4'
					});
				});
			});

		</script>
	</jsp:attribute>
</layouts:admin>
