<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav id="sidebar" style="overflow-y: scroll;">
	<div id="dismiss">
		<i class="fas fa-arrow-left"></i>
	</div>
    <div class="sidebar-header" id="header">
		<div class="text-center">
			<img src="${pageContext.servletContext.contextPath}/assets/images/avatar2.png" class="rounded-circle img-fluid user-image" alt="immagine profilo">
		</div>
		<div class="text-center div-info-user">
			<h6>${sessionScope.user.name} ${sessionScope.user.lastname}</h6>
			<a href="${pageContext.servletContext.contextPath}/restricted/Logout"><span style="font-size: 15px;">Logout </span><i class="fas fa-sign-out-alt"></i></a>
		</div>
	</div>

    <div class="sidebar-body">
		<div>
			<a href="#"><p style="margin: 0 20px; margin-bottom: 20px; color: whitesmoke;" class="font-weight-bold">I tuoi prodotti</p></a>
			<a href="#"><p class="p-sidebar" style="margin: 0 20px;">Nuova Lista <i class="fas fa-plus-circle"></i></p></a>
		</div>
		<ul class="list-unstyled components">
			<li>
				<span style="font-size: 25px;">
					<b> Liste personali</b>
				</span>
				<ul class="list-unstyled scrollable-menu" id="personalList" style="font-family: sans-serif;">
					<li>
						<a href="#">Ferramenta</a>
					</li>
					<li>
						<a href="#">Farmacia</a>
					</li>
					<li>
						<a href="#">Unieuro</a>
					</li>
					<li>
						<a href="#">Lista 1</a>
					</li>
					<li>
						<a href="#">Lista 2</a>
					</li>
					<li>
						<a href="#">Lista 3</a>
					</li>
					<li>
						<a href="#">Lista 4</a>
					</li>
				</ul>
			</li>
			<li>
				<span style="font-size: 25px;">
					<b> Liste condivise</b>
				</span>
				<ul class="list-unstyled scrollable-menu" id="shareList">
					<li>
						<a href="#">Supermercato</a>
					</li>
					<li>
						<a href="#">Lista 3</a>
					</li>
					<li>
						<a href="#">Lista 4</a>
					</li>
					<li>
						<a href="#">Lista 5</a>
					</li>
					<li>
						<a href="#">Lista 6</a>
					</li>
					<li>
						<a href="#">Lista 7</a>
					</li>
				</ul>
			</li>
		</ul>
	</div>

</nav>
