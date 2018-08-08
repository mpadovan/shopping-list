/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

Vue.component('list-item', {
	props: ['name', 'amount'],
	template: '<tr> \
				<td>{{ name }}</td> \
				<td>{{ amount }}</td> \
				<td><i class="fas fa-pen-square"></i></td> \
				<td><i class="fas fa-trash"></i></td> \
			</tr>'
});
Vue.component('search-item', {
	
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
                "id": 0,
                "name": "banane",
                "inList": false
            },
            {
                "id": 1,
                "name": "fragole",
                "inList": true
            }
       ],
	   items: [
		   {
			   name: 'Latte',
			   amount: 1
		   },
		   {
			   name: 'Biscotti',
			   amount: 2
		   },
		   {
			   name: 'Detersivo',
			   amount: 1
		   },
		   {
			   name: 'Pane',
			   amount: 1
		   },
		   {
			   name: 'Pasta',
			   amount: 2
		   },
		   {
			   name: 'Pollo',
			   amount: 1
		   },
		   {
			   name: 'Gelato',
			   amount: 1
		   },
		   {
			   name: 'Prosciutto',
			   amount: 1
		   },
		   {
			   name: 'Pomodoro',
			   amount: 1
		   },
		   {
			   name: 'Mozzarella',
			   amount: 1
		   },
		   {
			   name: 'Pizza',
			   amount: 2
		   },
		   {
			   name: 'Carne',
			   amount: 1
		   },
		   {
			   name: 'Pesce',
			   amount: 1
		   },
		   {
			   name: 'Sapone',
			   amount: 2
		   },
		   {
			   name: 'Coca Cola',
			   amount: 1
		   },
		   {
			   name: 'Acqua',
			   amount: 1
		   },
		   {
			   name: 'Sale',
			   amount: 2
		   },
		   {
			   name: 'NON SO PIU COSA METTERE',
			   amount: 1
		   }
	   ]
    },
    methods: {
        searching: function() {
            console.log(this.query);
            if(this.query.length !== 0) {
                this.showList = false;
            } else {
                this.showSearch = false;
            }
        },
        listHided: function() {
            this.showSearch = true;
        },
        searchHided: function() {
            this.showList = true;
        }
    }
});