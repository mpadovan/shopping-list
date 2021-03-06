/* jshint esversion:6 */

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

Vue.component('infoModal', {
	props: ['item'],
	data: function () {
		return {
			data: {
				name: '',
				categoryName: '',
				categoryLogo: '',
				notes: '',
				isPrivate: '',
				photo: '',
				logo: '',
				amIAnon: ''
			},
			defaultImage: 'https://www.gardensbythebay.com.sg/etc/designs/gbb/clientlibs/images/common/not_found.jpg',
			defaultItem: null
		}
	},
	mounted: function () {
		this.defaultItem = (this.item.item.category == undefined) ? this.item.item : this.item;
		this.data.name = this.defaultItem.item.name;
		this.data.categoryName = this.defaultItem.item.category.name;
		this.data.categoryLogo = (this.defaultItem.item.category.logo == "null" || this.defaultItem.item.category.logo == undefined) ? this.defaultImage : app.path + this.defaultItem.item.category.logo;
		this.data.notes = this.defaultItem.item.note;
		this.data.logo = (this.defaultItem.item.logo == "null" || this.defaultItem.item.logo == undefined) ? this.defaultImage : app.path + this.defaultItem.item.logo;
		this.data.photo = (this.defaultItem.item.photography == "null" || this.defaultItem.item.photography == undefined) ? this.defaultImage : app.path + this.defaultItem.item.photography;
		$('#info-modal').modal('show');
	},
	methods: {
		hideModal: function () {
			this.$emit('close');
		}
	},
	template: `
				<div id="info-modal" class="modal" tabindex="-1" role="dialog">
					<div class="modal-dialog modal-dialog-centered" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title">Informazioni Prodotto</h5>
								<button type="button" class="close" data-dismiss="modal" aria-label="Close" @click="hideModal">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								<div class="row">
									<div class="col-10 text-center" style="margin:auto;">
										<img class="image-product" v-bind:src="data.photo" style="max-width:100%; max-height: 15rem;">
									</div>
									<div class="col">
									<h5 class="card-title text-center mt-2">Informazioni prodotto </h5>
									<table class="table table-responsive-md">
										<tbody>
											<tr>
												<th scope="row">Nome</th>
												<td>{{data.name}}</td>
											</tr>
											<tr>
												<th scope="row">Note</th>
												<td>{{data.notes}}</td>
											</tr>
											<tr>
												<th scope="row">Logo</th>
												<td>
													<div class="info-custom-product"><img class="rounded logo-product" v-bind:src="data.logo" alt="Logo" title="Logo"></div>
												</td>
											</tr>
											<tr>
												<th scope="row">Categoria</th>
												<td>
													<div class="info-custom-product"><img class="rounded logo-product" v-bind:src="data.categoryLogo" alt="Logo" title="Logo categoria di lista">{{ data.categoryName }}</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							</div>
						</div>
					</div>
				</div>`
});

Vue.component('ajaxComponent', {
	props: ['settings'],
	data: function () {
		return {
			results: []
		};
	},
	created: function () {
		var self = this;
		$.ajax(this.settings)
				.done(function (data) {
					self.$emit('done', data);
				})
				.fail(function (err) {
					console.log(err);
					toastr["error"]('Errore AJAX, dettagli in console');
					self.$emit('done', 'error');
				});
	},
	template: "<div style=\"display:none;\"></div>"
});

Vue.component('list-item', {
	props: ['item', 'permission'],
	computed: {
		capitalized: function () {
			var capitalized = _.capitalize(this.item.item.name);
			return capitalized;
		}
	},
	methods: {
		updateItem: function () {
			var self = this;
			this.$emit('update', {
				item: self.item,
			});
			$('#item-modal').modal('show');
		},
		deleteItem: function () {
			var self = this;
			this.$emit('delete', {
				item: self.item,
			});
			$('#item-modal').modal('show');
		},
		infoModal: function () {
			var self = this;
			this.$emit('info', {
				item: self.item
			});
		}
	},
	template: `<tr> 
				<td>{{ capitalized }}</td>
				<td>{{ item.amount }}</td>
				<td @click="infoModal"><i class="fas fa-info-circle"></i></td>
				<td v-if="permission" @click="updateItem"><i class="fas fa-pen-square"></i></td> 
				<td v-if="permission" @click="deleteItem"><i class="fas fa-times"></i></td> 
			</tr>`
});

