<%--
    Document   : ProductList
    Created on : 12-ago-2018, 22.41.27
    Author     : giulia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<layouts:admin pageTitle="Admin products">
	<jsp:attribute name="pageContent">
		<div class="card card-list-product">
			<div class="card-body">
				<h1 class="card-title">Prodotti</h1>
				<p><a class="a-admin"href="${pageContext.servletContext.contextPath}/restricted/admin/NewPublicProduct">Nuovo prodotto <i class="fas fa-plus-circle"></i></a></p>
				<form>
					<label class="sr-only" for="search">Cerca</label>
					<div class="input-group mb-2">
						<input type="text" class="form-control" id="search" name="search" placeholder="Cerca">
						<div class="input-group-append">
							<button class="btn btn-outline-secondary" type="submit"><i class="fas fa-search"></i></button>
						</div>
					</div>
				</form>
				<table class="table table-responsive-md table-striped">
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
						<c:forEach var="p" items="${requestScope.publicProducts}">
							<tr>
								<td>${p.name}</td>
								<td>${p.note}</td>
								<c:if test="${not empty p.logo}">
									<td>
										<div class="info-custom-product text-center"><img class="rounded logo-product" src="${pageContext.servletContext.contextPath}${p.logo}"></div>
									</td>
								</c:if>
								<c:if test="${empty p.logo}">
									<td>

									</td>
								</c:if>
								<c:if test="${not empty p.photography}">
									<td>
										<div class="info-product-image text-center"><img class="image-product-list" src="${pageContext.servletContext.contextPath}${p.photography}" alt="" title=""></div>
									</td>
								</c:if>
								<c:if test="${empty p.photography}">
									<td>

									</td>
								</c:if>
								<td>${p.category.name}</td>
								<td>
									<span><a href="${pageContext.servletContext.contextPath}/restricted/admin/EditPublicProduct?id=${p.id}"><i class="fas fa-pen-square"></i></a></span>
									<span class="ml-4"><a href="#delete-${c.id}" data-toggle="modal" data-target="#delete-${p.id}"><i class="fas fa-trash"></i></a></span>
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
										<a href="${pageContext.servletContext.contextPath}/restricted/admin/DeletePublicProduct?id=${p.id}" class="btn btn-danger">Elimina</a>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
					<c:if test="${fn:length(requestScope.publicProducts) == 0}">
						<tr>
							<td class="text-center" colspan="6">Nessun risultato</td>
						</tr>
					</c:if>
					</tbody>
				</table>
				<c:if test="${requestScope.checkParam > 0}">
					<div class="text-center"><a href="${pageContext.servletContext.contextPath}/restricted/admin/PublicProductList"><p>Torna alla lista</p></a></div>
				</c:if>
			</div>
		</div>



	</jsp:attribute>
	<jsp:attribute name="customCss">

	</jsp:attribute>
	<jsp:attribute name="customJs">

	</jsp:attribute>
</layouts:admin>
