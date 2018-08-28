(function () {
    var n = false;
    if (Notification.permission === "granted") {
        // If it's okay let's create a notification
        n = true;
        //var notification = new Notification("Hi there!");
    }

    // Otherwise, we need to ask the user for permission
    else if (Notification.permission !== "denied") {
        Notification.requestPermission(function (permission) {
            // If the user accepts, let's create a notification
            if (permission === "granted") {
                n = true;
                //var notification = new Notification("Hi there!");
            }
        });
    }

    var w = Worker();

    w.onmessage(function() {
        if(n) new Notification("Hi there!");
    });
})