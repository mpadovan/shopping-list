<%-- 
    Document   : HomePageLogin
    Created on : 1-lug-2018, 11.44.20
    Author     : giuliapeserico
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:base pageTitle="Landing Page">
	<jsp:attribute name="pageContent">
		<div class="container-fluid mt-4">
			<div class="row justify-content-center">
				<c:if test="${!empty requestScope.currentList}">
					<div class="col" id="app">
						<c:if test="${requestScope.addDeletePermission}">
							<transition name="fade">
								<div v-show="loaded_list" style="display: none;">
									<div class="card">
										<div class="card-body">
											<!--
											<div class="float-right mb-2">
												<a href="NewProduct.jsp">
													<u>Crea prodotto</u>
												</a>
											</div>
											-->
											<div class="input-group mb-0">
												<input id="search-bar" type="text" class="form-control" v-bind:placeholder="msg" v-model="query">
												<div class="input-group-append">
													<button class="btn btn-outline-secondary" type="button" @click="searching">
														<i class="fas fa-search"></i>
													</button>
												</div>
											</div>
											<div class="p-1 pt-3 pb-2 autocomplete" v-if="showAutocomplete">
												<li class="pointer autocomplete-li" v-if="!showAutocompleteList" @click="quickAddProduct()">Non troviamo alcun prodotto con nome
													<b> {{ query }}</b>. Clicca qui per crearlo.</li>
												<li v-bind:id="'item' + item.sid" class="pointer autocomplete-li" v-if="showAutocompleteList" v-for='item in autocompleteComputed' v-bind:key='item.name' @click="replaceQuerySearch(item.name)">{{ item.name }}</li>
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
														<search-item v-for="result in resultsSorted" v-bind:key="result.name + result.id" v-bind:item="result" @add="addItemToList"
																	 class="search-result pointer"></search-item>
													</ul>
												</div>
											</transition>
										</div>
									</div>
							</transition>
						</c:if>
						<transition name="fade" v-on:after-leave="listHided">
							<div class="card" id="list" v-if="showList">
								<div class="card-body">
									<h5 class="card-title text-center">Lista corrente: <a href="${pageContext.servletContext.contextPath}/restricted/InfoList/${sessionScope.user.id}/${requestScope.currentList.id}">
											<u>${requestScope.currentList.name}</u></a>
									</h5>

									<c:if test="${!empty requestScope.sharedUsers}">
										<div class="d-flex justify-content-end">

											<p class="pointer" @click="chat = !chat">Chat
												<a href="#">
													<i class="far fa-comments"></i>
												</a>
											</p>
										</div>
									</c:if>
									<div class="table-wrapper-2 table-responsive-md">
										<table class="table table-striped">
											<thead>
												<tr>
													<th scope="col">Nome prodotto</th>
													<th scope="col">Quantità</th>
													<th scope="col">Note</th>
													<th scope="col">Categoria</th>
													<th v-if="permission" scope="col" colspan="2">Gestisci</th>
												</tr>
											</thead>
											<tbody>
												<tr is="list-item" v-for='item in items' v-bind:key='item.item.name + item.item.id' v-bind:item="item" v-bind:permission="permission" @update="updateWithModal"
													@delete="deleteWithModal"></tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</transition>
						<fetch-list-component @done="fetchListDone" v-bind:settings="fetchListSettings" v-if="fetchListComponent"></fetch-list-component>
						<ajax-component @done="ajaxDone" v-bind:settings="ajaxSettings" v-if="ajaxComponent"></ajax-component>
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
											<input type="number" name="item-amount" id="item-amount" v-model="item_amount" min="1">
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
					</div>
				</div>
				<div class="chat col-lg-5" id="chat">
					<div class="card">
						<div class="card-body" style="height:90%">
							<div class="d-flex justify-content-end mb-2">
								<button type="button" class="close" aria-label="Close" @click="chat = !chat">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="messages-container" id="message-container">
								<message-component v-for="message in messages" v-bind:message="message"></message-component>
							</div>
						</div>
						<div class="input-group" style="height:10%;">
							<input type="text" class="form-control" placeholder="Scrivi qualcosa..." v-model="text" @keyup.enter="send">
							<div class="input-group-append">
								<button class="btn btn-outline-secondary" type="button" @click="send">
									<i class="far fa-paper-plane"></i>
								</button>
							</div>
						</div>
					</div>
				</div>
			</c:if>
			<c:if test="${empty requestScope.currentList}">
				<div class="col">
					<div class="card">
						<div class="card-body card-closed-list text-center">
							<p class="mb-2">Seleziona una lista oppure <a href="${pageContext.servletContext.contextPath}/restricted/NewList"><u>clicca qui</u></a> per crearne una nuova</p>
							<div class="div-img-closed-list"><img class="image-closed-list" src="http://getdrawings.com/image/panda-eating-bamboo-drawing-54.jpg" alt="Hungry Panda"></div>
						</div>
					</div>
				</div>
			</c:if>
		</div>
	</div>
</jsp:attribute>
<jsp:attribute name="customCss">
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assets/css/landing_page.css">
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assets/css/landing_page_restricted.css">
</jsp:attribute>
<jsp:attribute name="customJs">
	<c:if test="${not empty requestScope.currentList}">
		<script src="${pageContext.servletContext.contextPath}/assets/js/landing_page_restricted.js"></script>
		<script src="${pageContext.servletContext.contextPath}/assets/js/chat_manager.js"></script>
	</c:if>
</jsp:attribute>
</layouts:base>