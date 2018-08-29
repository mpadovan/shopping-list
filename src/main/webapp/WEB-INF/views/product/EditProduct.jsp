<%-- 
    Document   : EditProduct
    Created on : 8-ago-2018, 12.43.06
    Author     : giulia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<layouts:base pageTitle="Edit product">
    <jsp:attribute name="pageContent">
		<div class="cointainer-fluid px-2">
			<div class="card product-card">
				<div class="card-body">
					<div class="text-center mb-4">
						<h1 class="h3 mb-3 font-weight-normal">Modifica prodotto</h1>
					</div>
					<form method="POST" action="EditProduct?id=${param.id}">
						<input type="hidden" name="id" value="${product.id}">
						<div>
							<label for="name">Nome prodotto</label>
							<input type="text"
								   class="form-control" 
								   id="name"
								   name="name" 
								   value="${product.name}"
								   required />
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
								   class="form-control"
								   id="note"
								   name="note" 
								   value="${product.note}"
								   required>
						</div>
						<div>
							<label for="logo">Logo</label>
							<input type="text"
								   class="form-control"
								   id="logo"
								   name="logo" 
								   value="${product.logo}">
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
							<a href="${pageContext.servletContext.contextPath}/restricted/InfoProduct?id=${product.id}" class="btn btn-light">Annulla</a>
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
		<!--<script src="assets/js/landing_page.js"></script>-->
		<script>
			$(document).ready(function () {
				$('.select2').each(function () {
					$(this).select2({
						theme: 'bootstrap4'
					});
				});
			});
		</script>
	</jsp:attribute>

</layouts:base>