/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function editImage(id) {
	$("#form-file-" + id).toggleClass("choose-file-none");
	$("#btn-back-" + id).toggleClass("btn-hide");
	$("#edit-btn-" + id).toggleClass("btn-hide");
	$("#delete-btn-" + id).toggleClass("btn-hide");
}
function newImage() {
	$("#form-new-file").toggleClass("choose-file-none");
	$("#btn-back").toggleClass("btn-hide");
	$("#btn-add").toggleClass("btn-hide");
}
function clearModalImages() {
	$("#images .modal-content").html('');
}

