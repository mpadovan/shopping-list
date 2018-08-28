var message = {
    props: ['message'],
    data: function () {
        return {};
    },
    computed: {
        fullName: function() {
            return this.message.author.name + ' ' + this.message.author.lastname;
        }
    },
    template: ' <div class="alert alert-dark message-r" v-bind:class="message.isMine" role="alert"> \
                    <div style="font-size:.80rem" v-bind:style="{ color: message.color }">{{ fullName }} - {{ message.date }}</div> \
                    <div>{{ message.content }}</div> \
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
            messages: [
                {
                    author: {
                        id: 1,
                        name: 'gianbattista',
                        lastname: 'giangenovese II'
                    },
                    date: '07/07/07',
                    content: ' test testtest testtest testtest testtest testtest testtest testtest testtest test'
                },
                {
                    author: {
                        id: 4,
                        name: 'gianbattista',
                        lastname: 'giangenovese III'
                    },
                    date: '08/07/07',
                    content: ' test testtest testtest testtest testtest testtest testtest testtest testtest test'
                },
                {
                    author: {
                        id: 5,
                        name: 'gianbattista',
                        lastname: 'giangenovese II'
                    },
                    date: '07/07/07',
                    content: ' test testtest testtest testtest testtest testtest testtest testtest testtest test'
                },
                {
                    author: {
                        id: 2,
                        name: 'gianbattista',
                        lastname: 'giangenovese III'
                    },
                    date: '08/07/07',
                    content: ' test testtest testtest testtest testtest testtest testtest testtest testtest test'
                },
                {
                    author: {
                        id: 1,
                        name: 'gianbattista',
                        lastname: 'giangenovese II'
                    },
                    date: '07/07/07',
                    content: ' test testtest testtest testtest testtest testtest testtest testtest testtest test'
                },
                {
                    author: {
                        id: 3,
                        name: 'gianbattista',
                        lastname: 'giangenovese III'
                    },
                    date: '08/07/07',
                    content: ' test testtest testtest testtest testtest testtest testtest testtest testtest test'
                }
            ],
            user: null
        };
    },
    watch: {
        chat: function (val) {
            $('#chat').toggleClass('show-chat');
        }
    },
    created: function () {
        var self = this;
        this.user = window.location.pathname.split('HomePageLogin/')[1];
        var arr = [];
        for(var i = 0; this.messages.length > i; i++) {
            arr.push(this.messages[i].author.id);
        }
        var unique = arr.filter(function (elem, index, self) {
            return index === self.indexOf(elem);
        });
        arr = [];
        for(var k = 0; unique.length > k; k++) {
            arr.push({id: unique[k], color: self.getRandomColor()});
            console.log(arr[k]);
        }
        for(var j = 0; this.messages.length > j; j++) {
            if(this.messages[j].author.id == this.user) this.messages[j].isMine = 'message-s';
            else this.messages[j].isMine = 'message-r';
            for(var k = 0; arr.length > k; k++) {
                if(arr[k].id == this.messages[j].author.id) this.messages[j].color = arr[k].color;
            }
        }
    },
    methods: {
        getRandomColor: function () {
            var letters = '0123456789ABCDEF';
            var color = '#';
            for (var i = 0; i < 6; i++) {
                color += letters[Math.floor(Math.random() * 16)];
            }
            return color;
        }
    }
});

$(document).ready(function () {
    $('#chat').height($('#app').height());
    $('#app').resize(function() {
        $('#chat').height($('#app').height());
    });
});