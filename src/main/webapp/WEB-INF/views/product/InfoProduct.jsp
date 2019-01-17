<%-- 
    Document   : InfoProduct
    Created on : 8-ago-2018, 12.42.42
    Author     : giulia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:base pageTitle="Info product">
    <jsp:attribute name="pageContent">
		<div class="card info-product-card">
			<div class="card-body">
				<div class="row">
					<div class="col-sm-12 col-md-auto text-center">
						<img src="${pageContext.servletContext.contextPath}${requestScope.product.photography}" class="image-product" alt="...">
					</div>
					<div class="col">
						<h5 class="card-title text-center mt-0">Informazioni prodotto "${requestScope.product.name}"</h5>
						<div class="table-responsive-md">
							<table class="table ">
								<tbody>

									<tr>
										<th scope="row">Nome</th>
										<td>${requestScope.product.name}</td>
									</tr>
									<tr>
										<th scope="row">Note</th>
										<td>${requestScope.product.note}</td>
									</tr>
									<tr>
										<th scope="row">Logo</th>
											<c:if test="${requestScope.product.logo != ''}">
											<td>
												<div class="text-center info-custom-product"><img class="rounded logo-product" src="${pageContext.servletContext.contextPath}${requestScope.product.logo}" alt="Logo" title="Logo"></div>
											</td>
										</c:if>
										<c:if test="${requestScope.product.logo == ''}">
											<td></td>
										</c:if>
									</tr>
									<tr>
										<th scope="row">Categoria</th>
										<td>${requestScope.product.category.name}</td>
									</tr>
								</tbody>
							</table>
						</div>

						<a href="${pageContext.servletContext.contextPath}/restricted/ProductList" class="btn btn-light"><i class="fas fa-chevron-left"></i> Indietro</a>
						<a href="${pageContext.servletContext.contextPath}/restricted/permission/EditProduct?id=${requestScope.product.id}" class="btn btn-light float-right mx-2" title="Modifica"><i class="fas fa-pen-square"></i> Modifica</a>
					</div>
				</div>
			</div>
		</div>
	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link href="${pageContext.servletContext.contextPath}/assets/css/info_product.css" type="text/css" rel="stylesheet"/>
	</jsp:attribute>
	<jsp:attribute name="customJs">
		<!--<script src="assets/js/landing_page.js"></script>-->
		<script>

		</script>
	</jsp:attribute>

</layouts:base>
