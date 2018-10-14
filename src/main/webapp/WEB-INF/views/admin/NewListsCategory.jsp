<%-- 
    Document   : NewListsCategory
    Created on : 17-ago-2018, 22.31.44
    Author     : giulia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared/" %>

<layouts:admin pageTitle="New List Category">
	<jsp:attribute name="pageContent">
		<div class="card card-new">
			<div class="card-body">
				<c:if test="${!empty listsCategory.errors}">
					<div class="alert alert-danger" role="alert">
						<h4 class="alert-heading">I dati inseriti non sono validi.</h4>
						<p>Controlla i campi sottostanti.</p>
					</div>
				</c:if>
				<div class="text-center mb-4">
					<h3 class="mb-3 font-weight-normal">Nuova categoria di lista</h3>
				</div>
				<form class="form-list" method="POST" action="NewListsCategory">
					<div>
						<label for="name">Nome categoria</label>
						<input type="text"
							   class="form-control ${(listsCategory.getFieldErrors("name") != null ? "is-invalid" : "")}" 
							   id="name"
							   name="name"
							   maxlength="40"
							   required />
						<div class="invalid-feedback">
							<shared:fieldErrors entity="${listsCategory}" field="name" />
						</div>
					</div>
					<div>
						<label for="description">Descrizione</label>
						<input type="text"
							   class="form-control ${(listsCategory.getFieldErrors("description") != null ? "is-invalid" : "")}"
							   id="description"
							   name="description"
							   maxlength="256"
							   />
						<div class="invalid-feedback">
							<shared:fieldErrors entity="${listsCategory}" field="description" />
						</div>
					</div>
					<div>
						<label for="image">Fotografia</label>
						<div class="custom-file">
							<input type="file"
								   class="custom-file-input form-control"
								   id="image"
								   name="image"
								   aria-describedby="image"
								   value="${listsCategoryImage.image}">
							<label class="custom-file-label" for="image">Scegli file</label>
						</div>
					</div>
					<div class="float-right mt-3">
						<a href="${pageContext.servletContext.contextPath}/restricted/admin/ListCategory" class="btn btn-light">Annulla</a>
						<button class="btn btn-new ml-2" type="submit">Crea</button>
					</div>
				</form>
			</div>
		</div>



	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link href="${pageContext.servletContext.contextPath}/assets/css/listForm.css" type="text/css" rel="stylesheet"/>
	</jsp:attribute>
	<jsp:attribute name="customJs">
	</jsp:attribute>
</layouts:admin>
