/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
		results: [
			{
				id: 0,
				name: "banane",
				inList: false
			},
			{
				id: 1,
				name: "fragole",
				inList: true
			},
			{
				id: 2,
				name: 'Latte',
				inList: false
			},
			{
				id: 3,
				name: 'Biscotti',
				inList: false
			},
			{
				id: 4,
				name: 'Detersivo',
				inList: false
			},
			{
				id: 5,
				name: 'Pane',
				inList: false
			},
			{
				id: 6,
				name: 'Pasta',
				inList: false
			},
			{
				id: 7,
				name: 'Pollo',
				inList: false
			},
			{
				id: 8,
				name: 'Gelato',
				inList: false
			},
			{
				id: 9,
				name: 'Prosciutto',
				inList: false
			},
			{
				id: 10,
				name: 'Pomodoro',
				inList: false
			},
			{
				id: 11,
				name: 'Mozzarella',
				inList: false
			},
			{
				id: 12,
				name: 'Pizza',
				inList: false
			},
			{
				id: 13,
				name: 'Carne',
				inList: false
			},
			{
				id: 14,
				name: 'Pesce',
				inList: false
			},
			{
				id: 15,
				name: 'Sapone',
				inList: false
			},
			{
				id: 16,
				name: 'Coca Cola',
				inList: false
			},
			{
				id: 17,
				name: 'Acqua',
				inList: false
			},
			{
				id: 18,
				name: 'Sale',
				inList: false
			},
			{
				id: 19,
				name: 'NON SO PIU COSA METTERE',
				inList: false
			}
		],
		items: [],
		item_name: null,
		item_amount: null,
		updatingItem: true
	},
	methods: {
		searching: function () {
			this.showList = false;
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
		}
	},
	watch: {
		query: function (val) {
			if (val == 0) this.showSearch = false;
		},
		items: function(val) {
			this.updateLocalStorage();
		}
	},
	created: function () {
		console.log(JSON.parse(localStorage.getItem("items")));
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