<%-- 
    Document   : provabtn
    Created on : 29-ago-2018, 15.57.13
    Author     : simon
--%>

<!DOCTYPE html>
<html>
<body>

<p>Click the button to make a BUTTON element with text.</p>

<button onclick="myFunction()">Try it</button>

<script>
function myFunction() {
    var btn = document.createElement("BUTTON");
    var t = document.createTextNode("CLICK ME");
    btn.appendChild(t);
    document.body.appendChild(btn);
}
</script>

</body>
</html>
