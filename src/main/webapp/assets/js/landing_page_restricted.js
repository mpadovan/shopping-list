/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

Vue.component('search', {
	props: ['url'],
	data: function () {
		return {
			results: []
		};
	},
	created: function () {
		var self = this;
		$.get({
			url: self.url,
			success: function (data) {
				data = _.sortBy(data, ['name']);
				self.$emit('search', data);
				//self.results = mergedArray;
			},
			error: function (error) {
				alert('Errore nel caricamento del componente di ricerca, dettagli in console');
				console.log(error);
			}
		});
	},
	template: "<div style=\"display:none;\"></div>"
});

Vue.component('list-item', {
	props: ['item'],
	computed: {
		capitalized: function () {
			console.log(this.item);
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
						<div class="col align-self-center float-left"><h5>{{ capitalized }}</h5><h6>{{ item.category }}</h6></div>\
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
		user: 'anon',
		item_id: null
	},
	methods: {
		searching: function () {
			if (this.query != '') {
				this.showList = false;
				this.url = '/ShoppingList/services/products/?search=' + this.query;
				this.searchInitializing = 'search';
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
				console.log(data);
				(data.length == 0) ? this.autocompleteList = [{name: 'Nessun risultato'}] : this.autocompleteList = data;
			} else {
				this.results = data;
				this.resultsSorted = this.results;
				console.log(this.results.length);
				if (this.results.length == 0) this.noResults = true;
				else this.noResults = false;
				var arr = [];
				for (var i = 0; data.length > i; i++) {
					arr.push(data[i].category);
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
		}
	},
	watch: {
		query: function (val) {
			if (val == 0) {
				this.showAutocomplete = false;
				this.hideSearch();
			} else {
				this.showAutocomplete = true;
				this.url = '/ShoppingList/services/products/?search=' + this.query + '&compact=true';
				this.searchInitializing = 'search';
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