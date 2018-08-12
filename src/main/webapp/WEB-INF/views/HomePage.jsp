<%-- 
    Document   : HomePage
    Created on : 21-apr-2018, 13.26.36
    Author     : giuliapeserico
--%>

	<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
		<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

			<layouts:empty pageTitle="Landing Page Anonimus">
				<jsp:attribute name="pageContent">
					<div class="container-fluid" id="app">
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
							<div class="col-9 mt-4">
								<div class="card">
									<div class="card-body">
										<div class="input-group mb-0">
											<input type="text" class="form-control" v-bind:placeholder="msg" v-model="query" @keyup.enter="searching" id="search-input">
											<div class="input-group-append">
												<button class="btn btn-outline-secondary" type="button" @click="searching">
													<i class="fas fa-search"></i>
												</button>
											</div>
										</div>
										<div class="p-1 pt-3 pb-2 autocomplete" v-show="showAutocomplete">
											<li class="pointer autocomplete-li" style="padding-left: .5rem;" v-for='item in autocompleteList' v-bind:key='item.name' @click="replaceQuerySearch(item.name)">{{ item.name }}</li>
										</div>
										<transition name="fade" v-on:after-leave="searchHided">
											<div class="list-group" v-if="showSearch">
												<nav class="navbar navbar-dark bg-primary mt-3">
												  	<button @click="hideSearch" type="button" class="btn btn-outline-light">Torna alla lista</button>
													<div class="form-group" style="margin-bottom:0;">
      													<select class="form-control" v-model="selected">
															<option value="all">Tutte le categorie</option>
															<option v-for="searchCategory in searchCategories">{{ searchCategory }}</option>
      													</select>
													</div>
												</nav>
												<div id="row justify-content-center" v-show="noResults">
													<div id="col mt-2 text-center">
														Non troviamo nulla che soddisfi la tua ricerca ¯\_(ツ)_/¯
													</div>
												</div>
												<ul class="search-results list-group list-group-flush">
													<search-item v-for="result in resultsSorted" v-bind:key="result.id + result.name" v-bind:id="result.id" v-bind:name="result.name" v-bind:category="result.category" @add="addItemToList" class="search-result pointer"></search-item>
												</ul>
											</div>
										</transition>
									</div>
								</div>
								<transition name="fade" v-on:after-leave="listHided">
									<div class="card" id="list" v-if="showList">
										<div class="card-body">
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
													<tr is="list-item" v-for='item in items' v-bind:key='item.id + item.name' v-bind:id="item.id" v-bind:name='item.name' v-bind:amount='item.amount' @update="updateWithModal"
													    @delete="deleteWithModal"></tr>
												</tbody>
											</table>
										</div>
									</div>
								</transition>
							</div>
						</div>
						<component v-bind:is="searchInitializing" @search="addResultsToIstance" v-bind:url="url"></component>
						<div id="item-modal" class="modal" tabindex="-1" role="dialog">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<h5 v-show="updatingItem" class="modal-title">Modifica {{ item_name }}</h5>
										<h5 v-show="!updatingItem" class="modal-title">Elimina {{ item_name }}</h5>
										<button type="button" class="close" data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
									<div class="modal-body">
										<p v-show="updatingItem">Modifica la quantità di {{ item_name }}
											<input type="number" name="item-amount" id="item-amount" v-model="item_amount"
											    min="1">
										</p>
										<p v-show="!updatingItem">Elimina dalla lista {{ item_name }}</p>
									</div>
									<div class="modal-footer">
										<button v-show="updatingItem" type="button" class="btn btn-primary" @click="updateComponent" data-dismiss="modal">Update</button>
										<button v-show="!updatingItem" type="button" class="btn btn-primary" @click="deleteComponent" data-dismiss="modal">Delete</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</jsp:attribute>
				<jsp:attribute name="customCss">
					<link href="assets/css/landing_page.css" type="text/css" rel="stylesheet" />
				</jsp:attribute>
				<jsp:attribute name="customJs">
					<script src="assets/js/landing_page.js"></script>
				</jsp:attribute>

			</layouts:empty>