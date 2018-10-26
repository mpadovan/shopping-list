/*jshint esversion:6 */
var Socket;

var message = {
	props: ['message'],
	computed: {
		fullName: function () {
			return this.message.sender.name + ' ' + this.message.sender.lastname;
		}
	},
	template: ' <div class="alert alert-dark" v-bind:class="message.isMine" role="alert"> \
                    <div style="font-size:.80rem" v-bind:style="{ color: message.color }">{{ fullName }} - {{ message.sendTime }}</div> \
                    <div>{{ message.text }}</div> \
                </div>'
};

var chat = new Vue({
	el: '#chat',
	components: {
		'message-component': message
	},
	data: function () {
		return {
			chat: false,
			messages: null,
			user: null,
			websocket: null,
			message: {
				operation: 0,
				payload: {
					sender: {
						name: 'gianbattista',
						lastname: 'giangenovese III',
						id: this.user,
						errors: {}
					},
					list: {
						id: app.list,
						errors: {}
					},
					sendTime: null,
					text: this.text
				}
			},
			text: null
		};
	},
	watch: {
		chat: function () {
			$('#chat').css('display', 'none');
		},
		text: function (val) {
			if (val !== null && val.length > 254) {
				this.text = _.truncate(val, {length: 254});
			}
		}
	},
	created: function () {
		this.user = window.location.pathname.split('HomePageLogin/')[1].split('/')[0];
		this.arrColor = [];
	},
	methods: {
		getRandomColor: function () {
			var letters = '0123456789ABCDEF';
			var color = '#';
			for (var i = 0; i < 6; i++) {
				color += letters[Math.floor(Math.random() * 16)];
			}
			return color;
		},
		handleMessage: function (e) {
			var data = JSON.parse(e.data);
			switch (data.operation) {
				case "2":
					data.payload.forEach(element => {
						if (element.listId === app.list) {
							toastr['success']('Hai ' + element.unreadCount + ' messaggi non letti su questa lista');
						}
						//$('#shared-list-' + element.listId + '-badge').text(element.unreadCount).css('display', 'block');
					});
					var msg = {
						operation: 1,
						payload: app.list
					};
					Socket.send(JSON.stringify(msg));
					break;
				case "3":
					this.manageMessages(data.payload);
					this.text = null;
					break;
			}
		},
		send: function () {
			if (this.text) {
				this.message.operation = 0;
				this.message.payload.sendTime = moment().format('MMM D, YYYY hh:mm:ss A');
				this.message.payload.list.id = app.list;
				this.message.payload.sender.id = this.user;
				this.message.payload.text = this.text;
				if (this.message.payload.text.length <= 255) {
					Socket.send(JSON.stringify(this.message));
				}
			}
		},
		manageMessages: function (data) {
			this.messages = data;
			for (var j = 0; this.messages.length > j; j++) {
				if (this.messages[j].sender.id == this.user)
					this.messages[j].isMine = 'message-s';
				else
					this.messages[j].isMine = 'message-r';
			}
		}
	}
});

$(document).ready(function () {
	$('#chat').height($('#app').height());
	$('#app').resize(function () {
		$('#chat').height($('#app').height());
	});
	Socket = new WebSocket('ws://' + window.location.hostname + ':8080/ShoppingList/restricted/messages/' + window.location.pathname.split('HomePageLogin/')[1].split('/')[0]);
	Socket.onopen = function (evt) {
		Socket.send(JSON.stringify({
			operation: '1',
			payload: app.list
		}));
	};
	Socket.onclose = function (evt) {
		console.log(evt);
		toastr['error']('Errore durante il caricamento della chat, ricarica la pagina per continuare');
	};
	Socket.onmessage = function (evt) {
		chat.handleMessage(evt);
	};
	Socket.onerror = function (evt) {
		chat.handleMessage(evt);
	};
	try {
		$('#message-container').addEventListener('resize', function (e) {
			console.log(e);
			$('#message-container').scrollTop($('#message-container')[0].scrollHeight);
		});
	} catch (e) {
		console.log('privata');
	}
});