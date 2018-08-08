<%-- 
    Document   : HomePage
    Created on : 21-apr-2018, 13.26.36
    Author     : giuliapeserico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:empty pageTitle="Landing Page Anonimus">
    <jsp:attribute name="pageContent">
        <div class="container-fluid">
			<div class="row justify-content-center">
				<div class="col-10 mt-5">
					<h1 class="text-center mt-3">Prova una lista</h1>
					<h3 class="text-center mt-3">Per avere tutte le funzionalità:</h3>
					<div class="row justify-content-center">
						<div class="col-2 mt-3">
							<jsp:include page="./partials/LoginLogoutPartial.jsp"></jsp:include>
						</div>	
					</div>
				</div>
			</div>
            <div class="row justify-content-center">
                <div class="col-9 mt-4" id="app">
                    <div class="card">
                        <div class="card-body">
                            <div class="input-group mb-3">
                                <input type="text" class="form-control" v-bind:placeholder="msg" v-on:keyup="searching" v-model="query">
                                <div class="input-group-append">
                                    <button class="btn btn-outline-secondary" type="button"><i class="fas fa-search"></i></button>
                                </div>
                            </div>
                            <transition name="fade" v-on:after-leave="searchHided">
                                <div class="list-group" v-if="showSearch">
                                    <ul class="search-results list-group list-group-flush">
                                        <li v-for="result in results" class="list-group-item">{{ _.capitalize(result.name) }}</li>
                                    </ul>
                                </div>
                            </transition>
                        </div>
                    </div>
					<transition name="fade" v-on:after-leave="listHided">
						<div class="card" id="list" v-if="showList">
							<div class="card-body" >
								<table class="table table-striped">
									<thead>
										<tr>
											<th scope="col">Nome prodotto</th>
											<th scope="col">Quantità</th>
											<th scope="col"></th>
											<th scope="col"></th>
										</tr>
									</thead>
									<tbody>
										<tr is="list-item"
											v-for='item in items'
											v-bind:key='item.name'
											v-bind:name='item.name'
											v-bind:amount='item.amount'
										></tr>
									</tbody>
								</table>

							</div>

						</div>
					</transition>

				</div>

			</div>

		</div>
	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link href="assets/css/landing_page.css" type="text/css" rel="stylesheet"/>
	</jsp:attribute>
	<jsp:attribute name="customJs">
		<script src="assets/js/landing_page.js"></script>
	</jsp:attribute>

</layouts:empty>
