<%-- 
    Document   : ProductList
    Created on : 28-ago-2018, 15.50.21
    Author     : giulia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<layouts:base pageTitle="Product list">
    <jsp:attribute name="pageContent">
		<div class="card m-3">
			<div class="card-body">
				<h2 class="card-title text-center">I tuoi prodotti</h2>
				<div class="container-fluid mt-3">
					<ul class="list-group list-group-flush">
						<c:forEach items="${requestScope.products}" var="p">
							<li class="list-group-item"><a href="#">${p.name}</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>

	</jsp:attribute>
	<jsp:attribute name="customCss">
	</jsp:attribute>
	<jsp:attribute name="customJs">

	</jsp:attribute>

</layouts:base>
