<%-- 
    Document   : ChangeImageAdmin
    Created on : 25-ago-2018, 14.44.41
    Author     : simon
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:admin pageTitle="Cambia immagine">
	<jsp:attribute name="pageContent">
		<div class="container-fluid px-2 py-2">
			<div class="card card-change-image">
				<div class="card-body">
					<div class="text-center mb-4">
						<h3 class="mb-3 font-weight-normal">Cambia immagine profilo</h3>
					</div>
					<form  action="ChangeImageAdmin" method="POST" enctype='multipart/form-data'>
						<div class="custom-file my-2">
							<label class="custom-file-label" for="image">Scegli avatar</label>
							<input type="file" class="custom-file-input" id="image" name="image">
						</div>
						<div class="float-right mt-3">
							<a href="${pageContext.servletContext.contextPath}/restricted/admin/InfoAdmin" class="btn btn-light mr-2">Annulla</a>
							<button type="submit" class="btn btn-change float-right">Conferma</button>
						</div> 
					</form>
				</div>
			</div>
		</div>
	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link href="${pageContext.servletContext.contextPath}/assets/css/change_image_user.css" type="text/css" rel="stylesheet"/>
	</jsp:attribute>
	<jsp:attribute name="customJs">
	</jsp:attribute>
</layouts:admin>