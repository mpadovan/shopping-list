/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
Vue.component('infoModal', {
	props: ['item'],
	data: function() {
		return {
			data: {
				name: '',
				categoryName: '',
				notes: '',
				isPrivate: '',
				photo: '',
				logo: '',
				amIAnon: ''
			},
			defaultImage: 'https://www.gardensbythebay.com.sg/etc/designs/gbb/clientlibs/images/common/not_found.jpg'
		}
	},
	mounted: function () {
		this.item = (this.item.item.category == undefined) ? this.item.item : this.item;
		this.data.name = this.item.item.name;
		this.data.categoryName = this.item.item.category.name;
		this.data.notes = this.item.item.note;
		this.data.logo = (this.item.item.logo == "null" || this.item.item.logo == undefined) ? this.defaultImage : app.path + this.item.item.logo;
		this.data.photo = (this.item.item.photography == "null" || this.item.item.photography == undefined) ? this.defaultImage : app.path + this.item.item.photography;
		$('#info-modal').modal('show');
	},
	methods: {
		hideModal: function() {
			this.$emit('close');
		}
	},
	template: `
				<div id="info-modal" class="modal" tabindex="-1" role="dialog">
					<div class="modal-dialog" role="document">
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
												<td>{{data.categoryName}}</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							</div>
						</div>
					</div>
				</div>`,
	created: function() {
		console.log(this.item);
	}
});

Vue.component('autocompleteItemComponent', {
	props: ['item'],
	template: '<li v-bind:id="\'item\' + item.sid" class="pointer autocomplete-li" style="padding-left: .5rem;">{{item.name}}<li>'
});

Vue.component('getCat', {
	props: ['cat', 'lat', 'lon'],
	data: function () {
		return {
			data: null
		};
	},
	created: function () {
		var self = this;
		console.log(self.lat + ',' + self.lon);
		$.get({
			url: app.path + 'services/geolocation/' + self.cat + '?location=' + self.lat + ',' + self.lon,
			success: function (data) {
				self.data = data;
				if (!("Notification" in window)) {
					toastr['error']("This browser does not support desktop notification");
				}
				/*
				 // Let's check whether notification permissions have already been granted
				 else if (Notification.permission === "granted") {
				 // If it's okay let's create a notification
				 var notification = new Notification(self.data[0].category + ' vicino a te!');
				 }
				 
				 // Otherwise, we need to ask the user for permission
				 else if (Notification.permission !== "denied") {
				 Notification.requestPermission(function (permission) {
				 // If the user accepts, let's create a notification
				 if (permission === "granted") {
				 var notification = new Notification(self.data[0].category + ' vicino a te!');
				 }
				 });
				 }*/
			}
		});
	},
	template: `<div class="col-md-3 mt-4"> 
					<div class="card"> 
						<div class="card-body"> 
							<div>{{ data[0].category }} vicini a te:</div> 
							<ul style="list-style: disc !important; max-height: 20rem; overflow:auto;"><li style="list-style: initial" v-for="element in data[0].response.data">{{ element.name }}</li></ul>
						</div> 
					</div> 
				</div>`
});

