<%-- 
    Document   : EditListsCategory
    Created on : 18-ago-2018, 14.01.44
    Author     : giulia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:admin pageTitle="Edit Lists Category">
	<jsp:attribute name="pageContent">
		<div class="card card-new">
			<div class="card-body">
				<h1 class="card-title">Modifica categoria di lista</h1>
				<form method="POST" action="EditListsCategory?id=${param.id}">
					<input type="hidden" name="id" value="${listsCategory.id}">
					<div>
						<label for="name">Nome categoria</label>
						<input type="text"
							   class="form-control" 
							   id="name"
							   name="name" 
							   value="${listsCategory.name}"
							   required />
					</div>
					<div>
						<label for="note">Descrizione</label>
						<input type="text"
							   class="form-control"
							   id="description"
							   name="description" 
							   value="${listsCategory.description}"
							   >
					</div>
					<div>
						<c:forEach var="i" items="${requestScope.listsCategoryImage}">
							<c:if test="${listsCategory.id==i.category.id}"><input type="hidden" name="imageId" value="${i.id}"></c:if>
						</c:forEach>
						<c:forEach var="c" items="${requestScope.listsCategoryImage}">
							<c:if test="${listsCategory.id==c.category.id}">
								<label for="image">Fotografia</label>
								<p><i>1- ${c.image}</i></p>
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