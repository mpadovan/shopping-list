<%--
    Document   : ProductList
    Created on : 12-ago-2018, 22.41.27
    Author     : giulia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:admin pageTitle="Admin products">
	<jsp:attribute name="pageContent">
		<div class="card" style="margin-left: auto; margin-right: auto; margin-top: 10px; margin-bottom: 10px;">
			<div class="card-body">
				<h1 class="card-title">Prodotti</h1>
				<p><a href="#">Nuovo prodotto <i class="fas fa-plus-circle"></i></a></p>
				<form>
					<label class="sr-only" for="cerca">Cerca</label>
					<div class="input-group mb-2">
						<input type="text" class="form-control" id="cerca" placeholder="Cerca">
						<div class="input-group-append">
							<button class="btn btn-outline-secondary" type="submit"><i class="fas fa-search"></i></button>
						</div>
					</div>
				</form>
				<nav aria-label="Page navigation example" class="float-right">
					<ul class="pagination">
						<li class="page-item"><a class="page-link" href="#">Precedente</a></li>
						<li class="page-item"><a class="page-link" href="#">...</a></li>
						<li class="page-item"><a class="page-link" href="#">Successivo</a></li>
					</ul>
				</nav>
				<div class="table-responsive-md">
					<table class="table">
						<thead>
							<tr>
								<th>Nome prodotto</th>
								<th>Note</th>
								<th>Logo</th>
								<th>Fotografia</th>
								<th>Categoria</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Latte</td>
								<td>Generico</td>
								<td><div class="info-product text-center"><img class="logo-product" src="assets/images/milch.png" alt="Latte" title="Latte"></div></td>
								<td></td>
								<td>Latticini</td>
							</tr>
							<tr>
								<td>Pane</td>
								<td>Generico</td>
								<td><div class="info-product text-center"><img class="logo-product" src="assets/images/milch.png" alt="Latte" title="Latte"></div></td>
								<td></td>
								<td>Latticini</td>
							</tr>
							<tr>
								<td>Formaggio</td>
								<td>Generico</td>
								<td><div class="info-product text-center"><img class="logo-product" src="assets/images/milch.png" alt="Latte" title="Latte"></div></td>
								<td></td>
								<td>Latticini</td>
							</tr>
							<tr>
								<td>Cioccolata</td>
								<td>Generico</td>
								<td><div class="info-product text-center"><img class="logo-product" src="assets/images/milch.png" alt="Latte" title="Latte"></div></td>
								<td></td>
								<td>Latticini</td>
							</tr>
							<tr>
								<td>Prosciutto</td>
								<td>Generico</td>
								<td><div class="info-product text-center"><img class="logo-product" src="assets/images/milch.png" alt="Latte" title="Latte"></div></td>
								<td></td>
								<td>Latticini</td>
							</tr>
							<tr>
								<td>Caffe</td>
								<td>Generico</td>
								<td><div class="info-product text-center"><img class="logo-product" src="assets/images/milch.png" alt="Latte" title="Latte"></div></td>
								<td></td>
								<td>Latticini</td>
							</tr>
							<tr>
								<td>Acqua</td>
								<td>Generico</td>
								<td><div class="info-product text-center"><img class="logo-product" src="assets/images/milch.png" alt="Latte" title="Latte"></div></td>
								<td></td>
								<td>Latticini</td>
							</tr>
							<tr>
								<td>Cocacola</td>
								<td>Generico</td>
								<td><div class="info-product text-center"><img class="logo-product" src="assets/images/milch.png" alt="Latte" title="Latte"></div></td>
								<td></td>
								<td>Latticini</td>
							</tr>
							<tr>
								<td>Te</td>
								<td>Generico</td>
								<td><div class="info-product text-center"><img class="logo-product" src="assets/images/milch.png" alt="Latte" title="Latte"></div></td>
								<td></td>
								<td>Latticini</td>
							</tr>
							<tr>
								<td>Latte</td>
								<td>Generico</td>
								<td><div class="info-product text-center"><img class="logo-product" src="assets/images/milch.png" alt="Latte" title="Latte"></div></td>
								<td></td>
								<td>Latticini</td>
							</tr>
							<tr>
								<td>Carta igienica</td>
								<td>Generico</td>
								<td><div class="info-product text-center"><img class="logo-product" src="assets/images/milch.png" alt="Latte" title="Latte"></div></td>
								<td></td>
								<td>Latticini</td>
							</tr>
							<tr>
								<td>Brioches</td>
								<td>Generico</td>
								<td><div class="info-product text-center"><img class="logo-product" src="assets/images/milch.png" alt="Latte" title="Latte"></div></td>
								<td></td>
								<td>Latticini</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>



	</jsp:attribute>
	<jsp:attribute name="customCss">

	</jsp:attribute>
	<jsp:attribute name="customJs">

	</jsp:attribute>
</layouts:admin>