Vue.component('categories', {
	data: function () {
		return {
			categories: [],
			cat: null,
			geoOK: false,
			category: null
		};
	},
	template: `<div><div v-show="geoOK && category">Ricevi notifiche sui rivenditori vicini a te, hai selezionato: {{ category }}. Oppure cambia categoria: <select v-model="cat"> 
					<option v-for="category in categories" v-bind:value="category">{{ category.name }}</option> 
				</select></div><div v-show="!geoOK">Attiva la geolocalizzazione per esplorare i dintorni</div> 
				<div v-show="!category && geoOK">Ricevi notifiche sui rivenditori vicini a te, seleziona una categoria: <select v-model="cat"> 
					<option v-for="category in categories" v-bind:value="category">{{ category.name }}</option> 
				</select></div></div>`,
	watch: {
		cat: function (val) {
			localStorage.setItem("category", val.name);
			toastr['success']('Notifiche attivate per ' + val.name);
			window.location.reload();
		}
	},
	methods: {
		error: function (error) {
			this.geoOK = false;
		}
	},
	created: function () {
		var self = this;
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(function (position) {
				self.geoOK = true;
				self.category = localStorage.getItem("category");
				$.get({
					url: app.path + 'services/lists/categories',
					success: function (data) {
						data = _.sortBy(data, ['name']);
						self.categories = data;
						console.log(self.category);
						if (self.category != null) {
							self.$emit('done', {
								cat: self.category,
								lat: position.coords.latitude,
								lon: position.coords.longitude
							});
						}
					},
					error: function (error) {
						alert('Errore nel caricamento delle categorie');
						console.log(error);
					}
				});
			}, self.error, {
				enableHighAccuracy: true,
				timeout: 5000,
				maximumAge: 0
			});
		}
	}
});

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
				<td @click="infoModal"><i class="fas fa-question-circle"></i></td>
				<td @click="updateItem"><i class="fas fa-pen-square"></i></td> 
				<td @click="deleteItem"><i class="fas fa-trash"></i></td> 
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
									<div @click="infoModal" style="display:inline-block"><i class="fas fa-question-circle" style="font-size:4rem;"></i></div> 
								</div> 
							</div> 
						</div> 
					<h5 @click="callParent" class="pointer add-product-to-list" style="border-top:#333 solid 1px;">{{ capitalized }}</h5> 
					<h6>{{ item.category.name }}</h6> 
					</div> 
				</div>`
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
		item_id: null,
		categoriesResults: null,
		categoryFull: null,
		showLocals: false,
		lockSearch: false,
		item_selected_id: -2,
		loaded_list: false,
		showInfoModal: false
	},
	computed: {
		autocompleteComputed: function () {
			console.log(this.autocompleteList);
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
		searching: function () {
			if (this.query != '') {
				this.showList = false;
				this.url = this.path + 'services/products/?search=' + this.query;
				this.searchInitializing = 'search';
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
			toastr["success"](item.item.name + ' aggiunto');
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
				(data.length == 0) ? this.autocompleteList = [{
						name: 'Nessun risultato'
					}] : this.autocompleteList = data;
				this.item_selected_id = -2;
			} else {
				this.results = data;
				for (var j = 0; this.results.length > j; j++) {
					if (this.results[j].category == undefined)
						this.results[j].category = {
							name: 'Default'
						};
				}
				this.resultsSorted = this.results;
				console.log(this.results);
				if (this.results.length == 0)
					this.noResults = true;
				else
					this.noResults = false;
				var arr = [];
				for (var i = 0; data.length > i; i++) {
					if (typeof data[i].category == 'undefined') {
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
			this.lockSearch = false;
		},
		hideSearch: function () {
			this.query = '';
			this.selected = 'all';
			this.showSearch = false;
		},
		replaceQuerySearch: function (val) {
			this.lockSearch = true;
			this.query = val;
			this.searching();
		},
		showCat: function (data) {
			this.category = data.cat;
			this.lat = data.lat;
			this.lon = data.lon;
			this.showLocals = true;
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
			if (val == 0 || this.lockSearch) {
				this.showAutocomplete = false;
				this.hideSearch();
			} else {
				this.showAutocomplete = true;
				this.url = '/ShoppingList/services/products/?search=' + this.query + '&compact=true';
				this.searchInitializing = 'search';
				$('#search-input').focus();
			}
			if (app.item_selected_id != -2 && val === $('#item' + app.item_selected_id)[0].textContent) {
				app.searching();
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
				if (this.results[i].category.name == val)
					this.resultsSorted.push(this.results[i]);
			}
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
		//console.log(JSON.parse(localStorage.getItem("items")));
		if (localStorage.getItem("items")) {
			this.items = JSON.parse(localStorage.getItem("items"));
		} else {
			// this.items.push({
			// 	item: {
			// 		name: 'il tuo primo oggetto in lista',
			// 	},
			// 	amount: 1,
			// 	category: {
			// 		name: 'default'
			// 	}
			// });
		}
	},
	mounted: function() {
		this.loaded_list = true;
	}
});


$('#search-input').keydown((e) => {
	if (e.keyCode === 13 && app.item_selected_id != -2) {
		app.query = $('#item' + app.item_selected_id)[0].textContent;
	} else if (e.keyCode === 13) {
		app.searching();
	}
	if(app.item_selected_id == -2 && (e.keyCode === 40 || e.keyCode === 38)) {
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