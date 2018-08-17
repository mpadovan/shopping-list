<%-- 
    Document   : CategoryProduct
    Created on : 13-ago-2018, 12.28.26
    Author     : giulia
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<layouts:admin pageTitle="Admin category product">
	<jsp:attribute name="pageContent">
		<div class="card" style="margin-left: auto; margin-right: auto; margin-top: 10px; margin-bottom: 10px;">
			<div class="card-body">
				<h1 class="card-title">Categorie di prodotto</h1>
				<p><a href="${pageContext.servletContext.contextPath}/restricted/admin/NewProductsCategory">Nuovo categoria <i class="fas fa-plus-circle"></i></a></p>
				<form>
					<label class="sr-only" for="search">Cerca</label>
					<div class="input-group mb-2">
						<input type="text" class="form-control" id="search" name="search" placeholder="Cerca">
						<div class="input-group-append">
							<button class="btn btn-outline-secondary" type="submit"><i class="fas fa-search"></i></button>
						</div>
					</div>
				</form>
				<nav aria-label="Page navigation example" class="float-right">
					<ul class="pagination">
						<li class="page-item"><a class="page-link" href="#">Precedente</a></li>
						<li class="page-item"><a class="page-link" href="#">...</a></li>
						<li class="page-item"><a class="page-link" href="#">Successivo</a></li>
					</ul>
				</nav>
				<table class="table table-responsive-md table-striped">
					<thead>
						<tr>
							<th>Nome</th>
							<th>Descrizione</th>
							<th>Logo</th>
							<th>Categoria padre</th>
							<th>Gestisci</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="c" items="${requestScope.productsCategory}">
							<tr>
								<td>${c.name}</td>
								<td>${c.description}</td>
								<td>${c.logo}</td>
								<td>${c.category.name}</td>
								<td>
									<span><a href="#"><i class="fas fa-pen-square"></i></a></span>
									<span class="ml-4"><a href="#delete-${c.id}" data-toggle="modal" data-target="#delete-${c.id}"><i class="fas fa-trash"></i></a></span>
								</td>
							</tr>
							<!-- Modal -->
						<div class="modal fade" id="delete-${c.id}" tabindex="-1" role="dialog" aria-labelledby="delete" aria-hidden="true">
							<div class="modal-dialog modal-dialog-centered" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title" id="delete-${c.id}">Elimina categoria di prodotto</h5>
										<button type="button" class="close" data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
									<div class="modal-body">
										Sei sicuro di voler eliminare la categoria di prodotto "${c.name}"?
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
										<a href="${pageContext.servletContext.contextPath}/restricted/admin/DeleteProductsCategory?id=${c.id}" class="btn btn-danger">Elimina</a>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
					<c:if test="${fn:length(requestScope.productsCategory) == 0}">
						<tr>
							<td class="text-center" colspan="6">Nessun risultato</td>
						</tr>
					</c:if>
					</tbody>
				</table>
				<c:if test="${requestScope.checkParam > 0}">
					<div class="text-center"><a href="${pageContext.servletContext.contextPath}/restricted/admin/ProductsCategory"><p>Torna alla lista</p></a></div>
				</c:if>
			</div>
		</div>

	</jsp:attribute>
	<jsp:attribute name="customCss">

	</jsp:attribute>
	<jsp:attribute name="customJs">

	</jsp:attribute>
</layouts:admin>