<%-- 
    Document   : CategoryList
    Created on : 13-ago-2018, 12.23.16
    Author     : giulia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<layouts:admin pageTitle="List category">
	<jsp:attribute name="pageContent">
		<div class="card" style="margin-left: auto; margin-right: auto; margin-top: 10px; margin-bottom: 10px;">
			<div class="card-body">
				<h1 class="card-title">Categorie di lista</h1>
				<p><a href="${pageContext.servletContext.contextPath}/restricted/admin/NewListsCategory">Nuovo categoria <i class="fas fa-plus-circle"></i></a></p>
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
							<th>Nome</th>
							<th>Descrizione</th>
							<th>Immagine</th>
							<th>Gestisci</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="c" items="${requestScope.listsCategory}">
							<tr>
								<td>${c.name}</td>
								<td>${c.description}</td>
								<td class="td-images">
									<c:forEach var="i" items="${requestScope.listsCategoryImage}">
										<c:if test="${c.id == i.category.id}">
											<img class="cat-list-img" src="${pageContext.servletContext.contextPath}${i.image}">
										</c:if>
									</c:forEach>
								<td class="td-handler">
									<a href="#images" data-category-id="${c.id}" data-toggle="modal" data-target="#images"><i class="fas fa-images"></i></a>
									<a href="${pageContext.servletContext.contextPath}/restricted/admin/EditListsCategory?id=${c.id}"><i class="fas fa-pen-square"></i></a>
									<a href="#delete-${c.id}" data-toggle="modal" data-target="#delete-${c.id}"><i class="fas fa-trash"></i></a>
								</td>
							</tr>
							<!-- Modal -->
						<div class="modal fade" id="delete-${c.id}" tabindex="-1" role="dialog" aria-labelledby="delete" aria-hidden="true">
							<div class="modal-dialog modal-dialog-centered" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title" id="delete-${c.id}">Elimina categoria di lista</h5>
										<button type="button" class="close" data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
									<div class="modal-body">
										Sei sicuro di voler eliminare la categoria di lista "${c.name}"?
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
										<a href="${pageContext.servletContext.contextPath}/restricted/admin/DeleteListCategory?id=${c.id}" class="btn btn-danger">Elimina</a>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
					<c:if test="${fn:length(requestScope.listsCategory) == 0}">
						<tr>
							<td class="text-center" colspan="6">Nessun risultato</td>
						</tr>
					</c:if>
					</tbody>
				</table>
				<c:if test="${requestScope.checkParam > 0}">
					<div class="text-center"><a href="${pageContext.servletContext.contextPath}/restricted/admin/ListCategory"><p>Torna alla lista</p></a></div>
				</c:if>
			</div>
		</div>
		<!--Images Modal -->
		<div class="modal fade" id="images" tabindex="-1" role="dialog" aria-labelledby="images" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">

				</div>
			</div>
		</div>
		<!--Load modal -->
		<div class="modal fade" id="load-image" tabindex="-1" role="dialog" aria-labelledby="load-image" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="load-image">Caricamento immagine</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<form  action="ImagesListCategory" method="POST" enctype='multipart/form-data'>
							<div class="custom-file my-2">
								<label class="custom-file-label" for="image">Scegli immagine</label>
								<input type="file" class="custom-file-input" id="image" name="image">
							</div>
							<div class="float-right mt-3">
								<a href="${pageContext.servletContext.contextPath}/restricted/admin/ListCategory" class="btn btn-light mr-2">Annulla</a>
								<button type="submit" class="btn btn-change float-right">Conferma</button>
							</div> 
						</form>
					</div>
				</div>
			</div>
		</div>

	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link href="${pageContext.servletContext.contextPath}/assets/css/list_category.css" type="text/css" rel="stylesheet"/>
		<style>
			.carousel-item {
				/**height: 100vh;**/
				min-height: 300px;
				background: no-repeat center center scroll;
				-webkit-background-size: cover;
				-moz-background-size: cover;
				-o-background-size: cover;
				background-size: cover;
			}
		</style>
	</jsp:attribute>
	<jsp:attribute name="customJs">
		<script>
			$(function () {
				$('#images').on('shown.bs.modal', function (e) {
					loadModalImages($(e.relatedTarget).data("category-id"));
				})
			})
			function loadModalImages(id) {
				console.log(id);
				$("#images .modal-content").load("${pageContext.servletContext.contextPath}/restricted/admin/ListCategory/Images?categoryId=" + id);
			}
		</script>
	</jsp:attribute>
</layouts:admin>