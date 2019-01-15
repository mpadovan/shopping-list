/* jshint esversion:6 */


Vue.component('geores', {
    props: ['data', 'ok'],
    data: function () {
        return {
            sorted: null,
            maxCat: 4,
            maxNegozi: 4
        };
    },
	methods: {
		hideModal: function() {
			this.$emit('close');
		}
	},
    template: ` 
				<div id="geo-modal" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
					<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<div v-if="!ok">{{ data }}</div> 
								<div v-if="ok"><h3 class="text-dark">Vicino a te</h3></div>
								<button type="button" class="close" data-dismiss="modal" aria-label="Close" @click="hideModal">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								<div v-if="cat.response.data.length > 0" v-for="cat in sorted" class="row"> 
									<div class="card" v-for="element in cat.response.data" style="display:inline-block; width:48%; margin: 1%;">
										<div class="card-header">
											<div class="text-dark">{{ _.capitalize(cat.category) }}</div>
										</div>
										<img v-if="element.cover" class="card-img-top" v-bind:src="element.cover.source" alt="Card image cap">
										<div class="card-body">
											<h5 class="card-title text-dark">{{ element.name }}</h5>
											<p class="card-text text-secondary">{{ element.single_line_address }}</p>
											<a v-if="element.website != 0" v-bind:href="element.website" v-bind:class="{'disabled' : !element.website}" class="btn btn-primary text-white btn-sm" target="_blank">{{(element.website) ? 'Visita il sito' : 'Sito non disponibile'}}</a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>`,
    created: function () {
        if (this.ok) {
            this.sorted = _.cloneDeep(this.data);
            for (var i = 0; this.sorted.length > i; i++) {
                if (this.sorted[i].response.data.length > this.maxNegozi) {
                    this.sorted[i].response.data.splice(this.maxNegozi);
                    this.sorted[i].response.data.push({
                        name: (this.data[i].response.data.length - this.maxNegozi) + ' altri negozi...',
						website: 0
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

$('#geolocBtn').click(function() {
	$('#geo-modal').modal('show');
});
