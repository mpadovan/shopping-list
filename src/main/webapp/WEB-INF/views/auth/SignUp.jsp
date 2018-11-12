<%--
    Document   : SignUp
    Created on : 10-lug-2018, 10.34.38
    Author     : giuliapeserico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared/" %>
<layouts:auth pageTitle="Sign up">
	<jsp:attribute name="pageContent">
		<div class="cointainer-fluid px-2">
			<div class="card signUp-card">
				<div class="card-body">
					<c:if test="${!empty user.errors}">
						<div class="alert alert-danger" role="alert">
							<h4 class="alert-heading">I dati inseriti non sono validi.</h4>
							<p>Controlla i campi sottostanti.</p>
						</div>
					</c:if>
					<div class="text-center mb-4">
						<h3 class="mb-3 font-weight-normal">Registrazione</h3>
					</div>
					<form class="form-signin" action="SignUp" method="POST" enctype='multipart/form-data'>
						<div class="form-label-group">
							<input type="text"
								   id="name"
								   name="name"
								   class="form-control ${(user.getFieldErrors("name") != null ? "is-invalid" : "")}"
								   placeholder="Nome"
								   maxlength="40"
								   required>
							<label for="name">Nome</label>
							<div class="invalid-feedback">
								<shared:fieldErrors entity="${user}" field="name" />
							</div>
						</div>
						<div class="form-label-group">
							<input type="text" 
								   id="lastName"
								   name="lastName"
								   class="form-control ${(user.getFieldErrors("lastname") != null ? "is-invalid" : "")}"
								   placeholder="Cognome"
								   maxlength="40"
								   required>
							<label for="lastName">Cognome</label>
							<div class="invalid-feedback">
								<shared:fieldErrors entity="${user}" field="lastname" />
							</div>
						</div>
						<div class="form-label-group mt-3">
							<input type="email" 
								   id="email"
								   name="email"
								   aria-describedby="emailHelp"
								   class="form-control ${(user.getFieldErrors("email") != null ? "is-invalid" : "")}"
								   placeholder="Email"
								   maxlength="40"
								   required>
							<label for="email">Email</label>
							<div class="invalid-feedback">
								<shared:fieldErrors entity="${user}" field="email" />
							</div>
						</div>
						<div class="form-label-group">
							<input type="password" id="password" name="password" class="form-control ${(user.getFieldErrors("password") != null ? "is-invalid" : "")}" placeholder="Password" required>
							<label for="password">Password</label>
							<div class="invalid-feedback">
								<shared:fieldErrors entity="${user}" field="password" />
							</div>
							<div id="passwordStrength" class="progress d-none" style="height: 13pt; margin-top: 4pt;">
								<div id="progressBar" name="progressBar" class="progress-bar bg-danger" role="progressbar" style="width: 33.3%">bad</div>
							</div>
						</div>
						<div class="form-label-group">
							<input type="password" id="checkPassword" name="checkPassword" class="form-control ${(user.getFieldErrors("checkpassword") != null ? "is-invalid" : "")}" placeholder="Conferma password" required>
							<label for="checkPassword">Conferma password</label>
							<div class="invalid-feedback">
								<shared:fieldErrors entity="${user}" field="checkpassword" />
							</div>
						</div>
						<div class="form-group">
							<div class="form-check">
								<input class="form-check-input ${ requestScope.privacy != null ? "is-invalid" : ""}"
									   type="checkbox" value="privacy" name="privacy" id="privacy" required >
								<label class="form-check-label" for="privacy">
									<a href="${pageContext.servletContext.contextPath}/assets/privacy.html">Normativa privacy</a>
								</label>
								<div class="invalid-feedback">
									${requestScope.privacy}
								</div>
							</div>
						</div>
						<button type="submit" class="btn btn-lg btn-block form-signin-btn">Registrati</button>
						<div class="mt-3">
							<a href="${pageContext.servletContext.contextPath}/home">Indietro</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</jsp:attribute>
	<jsp:attribute name="customJs">
		<script>
			var weakTh = 30,goodTh = 60, strongTh = 80;		//Thresholds: weak,good or strong password; is valid when is good or strong
			$(document).ready(function () {
				var state=0;
				setTimeout(function () {
					var $Input = $('input:-webkit-autofill');
					$Input.next("label").addClass('active');
				}, 100);
				$("#password").on("input", function() {
					var pass = $(this).val();
					if((pass == null || pass.length == 0) && state === 1){
						$("#progressBar").removeClass("from0to33").removeClass("from66to33").addClass("from33to0");
						$("#passwordStrength").removeClass("zoomIn").addClass("zoomOut");
						state = 0;
					}
					if(state === 0 && pass.length > 0){
						$("#passwordStrength").removeClass("d-none").removeClass("zoomOut").addClass("zoomIn");
						$("#progressBar").removeClass("from33to0").addClass("from0to33");
						state = 1;
					}
					var passStrength = checkPassStrength(pass);
					var passScore = scorePassword(pass);
					//console.log(passScore);
					$("#progressBar").html(passStrength).val(passScore);
					if(passScore <= goodTh && state===2){
						$("#progressBar").removeClass("from100to66").removeClass("from33to66").removeClass("bg-warning").addClass("from66to33").addClass("bg-danger").width("33.3%");
						state=1;
					}
					else if(passScore > goodTh && passScore<=strongTh){
						if(state===1)
							$("#progressBar").removeClass("bg-danger").addClass("from33to66").addClass("bg-warning").width("66.6%").removeClass("from0to33").removeClass("from66to33");
						else if (state === 3)
							$("#progressBar").addClass("from100to66").addClass("bg-warning").width("66.6%").removeClass("from66to100").removeClass("bg-success");
						state=2;
					}
					else if(passScore > strongTh && state === 2){
						$("#progressBar").removeClass("bg-warning").addClass("from66to100").addClass("bg-success").width("100%").removeClass("from100to66").removeClass("from33to66");
						state=3;
					}
				});
			});
			
			function scorePassword(pass) {
				var score = 0;
				if (!pass)
					return score;

				// award every unique letter until 5 repetitions
				var letters = new Object();
				for (var i=0; i<pass.length; i++) {
					letters[pass[i]] = (letters[pass[i]] || 0) + 1;
					score += 5.0 / letters[pass[i]];
				}

				// bonus points for mixing it up
				var variations = {
					digits: /\d/.test(pass),
					lower: /[a-z]/.test(pass),
					upper: /[A-Z]/.test(pass),
					nonWords: /\W/.test(pass),
				}

				variationCount = 0;
				for (var check in variations) {
					variationCount += (variations[check] == true) ? 1 : 0;
				}
				score += (variationCount - 1) * 10;

				return parseInt(score);
			}

			function checkPassStrength(pass) {
				var score = scorePassword(pass);
				if (score > strongTh)
					return "forte";
				if (score > goodTh)
					return "buona";
				if (score >= weakTh)
					return "debole";

				return "cattiva";
			}

		</script>
	</jsp:attribute>
</layouts:auth>
