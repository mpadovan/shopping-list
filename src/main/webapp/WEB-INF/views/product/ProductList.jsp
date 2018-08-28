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
					<table class="table table-responsive-md">
						<thead>
							<tr>
								<th>Nome prodotto</th>
								<th>Note</th>
								<th>Logo</th>
								<th>Fotografia</th>
								<th>Categoria</th>
								<th>Gestisci</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="p" items="${requestScope.products}">
								<tr>
									<td><a href="${pageContext.servletContext.contextPath}/restricted/InfoProduct?id=${p.id}">${p.name}</a></td>
									<td>${p.note}</td>
									<c:if test="${p.logo != null}">
									<td>
										<div class="info-product text-center"><img class="rounded logo-product" src="${pageContext.servletContext.contextPath}${p.logo}"></div>
									</td>
									</c:if>
									<c:if test="${p.logo == null}">
										<td>
											
										</td>
									</c:if>
									<c:if test="${p.photography != null}">
										<td>
											<div class="info-product-image text-center"><img class="image-product-list" src="${pageContext.servletContext.contextPath}${p.photography}" alt="" title=""></div>
										</td>
									</c:if>
									<c:if test="${p.photography == null}">
										<td>
											
										</td>
									</c:if>

									<td>${p.category.name}</td>
									<td>
										<span><a href="${pageContext.servletContext.contextPath}/restricted/admin/EditPublicProduct?id=${p.id}"><i class="fas fa-pen-square"></i></a></span>
										<span class="ml-4"><a href="#delete-${p.id}" data-toggle="modal" data-target="#delete-${p.id}"><i class="fas fa-trash"></i></a></span>
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
											<a href="#" class="btn btn-danger">Elimina</a>
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
