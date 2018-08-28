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
		console.log('AjaxComponent Created');
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
	template: "<div style=\"display:none;\"></div>",
	destroyed: function () {
		console.log('destroyed', this._inactive, this.item);
	}
});

Vue.component('list-item', {
	props: ['item'],
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
				<td @click="updateItem"><i class="fas fa-pen-square"></i></td> \
				<td @click="deleteItem"><i class="fas fa-trash"></i></td> \
			</tr>'
});

Vue.component('search-item', {
	props: ['item'],
	computed: {
		capitalized: function () {
			var capitalized = _.capitalize(this.item.name);
			return capitalized;
		}
	},
	methods: {
		callParent: function () {
			var self = this;
			this.$emit('add', {
				item: self.item
			});
		}
	},
	template: '<li class="list-group-item" @click="callParent"> \
					<div class="row align-items-center"> \
						<div class="col align-self-center float-left"><h5>{{ capitalized }}</h5><h6>{{ item.category.name }}</h6></div>\
				 		<div class="col align-self-center float-right"><i class="fa fa-plus float-right"></i></div> \
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
		$.ajax(this.settings)
			.done(function (data) {
				self.$emit('done', data);
			})
			.fail(function (err) {
				console.log(err);
				toastr["error"]('Errore AJAX, dettagli in console');
				return;
			});
	},
	template: "<div style=\"display:none;\"></div>",
	destroyed: function () {
		console.log('fetch destroyed', this._inactive, this.item);
	}
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
		user: 1,
		item_id: null,
		showAutocompleteList: false,
		ajaxSettings: {},
		ajaxComponent: false,
		operation: null,
		list: 1,
		chat: false,
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
				}
			} else {
				this.results = data;
				for (var j = 0; this.results.length > j; j++) {
					if (this.results[j].category.id == 0 || typeof this.results[j].category == undefined) this.results[j].category = {
						name: 'Default'
					};
				}
				this.resultsSorted = this.results;
				if (this.results.length == 0) this.noResults = true;
				else this.noResults = false;
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
			this.query = val;
		},
		quickAddProduct: function () {
			this.ajaxSettings = {
				"async": true,
				"crossDomain": true,
				"url": "/ShoppingList/services/products/restricted/" + this.user,
				"method": "POST",
				"data": "{\"name\": " + this.query + "}",
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
			if (data === 'error') return;
			else {
				switch (this.operation) {
					case 1:
						data = data.products.concat(data.publicProducts);
						this.addResultsToIstance(data);
						break;
					case 2:
						toastr['success'](this.operationData.product_name + ' aggiunto ai propri prodotti');
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
			if (this.selected == 'all') return true;
			else if (val == this.selected) return true;
			else return false;
		},
		fetchListDone: function (data) {
			this.fetchListComponent = false;
			data = data.publicProducts.concat(data.products);
			//_.sortBy(data,['product.category.name', 'product.name']);
			this.items = [];
			for (var i = 0; data.length > i; i++) {
				data[i].item = data[i].product;
				data[i].product = undefined;
				this.items.push(data[i]);
			}
			console.log(this.items);
			$('#chat').height($('#app').height());
		}
	},
	watch: {
		query: function (val) {
			if (val == 0) {
				this.showAutocomplete = false;
				this.hideSearch();
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
		},
		selected: function (val) {
			if (val == 'all') {
				this.resultsSorted = this.results;
				return;
			}
			this.resultsSorted = [];
			for (var i = 0; this.results.length > i; i++) {
				if (this.results[i].category.name == val) this.resultsSorted.push(this.results[i]);
			}
		},
		chat: function(val) {
			$('#chat').toggleClass('show-chat');
		} 
	},
	created: function () {
		this.user = window.location.pathname.split('HomePageLogin/')[1];
		this.fetchList();
	}
});
/*
var wsUri = "ws://localhost:8080/ShoppingList/restricted/messages/1";
	var output;
	var date = new Date();
	var message = {
		operation: 0,
		payload: {
			sender: {
				name: "Luigi",
				lastname: "Bianchi",
				id: 2, 
				errors: {}
			},
			list: {
				id: 1,
				errors: {}
			},
			sendTime: "Aug 23, 2018 3:27:18 PM",
			text: "Ciao Mario, Tutto bene, e tu?",
			errors: {}
		}
	};

	function init()
	{
		output = document.getElementById("output");
		websocket = new WebSocket(wsUri);
		testWebSocket();
	}

	function testWebSocket()
	{
		websocket.onopen = function (evt) {
			onOpen(evt)
		};
		websocket.onclose = function (evt) {
			onClose(evt)
		};
		websocket.onmessage = function (evt) {
			onMessage(evt)
		};
		websocket.onerror = function (evt) {
			onError(evt)
		};
	}

	function onOpen(evt)
	{
		writeToScreen("CONNECTED");
	}

	function onClose(evt)
	{
		writeToScreen("DISCONNECTED");
	}

	function onMessage(evt)
	{
		writeToScreen('<span style="color: blue;">RESPONSE: ' + evt.data + '</span>');
	}

	function onError(evt)
	{
		writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
	}

	function doSend()
	{
		writeToScreen("SENT: " + JSON.stringify(message));
		websocket.send(JSON.stringify(message));
	}

	function writeToScreen(message)
	{
		var pre = document.createElement("p");
		pre.style.wordWrap = "break-word";
		pre.innerHTML = message;
		output.appendChild(pre);
	}
	*/