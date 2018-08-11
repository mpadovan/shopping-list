<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav id="sidebar">
    <div class="sidebar-header">
        <h3><a href="InfoUser.jsp"><img src="${pageContext.servletContext.contextPath}/assets/images/avatar.png" alt="Nome Cognome" class="rounded-circle img-fluid user-image"></a></h3>
        <h6>Nome Cognome</h6>
    </div>

    <ul class="list-unstyled components">
        <li>
            <span>
				<b> Liste personali</b>
				<a href="NewPersonalList.jsp" style="display: inline-block"><i class="fas fa-plus"></i></a>
			</span>
            <ul class="list-unstyled scrollable-menu" id="personalList">
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
                <li>
                    <a href="#">Lista 5</a>
                </li>
                <li>
                    <a href="#"></a>
                </li>
            </ul>
        </li>

        <c:if test="${pTitle == 'Landing Page'}">
            <li>
                <span>
					<b> Liste condivise</b>
					<a href="NewSharedList.jsp" style="display: inline-block"><i class="fas fa-plus"></i></a>
				</span>
                <ul class="list-unstyled scrollable-menu" id="shareList">
                    <li class="active">
                        <a href="#">Supermercato</a>
                    </li>
                </ul>
            </li>
        </c:if>
    </ul>
    <div class="d-flex justify-content-end">
        <a href="SignUp.jsp" class="btn btn_sidenav btn-outline-dark mr-sm-1">Registrati</a>
        <a href="Login.jsp" class="btn btn_sidenav btn-outline-dark mr-sm-1">Accedi</a>
        <a href="Logout" class="btn btn_sidenav btn-outline-dark"><i class="fas fa-sign-out-alt"></i></a>
    </div>

</nav>
