/*jshint esversion:6 */
var Socket;

var message = {
    props: ['message'],
    data: function () {
        return {};
    },
    computed: {
        fullName: function () {
            return this.message.sender.name + ' ' + this.message.sender.lastname;
        }
    },
    template: ' <div class="alert alert-dark message-r" v-bind:class="message.isMine" role="alert"> \
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
        chat: function (val) {
            $('#chat').toggleClass('show-chat');
        },
    },
    created: function () {
        this.user = window.location.pathname.split('HomePageLogin/')[1];
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
            console.log(data);
            switch (data.operation) {
                case "2":
                    data.payload.forEach(element => {
                        if (element.listId === app.list) {
                            toastr['success']('Hai ' + element.unreadCount + ' messaggi non letti su questa lista');
                        }
                        //$('#shared-list-' + element.listId + '-badge').text(element.unreadCount).css('display', 'block');
                    });
                    break;
                case "3":
                    this.manageMessages(data.payload);
                    this.text = null;
                    break;
            }
        },
        send: function () {
            if (this.text !== null || this.text !== '' || this.text !== ' ') {
                this.message.payload.sendTime = moment().format('MMM D, YYYY hh:mm:ss A');
                this.message.payload.list.id = app.list;
                this.message.payload.sender.id = this.user;
                Socket.send(JSON.stringify(this.message));
            }
        },
        manageMessages: function (data) {
            var self = this;
            if (this.messages == null) {
                this.messages = data;
                for (var i = 0; this.messages.length > i; i++) {
                    self.arrColor.push(this.messages[i].sender.id);
                }
                var unique = self.arrColor.filter(function (elem, index, self) {
                    return index === self.indexOf(elem);
                });
                self.arrColor = [];
                for (var k = 0; unique.length > k; k++) {
                    self.arrColor.push({
                        id: unique[k],
                        color: self.getRandomColor()
                    });
                    console.log(self.arrColor[k]);
                }
                for (var j = 0; this.messages.length > j; j++) {
                    if (this.messages[j].sender.id == this.user) this.messages[j].isMine = 'message-s';
                    else this.messages[j].isMine = 'message-r';
                    for (var k = 0; self.arrColor.length > k; k++) {
                        if (self.arrColor[k].id == this.messages[j].sender.id) this.messages[j].color = self.arrColor[k].color;
                    }
                }
            } else {
                for (var j = 0; this.messages.length > j; j++) {
                    if (this.messages[j].color == undefined) {
                        for (var k = 0; self.arrColor.length > k; k++) {
                            if (self.arrColor[k].id == this.messages[j].sender.id) this.messages[j].color = self.arrColor[k].color;
                            else {
                                var element = {
                                    id: this.messages[j].sender.id,
                                    color: this.getRandomColor()
                                }
                                this.messages[j].color = elment.getRandomColor();
                                this.arrColor.push(element);
                            }
                        }
                    }
                }
            }
        }
    }
});

$(document).ready(function () {
    $('#chat').height($('#app').height());
    $('#app').resize(function () {
        $('#chat').height($('#app').height());
    });
    var output;
    var date = new Date();
    var message = {
        operation: 0,
        payload: {
            sender: {
                name: "Luigi",
                lastname: "Bianchi",
                id: 2,
                errors: {}
            },
            list: {
                id: 1,
                errors: {}
            },
            sendTime: moment().format('MMM D, YYYY h:m:s A'),
            text: "Ciao Mario, Tutto bene, e tu?",
            errors: {}
        }
    };
    Socket = new WebSocket('ws://localhost:8080/ShoppingList/restricted/messages/' + window.location.pathname.split('HomePageLogin/')[1]);
    Socket.onopen = function (evt) {
        /*Socket.send(JSON.stringify({
            operation: '1'
        }));*/
    };
    Socket.onclose = function (evt) {
        console.log(evt);
    };
    Socket.onmessage = function (evt) {
        chat.handleMessage(evt);
    };
    Socket.onerror = function (evt) {
        chat.handleMessage(evt);
    };

    function doSend() {
        writeToScreen("SENT: " + JSON.stringify(message));
        websocket.send(JSON.stringify(message));
    }
});