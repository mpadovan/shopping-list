<%-- 
    Document   : HomePage
    Created on : 21-apr-2018, 13.26.36
    Author     : giuliapeserico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:base pageTitle="Landing Page Anonimus">
    <jsp:attribute name="pageContent">
        <div class="container-fluid">
            <div class="row">
                <div class="col" id="app">
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
								<h5 class="card-title text-center">Lista corrente: <a href="InfoList.jsp"><u>Supermercato</u></a></h5>
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
										<tr data-item="A00">
											<td>Latte Zymil</td>
											<td>1</td>
											<td><a href="#"><i class="fas fa-pen-square"></i></a></td>
											<td><a href="#"><i class="fas fa-trash"></i></a></td>
										</tr>
										<tr data-item="A01">
											<td>Biscotti</td>
											<td>2</td>
											<td><a href="#"><i class="fas fa-pen-square"></i></a></td>
											<td><a href="#"><i class="fas fa-trash"></i></a></td>
										</tr>
										<tr data-item="A02">
											<td>Detersivo</td>
											<td>1</td>
											<td><a href="#"><i class="fas fa-pen-square"></i></a></td>
											<td><a href="#"><i class="fas fa-trash"></i></a></td>
										</tr>
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

</layouts:base>
