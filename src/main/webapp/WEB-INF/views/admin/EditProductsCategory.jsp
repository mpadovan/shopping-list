<%-- 
    Document   : EditProductsCategory
    Created on : 17-ago-2018, 16.35.23
    Author     : giulia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:admin pageTitle="Edit Product Category">
	<jsp:attribute name="pageContent">
		<div class="card card-new">
			<div class="card-body">
				<h1 class="card-title">Modifica categoria di prodotto</h1>
				<form method="POST" action="EditProductCategory">
					<div>
						<label for="name">Nome categoria</label>
						<input type="text"
							   class="form-control" 
							   id="name"
							   name="name" 
							   value="${productsCategory.name}"
							   required />
					</div>
					<div>
						<label for="note">Note</label>
						<input type="text"
							   class="form-control"
							   id="note"
							   name="note" 
							   value="${productsCategory.description}"
							   required>
					</div>
					<div>
						<label for="logo">Logo</label>
						<input type="text"
							   class="form-control"
							   id="logo"
							   name="logo" 
							   value="${productsCategory.logo}"
							   required>
					</div>
					<div>
						<label for="category">Categoria padre</label>
						<select class="select2 js-example-basic-single form-control py-3"
								name="category"
								id="category"
								name="category" 
								required
								>
							<option selected>Nessuna</option>
							<c:forEach var="c" items="${requestScope.productsCategory}">
								<option value="${c.id}">${c.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="float-right mt-3">
						<a href="${pageContext.servletContext.contextPath}/restricted/admin/ProductsCategory" class="btn btn-light">Annulla</a>
						<button class="btn btn-new ml-2" type="submit">Modifica</button>
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
