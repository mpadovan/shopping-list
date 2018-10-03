<%-- 
    Document   : EditListsCategory
    Created on : 18-ago-2018, 14.01.44
    Author     : giulia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared/" %>

<layouts:admin pageTitle="Edit Lists Category">
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
					<h3 class="mb-3 font-weight-normal">Modifica categoria di lista</h3>
				</div>
				<form class="form-list" method="POST" action="EditListsCategory?id=${param.id}" enctype='multipart/form-data'>
					<input type="hidden" name="id" value="${listsCategory.id}">
					<div>
						<label for="name">Nome categoria</label>
						<input type="text"
							   class="form-control ${(listsCategory.getFieldErrors("name") != null ? "is-invalid" : "")}" 
							   id="name"
							   name="name" 
							   value="${listsCategory.name}"
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
							   value="${listsCategory.description}"
							   maxlength="256"
							   >
						<div class="invalid-feedback">
							<shared:fieldErrors entity="${listsCategory}" field="description" />
						</div>
					</div>
					<div>
						<c:forEach var="i" items="${requestScope.listsCategoryImage}">
							<c:if test="${listsCategory.id==i.category.id}"><input type="hidden" name="imageId" value="${i.id}"></c:if>
						</c:forEach>
						<c:forEach var="c" items="${requestScope.listsCategoryImage}">
							<c:if test="${listsCategory.id==c.category.id}">
								<label for="image">Fotografia</label>
								<p><i>1- ${pageContext.servletContext.contextPath}${c.image}</i></p>
								<div class="custom-file">
									<input type="file"
										   class="custom-file-input form-control"
										   name="image"
										   aria-describedby="image"
										   value="${c.image}">
									<label class="custom-file-label" for="image">Scegli file</label>
								</div>
							</c:if>
						</c:forEach>
						
					</div>
					<div class="float-right mt-3">
						<a href="${pageContext.servletContext.contextPath}/restricted/admin/ListCategory" class="btn btn-light">Annulla</a>
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
