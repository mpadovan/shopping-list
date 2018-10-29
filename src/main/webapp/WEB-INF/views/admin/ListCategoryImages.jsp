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
				<c:if test="${not empty requestScope.images}">
					<c:forEach var="i" begin="0" end="${fn:length(requestScope.images)-1}">
						<li data-target="#carouselExampleIndicators" data-slide-to="${i}" <c:if test="${i==0}">class="active"</c:if>></li>
						</c:forEach>
					</c:if>
					<c:if test="${empty requestScope.images}">
					<li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
					</c:if>
			</ol>
			<div class="carousel-inner" role="listbox">
				<c:if test="${empty requestScope.images}">
					<div class="carousel-item active" style="background-image: url('http://placehold.it/1900x1080')">
						<div class="carousel-caption d-none d-md-block">
							<button id="btn-add" onclick="newImage()" class="btn btn-light">Aggiungi</button>
							<button id="btn-back" onclick="newImage()" class="btn btn-light btn-hide">Indietro</button>
						</div>
						<form id="form-new-file"class="form-control choose-file-none" action="NewListCategoryImage" method="POST" enctype='multipart/form-data'>
							<input name="categoryId" value="${param.categoryId}" hidden="true">
							<input name="imageId" value="${i.id}" hidden="true">
							<div class="input-group">
								<div class="custom-file">
									<input type="file"
										   class="custom-file-input form-control"
										   id="image"
										   name="image"
										   aria-describedby="image">
									<label class="custom-file-label" for="image">Scegli file</label>
								</div>
								<div class="input-group-append">
									<button type="submit" class="btn btn-primary">Salva</button>
								</div>
							</div>
						</form>
					</div>
				</c:if>
				<c:if test="${not empty requestScope.images}">
					<c:forEach var="i" varStatus="j" items="${requestScope.images}">
						<div class="carousel-item <c:if test="${j.index == 0}">active</c:if>"
							 style="background-image: url('${pageContext.servletContext.contextPath}${i.image}')">
							<div>
								<form id="form-file-${i.id}"class="form-control choose-file-none form-file" action="ListCategoryServlet" method="POST" enctype='multipart/form-data'>
									<input name="imageId" value="${i.id}" hidden="true">
									<div class="input-group">
										<div class="custom-file">
											<input type="file"
												   class="custom-file-input form-control"
												   id="image"
												   name="image"
												   aria-describedby="image">
											<label class="custom-file-label" for="image">Scegli file</label>
										</div>
										<div class="input-group-append">
											<button type="submit" class="btn btn-primary">Salva</button>
										</div>
									</div>
								</form>
							</div>
							<div class="carousel-caption d-none d-md-block">
								<button id="btn-back-${i.id}" onclick="editImage(${i.id})"class="btn btn-light btn-hide">Indietro</button>
								<button id="edit-btn-${i.id}" onclick="editImage(${i.id})" class="btn btn-light">Modifica</button>
								<a href="#" id="delete-btn-${i.id}" class="btn btn-danger">Elimina</a>
							</div>
						</div>
					</c:forEach>
					<div class="carousel-item" style="background-image: url('http://placehold.it/1900x1080')">
						<div>
							<form id="form-new-other-file" class="form-control choose-file-none" action="NewListCategoryImage" method="POST" enctype='multipart/form-data'>
								<input name="categoryId" value="${param.categoryId}" hidden="true">
								<input name="imageId" value="${i.id}" hidden="true">
								<div class="input-group">
									<div class="custom-file">
										<input type="file"
											   class="custom-file-input form-control"
											   id="image"
											   name="image"
											   aria-describedby="image">
										<label class="custom-file-label" for="image">Scegli file</label>
									</div>
									<div class="input-group-append">
										<button type="submit" class="btn btn-primary">Salva</button>
									</div>
								</div>
							</form>
						</div>
						<div class="carousel-caption d-none d-md-block">
							<button id="btn-other-add" onclick="newOtherImage()" class="btn btn-light">Aggiungi</button>
							<button id="btn-other-back" onclick="newOtherImage()" class="btn btn-light btn-hide">Indietro</button>
						</div>
					</div>
				</c:if>
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