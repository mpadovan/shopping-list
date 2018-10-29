/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function editImage(id) {
	$("#form-file-" + id).toggleClass("choose-file-none");
	$("#form-file-" + id).toggleClass("form-up");
	$("#btn-back-" + id).toggleClass("btn-hide");
	$("#edit-btn-" + id).toggleClass("btn-hide");
	$("#delete-btn-" + id).toggleClass("btn-hide");
}
function newImage() {
	$("#form-new-file").toggleClass("choose-file-none");
	$("#form-new-file").toggleClass("form-up");
	$("#btn-back").toggleClass("btn-hide");
	$("#btn-add").toggleClass("btn-hide");
}
function newOtherImage() {
	$("#form-new-other-file").toggleClass("choose-file-none");
	$("#form-new-other-file").toggleClass("form-up");
	$("#btn-other-add").toggleClass("btn-hide");
	$("#btn-other-back").toggleClass("btn-hide");
}
function clearModalImages() {
	$("#images .modal-content").html('');
}

