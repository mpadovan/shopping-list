<%-- 
    Document   : ProductList
    Created on : 28-ago-2018, 15.50.21
    Author     : giulia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<layouts:base pageTitle="Product list">
    <jsp:attribute name="pageContent">
		<div class="card m-3">
			<div class="card-body">
				<h2 class="card-title text-center">
					<div id="btn-back" class="float-left font-product-list">
						<a href="${pageContext.servletContext.contextPath}/restricted/HomePageLogin/${sessionScope.user.id}"><i class="fas fa-chevron-left"></i> Indietro</a>
					</div>
					<div id="btn-back-res" class="float-left font-product-list">
						<a href="${pageContext.servletContext.contextPath}/restricted/HomePageLogin/${sessionScope.user.id}"><i class="fas fa-chevron-left"></i></a>
					</div> I tuoi prodotti
					<div id="btn-new" class="float-right font-product-list">
						<a class="info-custom-product-a" href="${pageContext.servletContext.contextPath}/restricted/NewProduct">Crea prodotto <i class="fas fa-plus-circle"></i></a>
					</div>
					<div id="btn-new-res" class="float-right font-product-list">
						<a class="info-custom-product-a" href="${pageContext.servletContext.contextPath}/restricted/NewProduct"><i class="fas fa-plus-circle"></i></a>
					</div>
				</h2>
				<div class="container-fluid mt-3">
					<table class="table table-responsive-md">
						<thead>
							<tr>
								<th>Nome prodotto</th>
								<th>Note</th>
								<th>Logo</th>
								<th>Fotografia</th>
								<th>Categoria</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="p" items="${requestScope.products}">
								<tr>
									<td><a class="info-custom-product-a"href="${pageContext.servletContext.contextPath}/restricted/InfoProduct?id=${p.id}">${p.name}</a></td>
									<td>${p.note}</td>
									<td>
										<c:if test="${p.logo ne 'null'}" >
											<div class="info-custom-product"><img class="logo-product" src="${pageContext.servletContext.contextPath}${p.logo}"></div>
										</c:if>
									</td>
									<td>
										<c:if test="${p.photography ne 'null'}">
											<div class="info-product-image text-center"><img class="image-product-list" src="${pageContext.servletContext.contextPath}${p.photography}"></div>
										</c:if>
									</td>
									<td>${p.category.name}</td>
									<td>
										<span class="ml-4"><a class="info-custom-product-a"href="#delete-${p.id}" data-toggle="modal" data-target="#delete-${p.id}"><i class="fas fa-trash"></i></a></span>
									</td>
								</tr>
								<!-- Modal -->
							<div class="modal fade" id="delete-${p.id}" tabindex="-1" role="dialog" aria-labelledby="delete-${p.id}" aria-hidden="true">
								<div class="modal-dialog modal-dialog-centered" role="document">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title" id="delete-${p.id}">Elimina prodotto</h5>
											<button type="button" class="close" data-dismiss="modal" aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
										</div>
										<div class="modal-body">
											Sei sicuro di voler eliminare il prodotto "${p.name}"?
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
											<a href="${pageContext.servletContext.contextPath}/restricted/DeleteProduct?id=${p.id}" class="btn btn-danger">Elimina</a>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>

	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link href="${pageContext.servletContext.contextPath}/assets/css/info_product.css" type="text/css" rel="stylesheet"/>
	</jsp:attribute>
	<jsp:attribute name="customJs">

	</jsp:attribute>

</layouts:base>
