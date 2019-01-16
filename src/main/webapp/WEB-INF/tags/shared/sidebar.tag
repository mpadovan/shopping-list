<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav id="sidebar" style="overflow-y: scroll;">
	<div id="dismiss">
		<i class="fas fa-arrow-left"></i>
	</div>
    <div class="sidebar-header" id="header">
		<div class="text-center">
			<c:if test="${not empty sessionScope.user.image}">
				<img style="width: 300px; height: 300px;"src="${pageContext.servletContext.contextPath}${sessionScope.user.image}" class="rounded-circle img-fluid user-image" alt="immagine profilo">
			</c:if>
			<c:if test="${empty sessionScope.user.image}">
				<img style="width: 300px; height: 300px;"src="${pageContext.servletContext.contextPath}/assets/images/avatar2.png" class="rounded-circle img-fluid user-image" alt="immagine profilo" title="Immagine profilo">
			</c:if>
		</div>
		<div class="text-center div-info-user">
			<a href="${pageContext.servletContext.contextPath}/restricted/InfoUser"><h5 class="h5-user-name">${sessionScope.user.name} ${sessionScope.user.lastname}</h5></a>
			<a href="${pageContext.servletContext.contextPath}/restricted/Logout"><p><span class="p-size">Logout </span><i class="fas fa-sign-out-alt"></i></p></a>
				<c:if test="${sessionScope.user.administrator}">
				<a href="${pageContext.servletContext.contextPath}/restricted/admin/PublicProductList"><p><span class="p-size">Amministrazione</span></p></a>
				</c:if>
		</div>
	</div>

    <div class="sidebar-body">
		<div>
			<a href="${pageContext.servletContext.contextPath}/restricted/ProductList"><p  class="font-weight-bold p-products-sidebar">I tuoi prodotti</p></a>
			<a href="${pageContext.servletContext.contextPath}/restricted/NewList"><p class="p-new-sidebar"><i class="fas fa-plus-circle"></i> Nuova Lista</p></a>
		</div>
		<ul class="list-unstyled components">
			<c:if test="${!empty requestScope.personalLists}">
				<li>
					<span class="font-weight-bold" style="font-size: 20px;">
						Liste personali
					</span>
					<div class="divider"></div>
					<ul class="list-unstyled scrollable-menu" id="personalList" style="font-family: sans-serif;">
						<c:forEach items="${requestScope.personalLists}" var="list">
							<li id="personal-list-${list.id}">
								<a href="${pageContext.servletContext.contextPath}/restricted/HomePageLogin/${sessionScope.user.hash}/${list.hash}">${list.name}</a>
							</li>
						</c:forEach>
					</ul>
				</li>
			</c:if>
			<c:if test="${!empty requestScope.sharedLists}">
				<li>
					<span class="font-weight-bold" style="font-size: 20px;">
						Liste condivise <span id="new-messages" class="badge badge-danger" style="display: none;"><i class="far fa-envelope"></i></span>
					</span>
					<div class="divider"></div>
					<ul class="list-unstyled scrollable-menu" id="shareList">
						<c:forEach items="${requestScope.sharedLists}" var="list">
							<li>
								<a id="shared-list-outer-${list.id}" href="${pageContext.servletContext.contextPath}/restricted/HomePageLogin/${sessionScope.user.hash}/${list.hash}"><span id="shared-list-${list.id}" class="badge badge-danger" style="width: 20px; height: 20px;"></span>${list.name}</a>
							</li>
						</c:forEach>
					</ul>
				</li>
			</c:if>
		</ul>
	</div>

</nav>
