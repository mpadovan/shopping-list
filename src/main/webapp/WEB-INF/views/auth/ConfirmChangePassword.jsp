<%-- 
    Document   : ConfirmChangePassword
    Created on : 21-ago-2018, 19.02.37
    Author     : simon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:auth pageTitle="Password modificata">
	<jsp:attribute name="pageContent">
		<div class="container-fluid px-2">
			<div class="card login-card">
				<div class="card-body">
					<div class="text-center mb-4">
							<h1 class="h3 mb-3 font-weight-normal">Password modificata</h1>
						</div>
					<div class="alert alert-success" role="alert">
						<h5>Hai modificato correttamente la tua password. Torna al <a href="${pageContext.servletContext.contextPath}/Login" class="badge badge-light">Login</a></h5>
					</div>
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