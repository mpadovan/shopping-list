<%-- 
    Document   : NewCategoryProduct
    Created on : 17-ago-2018, 12.48.40
    Author     : giulia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared/" %>

<layouts:admin pageTitle="New Product Category">
	<jsp:attribute name="pageContent">
		<div class="card card-new">
			<div class="card-body">
				<c:if test="${!empty productCategory.errors}">
					<div class="alert alert-danger" role="alert">
						<h4 class="alert-heading">I dati inseriti non sono validi.</h4>
						<p>Controlla i campi sottostanti.</p>
					</div>
				</c:if>
				<div class="text-center mb-4">
					<h3 class="mb-3 font-weight-normal">Nuovo categoria di prodotto</h3>
				</div>
				<form class="form-list" method="POST" action="NewProductsCategory" enctype='multipart/form-data'>
					<div>
						<label for="name">Nome categoria</label>
						<input type="text"
							   class="form-control ${(productCategory.getFieldErrors("name") != null ? "is-invalid" : "")}" 
							   id="name"
							   name="name" 
							   required />
						<div class="invalid-feedback">
							<shared:fieldErrors entity="${productCategory}" field="name" />
						</div>
					</div>
					<div>
						<label for="description">Descrizione</label>
						<input type="text"
							   class="form-control ${(productCategory.getFieldErrors("description") != null ? "is-invalid" : "")}"
							   id="description"
							   name="description" 
							   />
						<div class="invalid-feedback">
							<shared:fieldErrors entity="${productCategory}" field="description" />
						</div>
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
					<div class="float-right mt-3">
						<a href="${pageContext.servletContext.contextPath}/restricted/admin/ProductsCategory" class="btn btn-light">Annulla</a>
						<button class="btn btn-new ml-2" type="submit">Crea</button>
					</div>
				</form>
			</div>
		</div>



	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assets/css/select2-bootstrap4.css">
		<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
		<link href="${pageContext.servletContext.contextPath}/assets/css/listForm.css" type="text/css" rel="stylesheet"/>
	</jsp:attribute>
	<jsp:attribute name="customJs">
		<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
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

