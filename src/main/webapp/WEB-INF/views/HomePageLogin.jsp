<%-- 
    Document   : HomePageLogin
    Created on : 1-lug-2018, 11.44.20
    Author     : giuliapeserico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts/" %>

<layouts:base pageTitle="Landing Page">
	<jsp:attribute name="pageContent">
		<div class="container-fluid">
			<div class="row">
				<div class="col">
					<div class="card">
						<div class="card-body">
							<div class="input-group mb-3">
								<input type="text" class="form-control" placeholder="Cerca prodotto">
								<div class="input-group-append">
									<button class="btn btn-outline-secondary" type="button"><i class="fas fa-search"></i></button>
								</div>
							</div>
							<div class="float-right"><a href="NewProduct.jsp"><u>Crea prodotto</u></a></div>
						</div>
						<div class="card">
							<div class="card-body">
								<h5 class="card-title text-center">Lista corrente: <a href="InfoList.jsp"><u>Supermercato</u></a></h5>
								<div class="d-flex justify-content-end">
									<p>Chat <a href="#"><i class="far fa-comments"></i></a></p>
								</div>
								<div class="table-wrapper-2 table-responsive-md">
									<table class="table table-striped">
										<thead>
											<tr>
												<th scope="col">Nome prodotto</th>
												<th scope="col">Quantità</th>
												<th scope="col"></th>
												<th scope="col"></th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td><a href="InfoProduct.jsp">Latte Zymil</a></td>
												<td>1</td>
												<td><a href="EditProduct.jsp"><i class="fas fa-pen-square"></i></a></td>
												<td><a href="#"><i class="fas fa-trash"></i></a></td>
											</tr>
											<tr>
												<td>Biscotti</td>
												<td>2</td>
												<td><a href="#"><i class="fas fa-pen-square"></i></a></td>
												<td><a href="#"><i class="fas fa-trash"></i></a></td>
											</tr>
											<tr>
												<td>Detersivo</td>
												<td>1</td>
												<td><a href="#"><i class="fas fa-pen-square"></i></a></td>
												<td><a href="#"><i class="fas fa-trash"></i></a></td>
											</tr>
											<tr>
												<td>Pane</td>
												<td>1</td>
												<td><a href="#"><i class="fas fa-pen-square"></i></a></td>
												<td><a href="#"><i class="fas fa-trash"></i></a></td>
											</tr>
											<tr>
												<td>Pasta</td>
												<td>2</td>
												<td><a href="#"><i class="fas fa-pen-square"></i></a></td>
												<td><a href="#"><i class="fas fa-trash"></i></a></td>
											</tr>
											<tr>
												<td>Pollo</td>
												<td>1</td>
												<td><a href="#"><i class="fas fa-pen-square"></i></a></td>
												<td><a href="#"><i class="fas fa-trash"></i></a></td>
											</tr>
											<tr>
												<td>Gelato</td>
												<td>1</td>
												<td><a href="#"><i class="fas fa-pen-square"></i></a></td>
												<td><a href="#"><i class="fas fa-trash"></i></a></td>
											</tr>
											<tr>
												<td>prosciutto</td>
												<td>2</td>
												<td><a href="#"><i class="fas fa-pen-square"></i></a></td>
												<td><a href="#"><i class="fas fa-trash"></i></a></td>
											</tr>
											<tr>
												<td>Pomodoro</td>
												<td>1</td>
												<td><a href="#"><i class="fas fa-pen-square"></i></a></td>
												<td><a href="#"><i class="fas fa-trash"></i></a></td>
											</tr>
											<tr>
												<td>Mozzarella</td>
												<td>1</td>
												<td><a href="#"><i class="fas fa-pen-square"></i></a></td>
												<td><a href="#"><i class="fas fa-trash"></i></a></td>
											</tr>
											<tr>
												<td>Pizza</td>
												<td>2</td>
												<td><a href="#"><i class="fas fa-pen-square"></i></a></td>
												<td><a href="#"><i class="fas fa-trash"></i></a></td>
											</tr>
											<tr>
												<td>Carne</td>
												<td>1</td>
												<td><a href="#"><i class="fas fa-pen-square"></i></a></td>
												<td><a href="#"><i class="fas fa-trash"></i></a></td>
											</tr>
											<tr>
												<td>Pesce</td>
												<td>1</td>
												<td><a href="#"><i class="fas fa-pen-square"></i></a></td>
												<td><a href="#"><i class="fas fa-trash"></i></a></td>
											</tr>
											<tr>
												<td>Sapone</td>
												<td>2</td>
												<td><a href="#"><i class="fas fa-pen-square"></i></a></td>
												<td><a href="#"><i class="fas fa-trash"></i></a></td>
											</tr>
											<tr>
												<td>Coca Cola</td>
												<td>1</td>
												<td><a href="#"><i class="fas fa-pen-square"></i></a></td>
												<td><a href="#"><i class="fas fa-trash"></i></a></td>
											</tr>
											<tr>
												<td>Acqua</td>
												<td>1</td>
												<td><a href="#"><i class="fas fa-pen-square"></i></a></td>
												<td><a href="#"><i class="fas fa-trash"></i></a></td>
											</tr>
											<tr>
												<td>Sale</td>
												<td>2</td>
												<td><a href="#"><i class="fas fa-pen-square"></i></a></td>
												<td><a href="#"><i class="fas fa-trash"></i></a></td>
											</tr>
											<tr>
												<td>NON SO PIU COSA METTERE</td>
												<td>1</td>
												<td><a href="#"><i class="fas fa-pen-square"></i></a></td>
												<td><a href="#"><i class="fas fa-trash"></i></a></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>


					</div>

				</div>

			</div>
		</div>
	</jsp:attribute>
	<jsp:attribute name="customCss">
		<link rel="stylesheet" href="assets/css/landing_page.css">
	</jsp:attribute>
	<jsp:attribute name="customJs">

	</jsp:attribute>
</layouts:base>
