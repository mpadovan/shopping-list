<%-- 
    Document   : CategoryList
    Created on : 13-ago-2018, 12.23.16
    Author     : giulia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:admin pageTitle="Admin category list">
	<jsp:attribute name="pageContent">
		<div class="card" style="margin-left: auto; margin-right: auto; margin-top: 10px; margin-bottom: 10px;">
			<div class="card-body">
				<h1 class="card-title">Categorie di lista</h1>
				<p><a href="#">Nuovo categoria <i class="fas fa-plus-circle"></i></a></p>
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
								<th>Nome</th>
								<th>Descrizione</th>
								<th>Immagine</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Supermercato</td>
								<td>Famila</td>
								<td></td>
							</tr>
							<tr>
								<td>Farmacia</td>
								<td>Aiuto</td>
								<td></td>
							</tr>
							<tr>
								<td>Ferramenta</td>
								<td>Camera di Simone</td>
								<td></td>
							</tr>
							<tr>
								<td>Fruttivendolo</td>
								<td>verdura fresca</td>
								<td></td>
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