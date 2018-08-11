<%-- 
    Document   : AuthFailed
    Created on : 11-ago-2018, 18.41.02
    Author     : giulia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <title>Error page</title>
    </head>
    <body>
		<div class="text-center">
			<div class="alert alert-danger" role="alert">
				Errore 401.
			</div>
			<div class="py-3">
				<h5><b>Autenticazione fallita:</b> qualcosa non ha funzionato. Riprova andando al <a class="badge badge-dark" href="${pageContext.servletContext.contextPath}/Login">Login</a></h5>
			</div>
		</div>
    </body>
</html>
