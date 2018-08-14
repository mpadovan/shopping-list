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
			.done(function(data) {
				self.$emit('done', data);
			})
			.fail(function(err) {
				alert("Errore nel caricamento del componente AJAX, dettagli in console.");
				console.log(err);
			});
	},
	template: "<div style=\"display:none;\"></div>"
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
		user: '1',
		item_id: null,
		showAutocompleteList: false,
		ajaxSettings: {},
		ajaxComponent: null,
		operation: null
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
				this.ajaxComponent = 'ajaxComponent';
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
			toastr["success"](item.item.name + ' aggiunto')
			for (var i = 0; this.items.length > i; i++) {
				if (this.items[i].item.name == item.item.name && this.items[i].item.id == item.item.id) {
					this.items[i].amount++;
					this.updateLocalStorage();
					return;
				}
			}
			item.amount = 1;
			this.items.push(item);
		},
		updateLocalStorage: function () {
			localStorage.setItem("items", JSON.stringify(this.items));
		},
		updateWithModal: function (val) {
			this.updatingItem = true;
			this.item_name = val.item.item.name;
			this.item_id = val.item.item.id;
			this.item_amount = val.item.amount;
		},
		updateComponent: function () {
			for (var i = 0; this.items.length > i; i++) {
				if (this.items[i].item.name == this.item_name && this.items[i].item.id == this.item_id) {
					(this.item_amount == 0) ? this.items.splice(i, 1) : this.items[i].amount = this.item_amount;
					this.updateLocalStorage();
					return;
				}
			}
		},
		deleteWithModal: function (val) {
			this.updatingItem = false;
			this.item_name = val.item.item.name;
			this.item_id = val.item.item.id;
			this.item_amount = undefined;
		},
		deleteComponent: function () {
			for (var i = 0; this.items.length > i; i++) {
				if (this.items[i].item.name == this.item_name && this.items[i].item.id == this.item_id) {
					this.items.splice(i, 1);
					this.updateLocalStorage();
					return;
				}
			}
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
				this.resultsSorted = this.results;
				if (this.results.length == 0) this.noResults = true;
				else this.noResults = false;
				var arr = [];
				console.log(data);
				for (var i = 0; data.length > i; i++) {
					if(typeof data[i].category == 'undefined') {
						arr.push('default')
					} else {
						arr.push(data[i].category.name)
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
		replaceQuerySearch: function(val) {
			this.query = val;
		},
		quickAddProduct: function() {
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
			this.ajaxComponent = 'ajaxComponent';
			this.operationData = {
				product_name: this.query
			};
		},
		ajaxDone: function(data) {
			this.ajaxComponent = null;
			switch (this.operation) {
				case 1:
					this.addResultsToIstance(data);
					break;
				case 2:
					toastr['success'](this.operationData.product_name + ' aggiunto ai propri prodotti');
					break;
				case 3: 
					data = data.products.concat(data.publicProducts);
					this.addResultsToIstance(data);
					break;
				default:
					break;
			}
			this.operation = null;
		},
		sortBasedOnCategories: function(val) {
			if(this.selected == 'all') return true;
			else if(val == this.selected) return true;
			else return false;
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
					"url": '/ShoppingList/services/products/?search=' + this.query + '&compact=true',
					"method": 'GET',
					"async": true,
  					"crossDomain": true
				};
				this.operation = 1;
				this.ajaxComponent = 'ajaxComponent';
				$('#search-input').focus();
			}
		},
		items: function (val) {
			this.updateLocalStorage();
		},
		selected: function (val) {
			if (val == 'all') {
				this.resultsSorted = this.results;
				return;
			}
			this.resultsSorted = [];
			for (var i = 0; this.results.length > i; i++) {
				if (this.results[i].category == val) this.resultsSorted.push(this.results[i]);
			}
		}
	}
});