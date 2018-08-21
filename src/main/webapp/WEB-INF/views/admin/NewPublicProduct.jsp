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
				<form method="POST" action="NewPublicProduct">
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
							   value="${product.logo}"
							   >
					</div>
					<div>
						<label for="photography">Fotografia</label>
						<div class="custom-file">
							<input type="file"
								   class="custom-file-input form-control"
								   id="photography"
								   name="photography"
								   aria-describedby="photography"
								   value="${product.photography}">
							<label class="custom-file-label" for="photography">Scegli file</label>
						</div>
					</div>
					<div>
						<label for="category">Categoria</label>
						<select class="select2 js-example-basic-single form-control py-3"
								name="category"
								id="category"
								name="category" 
								value="${product.category}"
								required
								>
							<c:forEach var="c" items="${requestScope.productsCategory}">
								<option value="${c.id}">${c.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="float-right mt-3">
						<a href="${pageContext.servletContext.contextPath}/restricted/admin/ProductList" class="btn btn-light">Annulla</a>
						<button class="btn btn-new ml-2" type="submit">Crea</button>
					</div>
				</form>
			</div>
		</div>



	</jsp:attribute>
	<jsp:attribute name="customCss">

	</jsp:attribute>
	<jsp:attribute name="customJs">

	</jsp:attribute>
</layouts:admin>
