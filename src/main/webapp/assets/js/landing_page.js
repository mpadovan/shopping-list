/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
Vue.component('search', {
	props: ['query'],
	data: function() {
		return {
			results: [],
			user: 'mariorossi@gmail.com'
		};
	},
	created: function(){
		var self = this;
		$.get({
            url: '/ShoppingList/services/products/' + self.user + '?search=' + self.query,
            success: function (data) {
				var mergedArray = data.publicProducts.concat(data.products);
				for(var i = 0; mergedArray.length > i; i++) {
					if(mergedArray[i] == null) {
						mergedArray.splice(i,1);
					}
				}
				mergedArray = _.sortBy(mergedArray, ['name']);
				self.$emit('search', mergedArray);
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
	props: ['name', 'amount'],
	computed: {
		capitalized: function () {
			var capitalized = _.capitalize(this.name);
			return capitalized;
		}
	},
	methods: {
		updateItem: function() {
			var self = this;
			this.$emit('update', {
				name: self.name,
				amount: self.amount
			});
			$('#item-modal').modal('show');
		},
		deleteItem: function() {
			var self = this;
			this.$emit('delete', {
				name: self.name,
				amount: undefined
			});
			$('#item-modal').modal('show');
		}
	},
	template: '<tr> \
				<td>{{ capitalized }}</td> \
				<td>{{ amount }}</td> \
				<td @click="updateItem"><i class="fas fa-pen-square"></i></td> \
				<td @click="deleteItem"><i class="fas fa-trash"></i></td> \
			</tr>'
});
Vue.component('search-item', {
	props: ["name"],
	computed: {
		capitalized: function () {
			var capitalized = _.capitalize(this.name);
			return capitalized;
		}
	},
	methods: {
		callParent: function () {
			var self = this;
			this.$emit('add', {
				name: self.capitalized,
				amount: 1
			});
		}
	},
	template: '<li class="list-group-item" @click="callParent"> \
				 {{ capitalized }} \
				 <i class="fa fa-plus float-right"></i> \
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
		noResults: false
	},
	methods: {
		searching: function () {
			if(this.query != '') {
				this.showList = false;
				this.searchInitializing = 'search';
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
			for (var i = 0; this.items.length > i; i++) {
				if (this.items[i].name == item.name) {
					this.items[i].amount++;
					this.updateLocalStorage();
					return;
				}
			}
			this.items.push(item);
		},
		updateLocalStorage: function() {
			localStorage.setItem("items", JSON.stringify(this.items));
		},
		updateWithModal: function(val) {
			this.updatingItem = true;
			this.item_name = val.name;
			this.item_amount = val.amount;
		},
		updateComponent: function() {
			for (var i = 0; this.items.length > i; i++) {
				if (this.items[i].name == this.item_name) {
					(this.item_amount == 0) ? this.items.splice(i, 1) : this.items[i].amount = this.item_amount;
					this.updateLocalStorage();
					return;
				}
			}
		},
		deleteWithModal: function(val) {
			this.updatingItem = false;
			this.item_name = val.name;
			this.item_amount = val.amount;
		},
		deleteComponent: function() {
			for (var i = 0; this.items.length > i; i++) {
				if (this.items[i].name == this.item_name) {
					this.items.splice(i, 1);
					this.updateLocalStorage();
					return;
				}
			}
		},
		addResultsToIstance: function(data) {
			this.results = data;
			this.resultsSorted = this.results;
			console.log(this.results.length);
			if(this.results.length == 0) this.noResults = true;
			else this.noResults = false;
			var arr = [];
			for(var i = 0; data.length > i; i++) {
				arr.push(data[i].category);
			}
			var unique = arr.filter(function(elem, index, self) {
				return index === self.indexOf(elem);
			});
			this.searchCategories = unique;
			this.searchInitializing = null;
		},
		hideSearch: function() {
			this.query = '';
			this.selected = 'all';
			this.showSearch = false;
		}
	},
	watch: {
		query: function (val) {
			if (val == 0) this.hideSearch();
		},
		items: function(val) {
			this.updateLocalStorage();
		},
		selected: function(val) {
			if(val == 'all') {
				this.resultsSorted = this.results; 
				return;
			}
			this.resultsSorted = [];
			for(var i = 0; this.results.length > i; i++) {
				if(this.results[i].category == val) this.resultsSorted.push(this.results[i]);
			}
		}
	},
	created: function () {
		//console.log(JSON.parse(localStorage.getItem("items")));
		if (localStorage.getItem("items")) {
		 	this.items = JSON.parse(localStorage.getItem("items"));
		} else {
			this.items.push({
				name: 'il tuo primo oggetto in lista',
				amount: 1
			});
		}
	}
});