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
									<img class="cat-list-img" src="${pageContext.servletContext.contextPath}/assets/images/default.jpeg">
									<img class="cat-list-img" src="${pageContext.servletContext.contextPath}/assets/images/default.jpeg">
									<img class="cat-list-img" src="${pageContext.servletContext.contextPath}/assets/images/default.jpeg">
									<img class="cat-list-img" src="${pageContext.servletContext.contextPath}/assets/images/default.jpeg">
								<td class="td-handler">
									<a href="#images" data-toggle="modal" data-target="#images"><i class="fas fa-images"></i></a>
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
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="images">Immagini di categoria di lista</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="container-fluid" id="img-cat">
							<div class="row">
								<div class="col">
									<div class="mx-auto my-2 cat-image text-center">
										<a class="img-cat-link" href="${pageContext.servletContext.contextPath}/restricted/admin/ImagesListCategory">
											<div class="img-cat-hover">
												<div class="img-cat-hover-content">
													<i class="fas fa-plus fa-3x"></i>
												</div>
											</div>
											<img class="cat-list-img-modal" src="${pageContext.servletContext.contextPath}/assets/images/default.jpeg">
										</a>
									</div>
								</div>
								<div class="col">
									<div class="mx-auto my-2 cat-image text-center">
										<a class="img-cat-link" href="${pageContext.servletContext.contextPath}/restricted/admin/ImagesListCategory">
											<div class="img-cat-hover">
												<div class="img-cat-hover-content">
													<i class="fas fa-plus fa-3x"></i>
												</div>
											</div>
											<img class="cat-list-img-modal" src="${pageContext.servletContext.contextPath}/assets/images/default.jpeg">
										</a>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col">
									<div class="mx-auto my-2 cat-image text-center">
										<a class="img-cat-link" href="${pageContext.servletContext.contextPath}/restricted/admin/ImagesListCategory">
											<div class="img-cat-hover">
												<div class="img-cat-hover-content">
													<i class="fas fa-plus fa-3x"></i>
												</div>
											</div>
											<img class="cat-list-img-modal" src="${pageContext.servletContext.contextPath}/assets/images/default.jpeg">
										</a>
									</div>
								</div>
								<div class="col">
									<div class="mx-auto my-2 cat-image text-center">
										<a class="img-cat-link" href="${pageContext.servletContext.contextPath}/restricted/admin/ImagesListCategory">
											<div class="img-cat-hover">
												<div class="img-cat-hover-content">
													<i class="fas fa-plus fa-3x"></i>
												</div>
											</div>
											<img class="cat-list-img-modal" src="${pageContext.servletContext.contextPath}/assets/images/default.jpeg">
										</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</jsp:attribute>
	<jsp:attribute name="customCss">
		<style>
			.cat-list-img{
				width: 50px;
				height: 50px;
			}
			.cat-list-img-modal{
				width: 200px;
				height: 200px;
			}
			.td-images{
				width: 300px;
			}
			.td-handler{
				width: 150px;
			}
			#img-cat .cat-image {
				right: 0;
				margin-right: 20px;
				max-height: 200px;
				min-width: 200px;
				max-height: 200px;
				min-height: 200px;
			}

			#img-cat .cat-image .img-cat-link {
				position: relative;
				display: block;
				max-width: 400px;
				margin: 0 auto;
				cursor: pointer;
			}

			#img-cat .cat-image .img-cat-link .img-cat-hover {
				position: absolute;
				width: 100%;
				height: 100%;
				-webkit-transition: all ease 0.5s;
				transition: all ease 0.5s;
				opacity: 0;
				background: rgba(254, 209, 54, 0.9);
			}

			#img-cat .cat-image .img-cat-link .img-cat-hover:hover {
				opacity: 1;
			}

			#img-cat .cat-image .img-cat-link .img-cat-hover .img-cat-hover-content {
				font-size: 20px;
				position: absolute;
				top: 41%;
				width: 100%;
				height: 20px;
				margin-top: -12px;
				text-align: center;
				color: white;
			}

			#img-cat .cat-image .img-cat-link .img-cat-hover .img-cat-hover-content i {
				margin-top: -12px;
			}
			#img-cat .cat-image .img-cat-link .img-cat-hover .img-cat-hover-content p {
				margin-bottom: 0;
			}

			#img-cat .cat-image .img-cat-link .img-cat-hover .img-cat-hover-content h3,
			#img-cat .cat-image .img-cat-link .img-cat-hover .img-cat-hover-content h4 {
				margin: 0;
			}

			#img-cat * {
				z-index: 2;
			}
			#img-cat{
				display: block;
			}
		</style>
	</jsp:attribute>
	<jsp:attribute name="customJs">

	</jsp:attribute>
</layouts:admin>