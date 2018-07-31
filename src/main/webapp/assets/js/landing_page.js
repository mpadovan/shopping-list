/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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