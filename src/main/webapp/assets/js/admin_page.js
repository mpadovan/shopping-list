/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    $("#productList").on('click', function () {
        
		
    });

	$('.nav-link').each(function(index, elem, array) {
		var href = undefined;
		if($(elem).attr("href")) {
			href = $(elem).attr("href");
		}
		
		if(location.href.includes(href)) {
			$(elem).addClass('active')
		}
	});
});