Vue.component('search-item', {
	props: ['item'],
	data: function () {
		return {
			show: false,
			delay: 190,
			clicks: 0,
			timer: null,
			defaultImage: 'https://www.gardensbythebay.com.sg/etc/designs/gbb/clientlibs/images/common/not_found.jpg'
		};
	},
	computed: {
		capitalized: function () {
			var capitalized = _.capitalize(this.item.name);
			return capitalized;
		},
		photoComputed: function () {
			return (this.item.photography == "null" || this.item.photography == undefined) ? this.defaultImage : app.path + this.item.photography;
		},
		logoComputed: function () {
			return (this.item.logo == "null" || this.item.photography == undefined) ? this.defaultImage : app.path + this.item.logo;
		}
	},
	methods: {
		callParent: function () {
			var self = this;
			this.$emit('add', {
				item: self.item
			});
		},
		oneClick: function (event) { // DEPRECATED
			this.clicks++
			if (this.clicks === 1) {
				var self = this
				this.timer = setTimeout(function () {
					self.show = !self.show;
					self.clicks = 0
				}, this.delay);
			} else {
				clearTimeout(this.timer);
				this.callParent();
				this.clicks = 0;
			}
		},
		infoModal: function () {
			var self = this;
			this.$emit('info', {
				item: self.item
			})
		}
	},
	template: `<div class="col-lg-3"> 
					<div class="outer-search-item"> 
						<div v-bind:style="{ backgroundImage: \'url(\' + photoComputed + \')\' }" class="inner-search-item"> 
							<div class="row align-items-center search-item-more-info-overlay" style="width:100%; height:100%;margin:0;"> 
								<div style="width:fit-content; margin:auto;"> 
									<div @click="callParent" style="display:inline-block"><i class="fas fa-plus-circle" style="font-size:4rem;margin-right:1rem;"></i></div> 
									<div @click="infoModal" style="display:inline-block"><i class="fas fa-info-circle" style="font-size:4rem;"></i></div> 
								</div> 
							</div> 
						</div> 
					<h5 @click="callParent" class="pointer add-product-to-list" style="border-top:#333 solid 1px;">{{ capitalized }}</h5> 
					<h6>{{ item.category.name }}</h6> 
					</div> 
				</div>`
});

Vue.component('fetchListComponent', {
	props: ['settings'],
	data: function () {
		return {
			results: []
		};
	},
	created: function () {
		var self = this;
		if (window.location.pathname.split('HomePageLogin/')[1].includes('/') &&
				window.location.pathname.split('HomePageLogin/')[1].split("/")[1] !== '') {
			$.ajax(this.settings)
					.done(function (data) {
						self.$emit('done', data);
					})
					.fail(function (err) {
						console.log(err);
						toastr["error"]('Errore AJAX, dettagli in console');
						return;
					});
		}
	},
	template: "<div style=\"display:none;\"></div>"
});

