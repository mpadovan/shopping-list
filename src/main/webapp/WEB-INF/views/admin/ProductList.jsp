<%--
    Document   : ProductList
    Created on : 12-ago-2018, 22.41.27
    Author     : giulia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<layouts:admin pageTitle="Admin products">
	<jsp:attribute name="pageContent">
		<div class="card" style="margin-left: auto; margin-right: auto; margin-top: 10px; margin-bottom: 10px;">
			<div class="card-body">
				<h1 class="card-title">Prodotti</h1>
				<p><a href="#">Nuovo prodotto <i class="fas fa-plus-circle"></i></a></p>
				<form>
					<label class="sr-only" for="cerca">Cerca</label>
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
								<td>${p.logo}</td>
								<td>${p.photography}</td>
								<td>${p.category}</td>
								<td><span><a href="#"><i class="fas fa-pen-square"></i></a></span><span class="ml-4"><a href="#" data-toggle="modal" data-target="#delete"><i class="fas fa-trash"></i></a></span></td>
							</tr>
						</c:forEach>
						<c:if test="${fn:length(requestScope.publicProducts) == 0}">
							<tr>
								<td class="text-center" colspan="6">Nessun risultato</td>
							</tr>
						</c:if>
					</tbody>
				</table>
				<c:if test="${requestScope.checkParam > 0}">
					<div class="text-center"><a href="${pageContext.servletContext.contextPath}/restricted/admin/ProductList"><p>Torna alla lista</p></a></div>
				</c:if>
			</div>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="delete" tabindex="-1" role="dialog" aria-labelledby="delete" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="delete">Elimina prodotto</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						Sei sicuro di voler eliminare il prodotto?
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
						<button type="button" class="btn btn-danger">Elimina</button>
					</div>
				</div>
			</div>
		</div>


	</jsp:attribute>
	<jsp:attribute name="customCss">

	</jsp:attribute>
	<jsp:attribute name="customJs">

	</jsp:attribute>
</layouts:admin>
