<%-- 
    Document   : ChangeImageAdmin
    Created on : 25-ago-2018, 14.44.41
    Author     : simon
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:auth pageTitle="Login">
	<jsp:attribute name="pageContent">
		<div class="container-fluid px-2">
			<div class="card login-card">
				<div class="card-body">
					<div class="text-center mb-4">
						<h1 class="h3 mb-3 font-weight-normal">Cambia immagine profilo</h1>
					</div>
					<c:if test="${not empty sessionScope.user.image}">
						<img style="max-width: 100px; max-height: 100px;" src="${pageContext.servletContext.contextPath}${user.image}" alt="Nome Cognome" title="Immagine profilo">
					</c:if>
					<c:if test="${empty sessionScope.user.image}">
						<img style="max-width: 100px; max-height: 100px;" src="${pageContext.servletContext.contextPath}/assets/image/avatar.png" alt="Nome Cognome" title="Immagine profilo">
					</c:if>
					<form class="form-signin" action="ChangeImageAdmin" method="POST" enctype='multipart/form-data'>
						<div class="custom-file my-2">
							<label class="custom-file-label" for="image">Scegli avatar</label>
							<input type="file" class="custom-file-input" id="image" name="image">
						</div>
						<button type="submit" class="btn btn-primary float-right mx-2">Conferma</button>
						<div class="mt-3">
							<a href="${pageContext.servletContext.contextPath}/restricted/admin/InfoAdmin">Indietro</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</jsp:attribute>
	<jsp:attribute name="customJs">
		<script>
			$(document).ready(function () {
				setTimeout(function () {
					var $Input = $('input:-webkit-autofill');
					$Input.next("label").addClass('active');
				}, 100);
			});
		</script>
	</jsp:attribute>
</layouts:auth>