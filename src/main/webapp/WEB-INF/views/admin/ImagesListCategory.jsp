<%-- 
    Document   : ImagesListCategory
    Created on : 14-ott-2018, 18.33.07
    Author     : giulia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:admin pageTitle="Change image">
	<jsp:attribute name="pageContent">
		<div class="container-fluid px-2 py-2">
			<div class="card card-change-image">
				<div class="card-body">
					<div class="text-center mb-4">
						<h3 class="mb-3 font-weight-normal">Scegli immagine</h3>
					</div>
					<form  action="ImagesListCategory" method="POST" enctype='multipart/form-data'>
						<div class="custom-file my-2">
							<label class="custom-file-label" for="image">Scegli avatar</label>
							<input type="file" class="custom-file-input" id="image" name="image">
						</div>
						<div class="float-right mt-3">
							<a href="${pageContext.servletContext.contextPath}/restricted/admin/ListCategory" class="btn btn-light mr-2">Annulla</a>
							<button type="submit" class="btn btn-change float-right">Conferma</button>
						</div> 
					</form>
				</div>
			</div>
		</div>
	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link href="${pageContext.servletContext.contextPath}/assets/css/admin_form.css" type="text/css" rel="stylesheet"/>
		<style>
			.card-change-image{
				max-width: 600px;	
				margin: 100px auto;
			}
			#content{
				background-color: lightgray;
			}
			.btn-change{
				background-color: #2a5788;
				color: white;
			}
		</style>
	</jsp:attribute>
	<jsp:attribute name="customJs">
	</jsp:attribute>
</layouts:admin>
