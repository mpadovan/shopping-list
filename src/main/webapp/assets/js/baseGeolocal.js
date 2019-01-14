/* jshint esversion:6 */
Vue.component('geores', {
    props: ['data', 'ok'],
    data: function () {
        return {
            sorted: null,
            maxCat: 3,
            maxNegozi: 2
        };
    },
    template: ` <div style="margin-bottom: 8px"> 
                    <div v-if="!ok">{{ data }}</div> 
                    <div v-if="ok"><h5>Vicino a te: </h5> 
                    <div v-if="cat.response.data.length > 0" v-for="cat in sorted"> 
						<div class="card" v-for="element in cat.response.data" style="margin:5px 0 5px 0;">
							<div class="card-header">
								<div class="text-dark">{{ _.capitalize(cat.category) }}</div>
							</div>
							<div class="card-body">
								<h5 class="card-title text-dark">{{ element.name }}</h5>
								<p class="card-text text-secondary">{{ element.single_line_address }}</p>
							</div>
						</div>
                    </div></div> 
                </div>`,
    mounted: function () {
        console.log(this.data);
        tippy('#geolocBtn', {
            allowTitleHTML: true,
            arrow: true,
            placement: 'left',
            html: '#geolocContent'
        });
    },
    created: function () {
        if (this.ok) {
            this.sorted = _.cloneDeep(this.data);
            for (var i = 0; this.sorted.length > i; i++) {
                if (this.sorted[i].response.data.length > this.maxNegozi) {
                    this.sorted[i].response.data.splice(this.maxNegozi);
                    this.sorted[i].response.data.push({
                        name: (this.data[i].response.data.length - this.maxNegozi) + ' altri negozi...'
                    });
                }
            }
            if (this.sorted.length > this.maxCat) {
                this.sorted.splice(this.maxCat);
                this.sorted.push((this.data.length - this.maxCat) + ' altre categorie...');
            }
        }
    }
});

var geo = new Vue({
    el: '#geo',
    data: function () {
        return {
            msg: false,
            user: null,
            showRes: false,
            ok: false
        };
    },
    created: function () {
        var self = this;
        console.log(window.location.pathname.split('restricted/')[1].split('/')[1]);
        if (window.location.pathname.split('restricted/')[1].split('/')[1] == undefined) {
            this.msg = "OUCH! Sembra che i criceti che abbiamo ammaestrato siano in sciopero!";
            self.showRes = true;
        } else {
            this.user = window.location.pathname.split('restricted/')[1].split('/')[1];
        }
        if (navigator.geolocation && this.msg == false && this.user !== null) {
            navigator.geolocation.getCurrentPosition(function (position) {
                $.get({
                    url: '/ShoppingList/services/geolocation/restricted/' + self.user + '/?location=' + position.coords.latitude + ',' + position.coords.longitude,
                    success: function (data) {
                        self.msg = data;
                        self.ok = true;
                        self.showRes = true;
                        if (!("Notification" in window)) {
                            toastr['error']("This browser does not support desktop notification");
                        }

						/*
                        // Let's check whether notification permissions have already been granted
                        else if (Notification.permission === "granted") {
                            // If it's okay let's create a notification
                            var notification = new Notification('Ci sono ');
                        }

                        // Otherwise, we need to ask the user for permission
                        else if (Notification.permission !== "denied") {
                            Notification.requestPermission(function (permission) {
                                // If the user accepts, let's create a notification
                                if (permission === "granted") {
                                    var notification = new Notification('vicino a te!');
                                }
                            });
                        }*/
                    },
                    error: function() {
                        self.msg = 'Abilita i servizi di geolocalizzazione per avere suggerimenti';
                        self.showRes = true;
                    }
                });
            }, function (error) {
                self.msg = 'Abilita i servizi di geolocalizzazione per avere suggerimenti';
                self.showRes = true;
            });
        }
    }
});