<%-- 
    Document   : EditProduct
    Created on : 8-ago-2018, 12.43.06
    Author     : giulia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared/" %>

<layouts:base pageTitle="Modifica prodotto">
    <jsp:attribute name="pageContent">
		<div class="cointainer-fluid px-2">
			<div class="card product-card mt-4 mx-auto">
				<div class="card-body">
					<c:if test="${!empty product.errors}">
						<div class="alert alert-danger" role="alert">
							<h4 class="alert-heading">I dati inseriti non sono validi.</h4>
							<p>Controlla i campi sottostanti.</p>
						</div>
					</c:if>
					<div class="text-center mb-4">
						<h3 class="mb-3 font-weight-normal">Modifica prodotto</h3>
					</div>
					<form class="form-product" method="POST" action="EditProduct?id=${param.id}" enctype='multipart/form-data'>
						<input type="hidden" name="id" value="${product.id}">
						<div>
							<label for="name">Nome prodotto</label>
							<input type="text"
								   class="form-control ${(product.getFieldErrors("name") != null ? "is-invalid" : "")}" 
								   id="name"
								   name="name" 
								   value="${product.name}"
								   maxlength="40"
								   required />
							<div class="invalid-feedback">
								<shared:fieldErrors entity="${product}" field="name" />
							</div>
						</div>
						<div>
							<label for="category">Categoria</label>
							<select class="select2 js-example-basic-single form-control py-3"
									name="category"
									id="category"
									name="category"
									required
									>
								<c:forEach var="c" items="${requestScope.productsCategory}">
									<option value="${c.id}"<c:if test="${product.category.id == c.id}"	> selected</c:if>>${c.name}</option>
								</c:forEach>
							</select>
						</div>
						<div>
							<label for="note">Note</label>
							<input type="text"
								   class="form-control ${(product.getFieldErrors("note") != null ? "is-invalid" : "")}"
								   id="note"
								   name="note" 
								   value="${product.note}"
								   maxlength="256"
								   >
							<div class="invalid-feedback">
								<shared:fieldErrors entity="${product}" field="note" />
							</div>
						</div>
						<div>
							<label for="image">Logo</label>
							<div class="custom-file">
								<input type="file"
									   class="custom-file-input form-control"
									   id="image"
									   name="logo"
									   aria-describedby="image">
								<label class="custom-file-label" for="image">Scegli file</label>
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
							<a href="${pageContext.servletContext.contextPath}/restricted/InfoProduct?id=${product.id}" title="Torna a info prodotto" class="btn btn-light">Annulla</a>
							<button class="btn btn-new ml-2" type="submit">Modifica</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assets/css/select2-bootstrap4.css">
		<link href="${pageContext.servletContext.contextPath}/assets/css/listForm.css" type="text/css" rel="stylesheet"/>
	</jsp:attribute>
	<jsp:attribute name="customJs">

		<script>
			$(document).ready(function () {
				$('.select2').each(function () {
					$(this).select2({
						
					});
				});
			});
		</script>
	</jsp:attribute>

</layouts:base>