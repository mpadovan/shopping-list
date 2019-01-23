<%-- 
    Document   : InfoProduct
    Created on : 8-ago-2018, 12.42.42
    Author     : giulia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:base pageTitle="Informazioni prodotto">
    <jsp:attribute name="pageContent">
		<div class="card info-product-card mx-auto">
			<div class="card-body">
				<div class="row">
					<div class="col-sm-12 col-md-auto text-center">
						<c:if test="${requestScope.product.photography ne 'null' && not empty requestScope.product.photography}">	
							<img src="${pageContext.servletContext.contextPath}${requestScope.product.photography}" class="image-product" alt="Fotografia prodotto" title="Fotografia prodotto">
						</c:if>
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
										<td>
											<c:if test="${requestScope.product.logo ne 'null' && not empty requestScope.product.logo}">	
												<div class="text-center info-custom-product
													 "><img class="rounded logo-product" src="${pageContext.servletContext.contextPath}${requestScope.product.logo}" alt="Logo prodotto" title="Logo prodotto"></div>
												</c:if>
										</td>
									</tr>
									<tr>
										<th scope="row">Categoria</th>
										<td><img class="rounded logo-product" src="${pageContext.servletContext.contextPath}${requestScope.product.category.logo}"> ${requestScope.product.category.name} </td>
									</tr>
								</tbody>
							</table>
						</div>

						<a href="${pageContext.servletContext.contextPath}/restricted/ProductList" class="btn btn-light" title="Torna a lista prodotti"><i class="fas fa-chevron-left"></i> Indietro</a>
						<a href="#delete-${p.id}" data-toggle="modal" data-target="#delete-${requestScope.product.id}" class="btn btn-danger float-right mx-2" title="Elimina"><i class="fas fa-trash"></i></a>
						<a href="${pageContext.servletContext.contextPath}/restricted/permission/EditProduct?id=${requestScope.product.id}" class="btn btn-light float-right" title="Modifica"><i class="fas fa-pen-square"></i></a>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="delete-${requestScope.product.id}" tabindex="-1" role="dialog" aria-labelledby="delete-${requestScope.product.id}" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="delete-${requestScope.product.id}">Elimina prodotto</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						Sei sicuro di voler eliminare il prodotto "${requestScope.product.name}"?
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
						<a href="${pageContext.servletContext.contextPath}/restricted/DeleteProduct?id=${requestScope.product.id}" class="btn btn-danger">Elimina</a>
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
