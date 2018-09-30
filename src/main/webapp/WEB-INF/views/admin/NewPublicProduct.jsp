<%-- 
    Document   : NewPublicProduct
    Created on : 16-ago-2018, 21.28.47
    Author     : giulia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared/" %>

<layouts:admin pageTitle="New Public Product">
	<jsp:attribute name="pageContent">
		<div class="card card-new">
			<div class="card-body">
				<c:if test="${!empty product.errors}">
					<div class="alert alert-danger" role="alert">
						<h4 class="alert-heading">I dati inseriti non sono validi.</h4>
						<p>Controlla i campi sottostanti.</p>
					</div>
				</c:if>
				<div class="text-center mb-4">
					<h1 class="h3 mb-3 font-weight-normal">Nuovo prodotto</h1>
				</div>
				<form  class="form-product" method="POST" action="NewPublicProduct" enctype='multipart/form-data'>
					<div>
						<label for="name">Nome prodotto</label>
						<input type="text"
							   class="form-control ${(product.getFieldErrors("name") != null ? "is-invalid" : "")}" 
							   id="name"
							   name="name" 
							   required />
						<div class="invalid-feedback">
							<shared:fieldErrors entity="${product}" field="name" />
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
							<c:forEach var="c" items="${requestScope.productsCategory}">
								<option value="${c.id}">${c.name}</option>
							</c:forEach>
						</select>
					</div>
					<div>
						<label for="note">Note</label>
						<input type="text"
							   class="form-control ${(product.getFieldErrors("note") != null ? "is-invalid" : "")}"
							   id="note"
							   name="note" 
							   >
						<div class="invalid-feedback">
							<shared:fieldErrors entity="${product}" field="note" />
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
