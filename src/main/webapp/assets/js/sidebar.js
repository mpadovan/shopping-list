/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {

	$(document).ready(function () {
		$('#dismiss, .overlay').on('click', function () {
			$('#sidebar').removeClass('active');
			$('#content').removeClass('active');
			$('.overlay').removeClass('active');
		});
		$('#sidebarCollapse').on('click', function () {
			$('#sidebar, #content').toggleClass('active');
			$('.collapse.in').toggleClass('in');
			$('a[aria-expanded=true]').attr('aria-expanded', 'false');
			$('.overlay').addClass('active');
		});

		$(window).resize(function () {
			if ($(window).width() < 768) {
				$('#sidebar').removeClass('active');
				$('#content').removeClass('active');
				$('.overlay').removeClass('active');
			}
		});

	});

});
