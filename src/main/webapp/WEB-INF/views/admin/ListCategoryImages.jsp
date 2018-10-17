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
							<h3>First Slide</h3>
							<p>This is a description for the first slide.</p>
						</div>
					</div>
				</c:forEach>
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