var app = new Vue({
	el: '#app',
	data: {
		msg: 'Cerca Prodotti',
		showList: true,
		showSearch: false,
		query: '',
		results: [],
		items: [],
		item_name: null,
		item_amount: null,
		updatingItem: true,
		searchInitializing: null,
		searchCategories: null,
		resultsSorted: null,
		selected: 'all',
		noResults: false,
		showAutocomplete: false,
		url: null,
		autocompleteList: [],
		user: null,
		item_id: null,
		showAutocompleteList: false,
		ajaxSettings: {},
		ajaxComponent: false,
		operation: null,
		list: null,
		chat: false,
		permission: false,
		lockAjaxComponent: false,
		item_selected_id: -2,
		loaded_list: false,
		showInfoModal: false,
		fetchListComponent: false,
		updateTimer: 2000,
		firstFetch: true
	},
	computed: {
		autocompleteComputed: function () {
			var temp = _.cloneDeep(this.autocompleteList);
			for (var r = 0; r < temp.length; r++) {
				temp[r].sid = r;
			}
			return temp;
		},
		path: function () {
			return _.split(window.location.href, '/', 4).toString().replace(new RegExp(',', 'g'), '/') + '/';
		}
	},
	methods: {
		keepListAlwaysUpToDate: function() {
			var self = this;
			this.timer = setInterval(function() {
				self.fetchList();
			}, self.updateTimer);
		},
		searching: function () {
			if (this.query != '') {
				this.showList = false;
				this.ajaxSettings = {
					"url": this.path + 'services/products/restricted/' + this.user + '?search=' + this.query,
					"method": 'GET',
					"async": true,
					"crossDomain": true
				};
				this.operation = 3;
				this.ajaxComponent = true;
				this.showAutocomplete = false;
			}
		},
		listHided: function () {
			this.item_selected_id = -2;
			this.showSearch = true;
		},
		searchHided: function () {
			this.showList = true;
		},
		addItemToList: function (item) {
			this.isInList(item);
		},
		isInList: function (item) {
			var isPrivate = (item.item.owner) ? 'personal' : 'public';
			this.ajaxSettings = {
				"url": this.path + 'services/lists/restricted/' + this.user + '/permission/' + this.list + '/products/' + isPrivate,
				"method": 'POST',
				"async": true,
				"crossDomain": true,
				"data": "{\"id\": \"" + item.item.id + "\"}",
				"headers": {
					"Content-Type": "application/json",
					"Cache-Control": "no-cache"
				}
			};
			this.operation = 5;
			this.ajaxComponent = true;
			this.operationData = {
				product_name: item.item.name
			};
		},
		updateWithModal: function (val) {
			this.updatingItem = true;
			this.item_name = val.item.item.name;
			this.item_id = val.item.item.id;
			this.item_amount = val.item.amount;
			this.item_owner = (val.item.item.owner) ? 'personal' : 'public';
		},
		updateComponent: function () {
			this.ajaxSettings = {
				"url": this.path + 'services/lists/restricted/' + this.user + '/permission/' + this.list + '/products/' + this.item_owner + '/' + this.item_id,
				"method": 'PUT',
				"async": true,
				"crossDomain": true,
				"data": this.item_amount,
				"headers": {
					"Content-Type": "application/json",
					"Cache-Control": "no-cache"
				}
			};
			this.operation = 6;
			this.ajaxComponent = true;
			this.operationData = {
				product_name: this.item_name
			};
		},
		deleteWithModal: function (val) {
			this.updatingItem = false;
			this.item_name = val.item.item.name;
			this.item_id = val.item.item.id;
			this.item_owner = (val.item.item.owner) ? 'personal' : 'public';
		},
		deleteComponent: function () {
			this.ajaxSettings = {
				"url": this.path +'services/lists/restricted/' + this.user + '/permission/' + this.list + '/products/' + this.item_owner + '/' + this.item_id,
				"method": 'DELETE',
				"async": true,
				"crossDomain": true
			};
			this.operation = 7;
			this.ajaxComponent = true;
			this.operationData = {
				product_name: this.item_name
			};
		},
		addResultsToIstance: function (data) {
			if (this.showAutocomplete) {
				if (data.length == 0) {
					this.showAutocompleteList = false;
				} else {
					this.showAutocompleteList = true;
					this.autocompleteList = data;
					this.item_selected_id = -2;
				}
			} else {
				this.results = data;
				for (var j = 0; this.results.length > j; j++) {
					if (this.results[j].category.id == 0 || typeof this.results[j].category == undefined)
						this.results[j].category = {
							name: 'Default'
						};
				}
				this.resultsSorted = this.results;
				if (this.results.length == 0)
					this.noResults = true;
				else
					this.noResults = false;
				var arr = [];
				for (var i = 0; data.length > i; i++) {
					if (data[i].category.id == 0 || typeof data[i].category == undefined) {
						arr.push('Default');
					} else {
						arr.push(data[i].category.name);
					}
				}
				var unique = arr.filter(function (elem, index, self) {
					return index === self.indexOf(elem);
				});
				this.searchCategories = unique;
			}
			this.searchInitializing = null;
		},
		hideSearch: function () {
			this.query = '';
			this.selected = 'all';
			this.showSearch = false;
		},
		replaceQuerySearch: function (val) {
			this.lockAjaxComponent = true;
			this.query = val;
			this.ajaxComponent = false;
			this.searching();
		},
		quickAddProduct: function () {
			this.ajaxSettings = {
				"async": true,
				"crossDomain": true,
				"url": this.path + "services/products/restricted/" + this.user + '/' + this.list,
				"method": "POST",
				"data": "{\"name\": \"" + this.query + "\"}",
				"headers": {
					"Content-Type": "application/json",
					"Cache-Control": "no-cache"
				}
			};
			this.operation = 2;
			this.ajaxComponent = true;
			this.operationData = {
				product_name: this.query
			};
		},
		fetchList: function () {
			this.fetchListSettings = {
				"async": true,
				"crossDomain": true,
				"url": this.path + 'services/lists/restricted/' + this.user + '/permission/' + this.list + '/products',
				"method": "GET",
			};
			if(this.fetchListComponent){
				return;
			}
			else this.fetchListComponent = true;
		},
		ajaxDone: function (data) {
			this.ajaxComponent = false;
			this.lockAjaxComponent = false;
			if (data === 'error')
				return;
			else {
				switch (this.operation) {
					case 1:
						data = data.products.concat(data.publicProducts);
						this.addResultsToIstance(data);
						break;
					case 2:
						this.showAutocomplete = false;
						this.query = '';
						toastr['success'](_.capitalize(this.operationData.product_name) + ' creato ed aggiunto alla lista attuale');
						break;
					case 3:
						data = data.products.concat(data.publicProducts);
						this.addResultsToIstance(data);
						break;
					case 4:
						data = data.publicProducts.concat(data.products);
						//_.sortBy(data,['product.category.name', 'product.name']);
						for (var i = 0; data.length > i; i++) {
							data[i].item = data[i].product;
							data[i].product = undefined;
							this.items.push(data[i]);
						}
						break;
					case 5:
						toastr["success"](_.capitalize(this.operationData.product_name) + ' aggiunto');
						break;
					case 6:
						toastr["success"](_.capitalize(this.operationData.product_name) + ' aggiornato');
						break;
					case 7:
						toastr["success"](_.capitalize(this.operationData.product_name) + ' cancellato');
						break;
					default:
						break;
				}
				this.operation = null;
			}
			this.fetchList();
		},
		sortBasedOnCategories: function (val) {
			if (this.selected == 'all')
				return true;
			else if (val == this.selected)
				return true;
			else
				return false;
		},
		fetchListDone: function (data) {
			this.permission = true;
			if (!data.editList && this.firstFetch) {
				toastr['info']('Non sei abilitato alla modifica di questa lista, contatta l\'amministratore della lista per informazioni');
				this.permission = false;
			}
			this.firstFetch = false;
			this.fetchListComponent = false;
			data = data.publicProducts.concat(data.products);
			//_.sortBy(data,['product.category.name', 'product.name']);
			this.items = [];
			for (var i = 0; data.length > i; i++) {
				data[i].item = data[i].product;
				data[i].product = undefined;
				this.items.push(data[i]);
			}
			$('#chat').height($('#app').height());
		},
		infoItemOnModal: function (item) {
			this.showInfoModal = item;
		},
		infoModalClosed: function() {
			this.showInfoModal = false;
		}
	},
	watch: {
		query: function (val) {
			if (val == 0 || this.lockAjaxComponent) {
				this.showAutocomplete = false;
				this.hideSearch();
			} else if (val == '' && this.item_selected_id != -2) {
				$('#item' + this.item_selected_id).removeClass('selected');
				this.item_selected_id = -2;
			} else {
				this.showAutocomplete = true;
				this.ajaxSettings = {
					"url": this.path + 'services/products/restricted/' + this.user + '/?search=' + this.query + '&compact=true',
					"method": 'GET',
					"async": true,
					"crossDomain": true
				};
				this.operation = 1;
				this.ajaxComponent = true;
				$('#search-input').focus();
			}
			if (app.item_selected_id != -2 && val === $('#item' + app.item_selected_id)[0].textContent) {
				app.searching();
			}
		},
		selected: function (val) {
			if (val == 'all') {
				this.resultsSorted = this.results;
				return;
			}
			this.resultsSorted = [];
			for (var i = 0; this.results.length > i; i++) {
				if (this.results[i].category.name == val)
					this.resultsSorted.push(this.results[i]);
			}
		},
		chat: function (val) {
			$('#chat').css('display', 'block');
			$("#shared-list-outer-" + this.list).removeClass('show-badge');
			$('#shared-list-' + this.list).text('');
		},
		item_selected_id: function () {
			for (var i = 0; i < this.autocompleteComputed.length; i++) {
				$('#item' + i).removeClass('selected');
			}
			$('#item' + this.item_selected_id).addClass('selected');
		}
	},
	created: function () {
		this.user = window.location.pathname.split('HomePageLogin/')[1].split('/')[0];
		this.list = window.location.pathname.split('HomePageLogin/')[1].split('/')[1];
		this.fetchList();
		/*if (typeof (Worker) !== "undefined") {
		 if (typeof (w) == "undefined") {
		 w = new Worker("/ShoppingList/assets/js/workers/sw.js");
		 }
		 } else {
		 toastr['error']('Non riusciamo a mandare notifiche a questo PC, aggiorna il browser e riprova.');
		 }*/
	},
	mounted: function () {
		this.loaded_list = true;
		this.keepListAlwaysUpToDate();
	}
});

$('#search-bar').keydown((e) => {
	if (e.keyCode === 13 && app.item_selected_id != -2) {
		app.query = $('#item' + app.item_selected_id)[0].textContent;
	}
	if (e.keyCode === 13) {
		app.searching();
	}
	if (app.item_selected_id == -2 && (e.keyCode === 40 || e.keyCode === 38)) {
		app.item_selected_id = 0;
	} else if (e.keyCode === 40) {
		app.item_selected_id = app.item_selected_id + 1;
		if (app.item_selected_id == app.autocompleteComputed.length) {
			app.item_selected_id = app.item_selected_id - 1;
		}
	} else if (e.keyCode === 38) {
		app.item_selected_id = app.item_selected_id - 1;
		if (app.item_selected_id == -1) {
			app.item_selected_id = app.item_selected_id + 1;
		}
	}
});