/* jshint esversion:6 */

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
		}
	},
	template: '<tr> \
				<td>{{ capitalized }}</td> \
				<td>{{ item.amount }}</td> \
				<td>{{ item.item.note }}</td> \
				<td>{{ item.item.category.name }}</td> \
				<td v-if="permission" @click="updateItem"><i class="fas fa-pen-square"></i></td> \
				<td v-if="permission" @click="deleteItem"><i class="fas fa-trash"></i></td> \
			</tr>'
});

Vue.component('search-item', {
	props: ['item'],
	data: function () {
		return {
			show: false,
			delay: 190,
			clicks: 0,
			timer: null
		};
	},
	computed: {
		capitalized: function () {
			var capitalized = _.capitalize(this.item.name);
			return capitalized;
		}
	},
	created: function () {
		this.item.photography = (this.item.photography == "null") ? null : this.item.photography;
		this.item.logo = (this.item.logo == "null") ? null : this.item.logo;
	},
	methods: {
		callParent: function () {
			var self = this;
			this.$emit('add', {
				item: self.item
			});
		},
		oneClick: function (event) {
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
		}
	},
	template: '<li class="list-group-item noselect"> \
					<div class="row align-items-center" @click="oneClick"> \
						<div v-if="item.photography" class="float-left"><img v-bind:src="item.photography" class="img-thumbnail" v-bind:alt="capitalized" style="width:10%"></div> \
						<div class="col align-self-center float-left"><h5>{{ capitalized }}</h5><h6>{{ item.category.name }}</h6></div>\
				 		<div class="col align-self-center float-right"><div><i class="fas fa-chevron-down float-right" style="font-size:1.5em"></i></div></div> \
					</div> \
					<div class="row align-items-center mt-2" v-show="show"> \
						<div class="col-1 align-self-left" v-if="item.logo"> \
							<img v-bind:src="item.logo" class="img-thumbnail float-left" v-bind:alt="capitalized" style="width:100%"> \
						</div> \
						<div class="col align-self-left"> \
							<div >{{item.note }}</div> \
						</div> \
						<div class="col align-self-right"><button @click="callParent" type="button" class="btn btn-primary float-right">Aggiungi alla lista</button></div> \
					</div> \
				</li>'
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
		item_selected_id: -2
	},
	computed: {
		autocompleteComputed: function () {
			var temp = _.cloneDeep(this.autocompleteList);
			for (var r = 0; r < temp.length; r++) {
				temp[r].sid = r;
			}
			return temp;
		}
	},
	methods: {
		searching: function () {
			if (this.query != '') {
				this.showList = false;
				this.ajaxSettings = {
					"url": '/ShoppingList/services/products/restricted/' + this.user + '?search=' + this.query,
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
			if (this.items.length === 0)
				toastr['info']('Clicca due volte velocemente sopra un risultato per aggiungerlo alla lista rapidamente');
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
				"url": '/ShoppingList/services/lists/restricted/' + this.user + '/permission/' + this.list + '/products/' + isPrivate,
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
				"url": '/ShoppingList/services/lists/restricted/' + this.user + '/permission/' + this.list + '/products/' + this.item_owner + '/' + this.item_id,
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
				"url": '/ShoppingList/services/lists/restricted/' + this.user + '/permission/' + this.list + '/products/' + this.item_owner + '/' + this.item_id,
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
			alert(val);
			this.lockAjaxComponent = true;
			this.query = val;
			this.ajaxComponent = false;
			this.searching();
		},
		quickAddProduct: function () {
			this.ajaxSettings = {
				"async": true,
				"crossDomain": true,
				"url": "/ShoppingList/services/products/restricted/" + this.user + '/' + this.list,
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
				"url": '/ShoppingList/services/lists/restricted/' + this.user + '/permission/' + this.list + '/products',
				"method": "GET",
			};
			this.fetchListComponent = true;
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
						toastr['success'](this.operationData.product_name + ' creato ed aggiunto alla lista attuale');
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
						toastr["success"](this.operationData.product_name + ' aggiunto');
						break;
					case 6:
						toastr["success"](this.operationData.product_name + ' aggiornato');
						break;
					case 7:
						toastr["success"](this.operationData.product_name + ' cancellato');
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
			if (!data.editList) {
				toastr['info']('Non sei abilitato alla modifica di questa lista, contatta l\'amministratore della lista per informazioni');
				this.permission = false;
			}
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
		}
	},
	watch: {
		query: function (val) {
			if (val == 0 || this.lockAjaxComponent) {
				this.showAutocomplete = false;
				this.hideSearch();
			} else if(val == '' && this.item_selected_id != -2) {
				$('#item' + this.item_selected_id ).removeClass('selected');
				this.item_selected_id = -2;
			} else {
				this.showAutocomplete = true;
				this.ajaxSettings = {
					"url": '/ShoppingList/services/products/restricted/' + this.user + '/?search=' + this.query + '&compact=true',
					"method": 'GET',
					"async": true,
					"crossDomain": true
				};
				this.operation = 1;
				this.ajaxComponent = true;
				$('#search-input').focus();	
			}
			if(app.item_selected_id != -2 && val === $('#item' + app.item_selected_id)[0].textContent) {
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
		},
		item_selected_id: function () {
			for (var i = 0; i < this.autocompleteComputed.length; i++) {
				$('#item' + i).removeClass('selected');
			}
			$('#item' + this.item_selected_id).addClass('selected');
			console.log(this.item_selected_id);
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
	}
});

$('#search-bar').keydown((e) => {
	if( e.keyCode === 13 && app.item_selected_id != -2) {
		app.query = $('#item' + app.item_selected_id)[0].textContent;
	}
	if(e.keyCode === 13){
		app.searching();
	}
	if(app.item_selected_id == -2 && (e.keyCode === 40 || e.keyCode === 38)) {
		app.item_selected_id = 0;
	}
	else if (e.keyCode === 40) {
		app.item_selected_id = app.item_selected_id + 1;
		if(app.item_selected_id == app.autocompleteComputed.length) {
			app.item_selected_id = app.item_selected_id - 1;
		}
	} else if (e.keyCode === 38) {
		app.item_selected_id = app.item_selected_id - 1;
		if(app.item_selected_id == -1) {
			app.item_selected_id = app.item_selected_id + 1;
		}
	}
});