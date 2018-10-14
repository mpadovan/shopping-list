<%-- 
    Document   : EditAdmin
    Created on : 21-ago-2018, 19.53.27
    Author     : giulia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared/" %>

<layouts:admin pageTitle="Profile">
    <jsp:attribute name="pageContent">
		<div class="cointainer-fluid px-2">
			<div class="card edit-user-card">
				<div class="card-body">
					<c:if test="${!empty user.errors}">
						<div class="alert alert-danger" role="alert">
							<h4 class="alert-heading">I dati inseriti non sono validi.</h4>
							<p>Controlla i campi sottostanti.</p>
						</div>
					</c:if>
					<div class="text-center mb-4">
						<h3 class="mb-3 font-weight-normal">Modifica profilo</h3>
					</div>
					<form class="form-user" action="EditAdmin" method="POST">
						<div class="form-group">
							<label for="name">Nome</label>
							<input type="text" 
								   id="name"
								   name="name"
								   value="${sessionScope.user.name}"
								   class="form-control ${(user.getFieldErrors("name") != null ? "is-invalid" : "")}"
								   maxlength="40"
								   required>
							<div class="invalid-feedback">
								<shared:fieldErrors entity="${user}" field="name" />
							</div>
						</div>
						<div class="form-group">
							<label for="lastName">Cognome</label>
							<input type="text" 
								   id="lastName"
								   name="lastName"
								   value="${sessionScope.user.lastname}"
								   class="form-control ${(user.getFieldErrors("lastname") != null ? "is-invalid" : "")}"
								   maxlength="40"
								   required>
							<div class="invalid-feedback">
								<shared:fieldErrors entity="${user}" field="lastname" />
							</div>
						</div>
						<div class="form-group">
							<label for="email">Email</label>
							<input type="email"readonly
								   class="form-control"
								   id="email"
								   name="email"
								   value="${sessionScope.user.email}"
								   maxlength="40"
								   required>
						</div>
						<c:if test="${requestScope.changepassword == true}">
							<div class="form-group" id="divOldPassword">
								<label for="password">Password attuale</label>
								<input type="password"
									   id="oldPassword"
									   name="oldpassword"
									   class="form-control ${(user.getFieldErrors("oldpassword") != null ? "is-invalid" : "")}">
								<div class="invalid-feedback">
									<shared:fieldErrors entity="${user}" field="oldpassword" />
								</div>
							</div>
							<div class="form-group" id="divPassword">
								<label for="password">Nuova password</label>
								<input type="password" 
									   id="password" 
									   name="password" 
									   class="form-control ${(user.getFieldErrors("password") != null ? "is-invalid" : "")}">
								<div class="invalid-feedback">
									<shared:fieldErrors entity="${user}" field="password" />
								</div>
							</div>
							<div class="form-group" id="divCheckPassword">
								<label for="checkpassword">Nuova password</label>
								<input type="password" 
									   id="checkpassword" 
									   name="checkpassword" 
									   class="form-control ${(user.getFieldErrors("checkpassword") != null ? "is-invalid" : "")}">
								<div class="invalid-feedback ">
									<shared:fieldErrors entity="${user}" field="checkpassword" />
								</div>
							</div>
						</c:if>
						<div class="mt-4">
							<button type="submit" class="btn btn-new float-right mx-2">Conferma</button>
							<c:if test="${not requestScope.changepassword}">
								<a href="${pageContext.servletContext.contextPath}/restricted/admin/EditAdmin?changepassword=true" class="btn btn-light float-right mx-2" id="btnLock">Password <i class="fas fa-lock"></i></a>
							</c:if>
							<c:if test="${requestScope.changepassword == true}">
								<a href="${pageContext.servletContext.contextPath}/restricted/admin/EditAdmin" class="btn btn-light float-right mx-2" id="btnUnLock"> <i class="fas fa-unlock"></i></a>
								</c:if>
							<div id="btn-back-user">
								<a href="${pageContext.servletContext.contextPath}/restricted/admin/InfoAdmin" class="btn btn-light"><i class="fas fa-chevron-left"></i> Indietro</a>
							</div>
							<div id="btn-back-user-res">
								<a href="${pageContext.servletContext.contextPath}/restricted/admin/InfoAdmin" class="btn btn-light"><i class="fas fa-chevron-left"></i></a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link href="${pageContext.servletContext.contextPath}/assets/css/info_admin.css" type="text/css" rel="stylesheet"/>
	</jsp:attribute>
</layouts:admin>

