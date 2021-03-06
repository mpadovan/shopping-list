<%-- 
    Document   : HomePage
    Created on : 21-apr-2018, 13.26.36
    Author     : giuliapeserico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:empty pageTitle="">
	<jsp:attribute name="pageContent">
		<noscript>
			<h1>Questo sito funziona con JS, attivalo per utilizzare il sito</h1>
		</noscript>
		<div id="app">
			<div class="section home">
				<div class="intro_top_home">
					<div class="inner_home">
						<h1 style="margin-bottom: 0;" class="display-3 text-center">Ciao! Inizia ad usare la tua prima lista!<br>Scorri in giù per iniziare</h1>
						<div class="row justify-content-center">
							<div class="col-2 mt-3">
								<jsp:include page="./partials/LoginLogoutPartial.jsp"></jsp:include>
								</div>
							</div>
						</div>
					</div>					
				</div>
				<div class="container-fluid section" style="background-image: linear-gradient(rgba(127, 210, 236, 1), rgba(30, 136, 214, 1));">
					<transition name="fade">
						<div class="row justify-content-center" v-show="loaded_list" style="display: none;">
							<div class="col-md-10 align-self-center">
								<div class="card">
									<div class="card-header">
										<h4 style="text-align: center;">{{listTitle}}</h4>
										<div class="input-group mb-0">
											<input type="text" class="form-control" v-bind:placeholder="msg" v-model="query" @keyup.enter="searching" id="search-input">
											<div class="input-group-append">
												<button class="btn btn-outline-secondary" type="button" @click="searching">
													<i class="fas fa-search"></i>
												</button>
											</div>
										</div>
										<div class="p-1 pt-3 pb-2 autocomplete" v-if="showAutocomplete">
											<li v-bind:id="'item' + item.sid" class="pointer autocomplete-li" style="padding-left: .5rem;" v-for='item in autocompleteComputed' v-bind:key='item.name' @click="replaceQuerySearch(item.name)">{{ item.name }}</li>
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
												<div class="search-results row">
													<search-item v-for="result in resultsSorted" v-bind:key="result.name" v-bind:item="result" @add="addItemToList" @info="infoItemOnModal" class="search-result pointer"></search-item>
												</div>
											</div>
										</transition>
									</div>
									<transition name="fade" v-on:after-leave="listHided">
										<div id="list" v-if="showList">
											<div class="card-body">
												<div class="table-wrapper-2 table-responsive-md">
													<table class="table table-striped">
														<thead>
															<tr>
																<th scope="col">Nome prodotto</th>
																<th scope="col">Quantità</th>
																<th scope="col" colspan="3">Gestisci</th>
															</tr>
														</thead>
														<tbody>
															<tr is="list-item" v-for='item in items' v-bind:key='item.item.name + item.item.id' v-bind:item="item" @update="updateWithModal"
																@delete="deleteWithModal" @info="infoItemOnModal"></tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</transition>
								</div>
							</div>
					</transition>
					<component v-bind:is="searchInitializing" @search="addResultsToIstance" v-bind:url="url"></component>
					<div id="item-modal" class="modal" tabindex="-1" role="dialog">
						<div class="modal-dialog modal-dialog-centered" role="document">
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
									<button v-show="updatingItem" type="button" class="btn btn-primary" @click="updateComponent" data-dismiss="modal">Salva</button>
									<button v-show="!updatingItem" type="button" class="btn btn-primary" @click="deleteComponent" data-dismiss="modal">Cancella</button>
								</div>
							</div>
						</div>
					</div>
					<info-modal v-if="showInfoModal" v-bind:item="showInfoModal" @close="infoModalClosed"></info-modal>
				</div>
			</div>
			<div class="section cat-res" style="background-image: linear-gradient(rgba(30, 136, 214, 1), rgba(15, 99, 191, 1));"> 
				<transition name="fade">
					<div class="row justify-content-center" style="width:100%; margin: 0;">
						<div class="col-md-9" style="border:0px; padding:0px; padding-left: 15px; padding-right: 15px;">
							<div class="card" style="max-height:70vh;">
								<div class="card-header p-3">
									<categories @done="showCat" v-bind:data-cat="dataCat" v-bind:data-position="dataPosition"></categories>
								</div>
								<get-cat v-if="showLocals" v-bind:cat="category" v-bind:lat="lat" v-bind:lon="lon"></get-cat>
							</div>
						</div>
					</div>
				</transition>
			</div>					
		</div>
		<div class="section" style="background-color: rgba(15, 99, 191, 1);">
			<div class="row justify-content-center" style="margin:0;">
				<div class="col-10 mt-5">
					<h3 class="text-center mt-3 display-4">Accedi ovunque alle tue liste<br>Condividile con i tuoi amici</h3>
					<div class="row justify-content-center">
						<div class="col-2 mt-3">
						<jsp:include page="./partials/LoginLogoutPartial.jsp"></jsp:include>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="cookies-bar bg-light" id="cookies-bar">
		<div style="margin:5px; display: inline-block;">Utilizzando il sito accetti all'utilizzo dei cookies per migliorare l'esperienza di utilizzo del sito stesso.</div>
		<button type="button" class="btn btn-primary" style="margin:5px; display: inline-block;" onclick="closeCookies()">Chiudi</button>
	</div>
</jsp:attribute>
<jsp:attribute name="customCss">
	<link href="assets/css/landing_page.css" type="text/css" rel="stylesheet" />
	<link href="assets/css/home_fullscreen.css" type="text/css" rel="stylesheet" />
</jsp:attribute>
<jsp:attribute name="customJs">
	<script>
		function closeCookies() {
			$('#cookies-bar').css('display', 'none');
		}
	</script>
	<script src="https://cdn.jsdelivr.net/npm/js-cookie@2/src/js.cookie.min.js"></script>
	<script src="assets/js/landing_page.js"></script>
</jsp:attribute>

</layouts:empty>