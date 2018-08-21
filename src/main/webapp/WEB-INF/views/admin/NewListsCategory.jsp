<%-- 
    Document   : NewListsCategory
    Created on : 17-ago-2018, 22.31.44
    Author     : giulia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:admin pageTitle="New List Category">
	<jsp:attribute name="pageContent">
		<div class="card card-new">
			<div class="card-body">
				<h1 class="card-title">Nuovo categoria di lista</h1>
				<form method="POST" action="NewListsCategory">
					<div>
						<label for="name">Nome categoria</label>
						<input type="text"
							   class="form-control" 
							   id="name"
							   name="name" 
							   required />
					</div>
					<div>
						<label for="note">Descrizione</label>
						<input type="text"
							   class="form-control"
							   id="note"
							   name="description" 
							   required>
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

	</jsp:attribute>
	<jsp:attribute name="customJs">

	</jsp:attribute>
</layouts:admin>
