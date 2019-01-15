<%@tag description="navbar tag" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>

<%-- any content can be specified here e.g.: --%>
<nav class="navbar navbar-expand-lg navbar-light bg-light" id="main-navbar">
	<div class="container-fluid">
		<button type="button" id="sidebarCollapse" class="btn navbar-btn">
			<i class="fas fa-bars"></i>
		</button>
		<div class="float-right">
			<button id="geolocBtn" style="background-color: transparent; border-style: none;"><i class="fas fa-map-marker-alt" style="font-size: 1.5em;"></i></button>
				<%-- <a href="#"><i class="fas fa-bell" style="font-size: larger"></i><span class="badge badge-danger">2</span></a> --%>
			<div style="" id="geo">
				<div id="geolocContent">
					<geores v-if="showRes" v-bind:data="msg" v-bind:ok="ok"></geores>
				</div>
			</div>
		</div>
	</div>
</nav>
