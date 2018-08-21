var message = 

var chat = new Vue({
    el: '#chat',
    data: function() {
        return {
            chat: false
        }
    },
    watch: {
        chat: function(val) {
			$('#chat').toggleClass('show-chat');
		}
    }
});

$(document).ready(function() {
    $('#chat').height($('#app').height());
});