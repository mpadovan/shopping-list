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
			<a href="${pageContext.servletContext.contextPath}/restricted/InfoUser"><h6>${sessionScope.user.name} ${sessionScope.user.lastname}</h6></a>
			<a href="${pageContext.servletContext.contextPath}/restricted/Logout"><span style="font-size: 15px;">Logout </span><i class="fas fa-sign-out-alt"></i></a>
		</div>
	</div>

    <div class="sidebar-body">
		<div>
			<a href="${pageContext.servletContext.contextPath}/restricted/Products"><p  class="font-weight-bold p-products-sidebar">I tuoi prodotti</p></a>
			<a href="${pageContext.servletContext.contextPath}/restricted/NewSharedList"><p class="p-new-sidebar"><i class="fas fa-plus-circle"></i> Nuova Lista</p></a>
		</div>
		<ul class="list-unstyled components">
			<c:if test="${!empty sessionScope.personalLists}">
			<li>
				<span class="font-weight-bold" style="font-size: 20px;">
					 Liste personali
				</span>
				<div class="divider"></div>
				<ul class="list-unstyled scrollable-menu" id="personalList" style="font-family: sans-serif;">
					<c:forEach items="${sessionScope.personalLists}" var="list">
						<li>
						<a href="${pageContext.servletContext.contextPath}/restricted/HomePageLogin/${sessionScope.user.id}/${list.id}">${list.name}</a>
					</li>
					</c:forEach>
				</ul>
			</li>
			</c:if>
			<c:if test="${!empty sessionScope.sharedLists}">
			<li>
				<span class="font-weight-bold" style="font-size: 20px;">
					Liste condivise
				</span>
				<div class="divider"></div>
				<ul class="list-unstyled scrollable-menu" id="shareList">
					<c:forEach items="${sessionScope.sharedLists}" var="list">
						<li>
						<a href="${pageContext.servletContext.contextPath}/restricted/HomePageLogin/${sessionScope.user.id}/${list.id}">${list.name}</a>
					</li>
					</c:forEach>
				</ul>
			</li>
			</c:if>
		</ul>
	</div>

</nav>
