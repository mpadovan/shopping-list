<%-- 
    Document   : ListCategoryImages
    Created on : 15-ott-2018, 10.45.02
    Author     : giulia
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="modal-header">
	<h5 class="modal-title" id="images">Immagini di categoria di lista</h5>
	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
</div>
<div class="modal-body">
	<header>
		<div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
			<ol class="carousel-indicators">
				<c:forEach var="i" begin="0" end="${fn:length(requestScope.images)-1}">
					<li data-target="#carouselExampleIndicators" data-slide-to="${i}" <c:if test="${i==0}">class="active"</c:if>></li>
					</c:forEach>
			</ol>
			<div class="carousel-inner" role="listbox">
				<c:forEach var="i" varStatus="j" items="${requestScope.images}">
					<div class="carousel-item <c:if test="${j.index == 0}">active</c:if>"
						 style="background-image: url('${pageContext.servletContext.contextPath}${i.image}')">
						<div class="carousel-caption d-none d-md-block">
							<a href="#edit-${i.id}" class="btn btn-light" data-toggle="modal" data-target="#edit-${i.id}">Modifica</a>
							<a href="#" class="btn btn-light">Elimina</a>
						</div>
							<form class="form-control" action="ListCategoryServlet" method="POST" enctype='multipart/form-data'>
								<input name="categoryId" value="${param}" hidden="true">
								<input name="imageId" value="${i.id}" hidden="true">
							<div class="custom-file">
								<input type="file"
									   class="custom-file-input form-control"
									   id="image"
									   name="image"
									   aria-describedby="image">
								<label class="custom-file-label" for="image">Scegli file</label>
							</div>
						</form>
					</div>
					<!-- Modal 
					<div class="modal fade" id="edit-${i.id}" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
						<div class="modal-dialog modal-dialog-centered" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="edit-${i.id}">Modal title</h5>
									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">

								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
									<button type="button" class="btn btn-primary">Save changes</button>
								</div>
							</div>
						</div>
					</div> -->
				</c:forEach>
				<div class="carousel-item" style="background-image: url('http://placehold.it/1900x1080')">
					<div class="carousel-caption d-none d-md-block">
						<a href="#" class="btn btn-light">Aggiungi</a>
					</div>
				</div>
			</div>
			<a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
				<span class="carousel-control-prev-icon" aria-hidden="true"></span>
				<span class="sr-only">Previous</span>
			</a>
			<a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
				<span class="carousel-control-next-icon" aria-hidden="true"></span>
				<span class="sr-only">Next</span>
			</a>
		</div>

	</header>
</div>