<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Личный кабинет</title>
	<link rel="stylesheet" type="text/css"
		href="https://fonts.googleapis.com/css?family=Open+Sans:400,400italic,600,600italic,700,700italic|Playfair+Display:400,700&subset=latin,cyrillic">
	<link rel="stylesheet" type="text/css"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.css">
	<link rel="stylesheet" href="styles.css">
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/prefixfree/1.0.7/prefixfree.min.js"></script>
	<script src="http://code.jquery.com/jquery-1.8.3.js"> </script>
	<script>
				function toTheBasket(id){
					var dataStr = "id_last_order="+id+"&inBasket = yes";
					$.ajax(
						{
							type:"POST",
							url:"Orders",
							data:dataStr,
							success:function(){
								window.location.href = "Basket";	
							}
						}		
					);
				}
			</script>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
</head>
<body>
	<header>
		<nav class="container">
			<a class="logo"
				href="http://localhost:8080/GuitarOnline/GuitarsCatalog"> <span>G</span>
				<span>U</span> <span>I</span> <span>T</span> <span>A</span> <span>R</span>
				<span>S</span>
			</a>
			<ul id="menu">
				<li><a href="Exit">Выход</a></li>
			</ul>
			<form action="Basket" method="get" id="searchform">
				<button type="submit">
					<i class="fa fa-cart-arrow-down"></i>
				</button>
			</form>
		</nav>
	</header>
	<div class="container">
		<div class="posts-list">
			<table>
				<tr>
					<th colspan="3">Ваши данные</tr>
				
				<tr>
					<th>Имя</th>
					<th>Почтовый ящик</th>
					<th>Номер телефона</th>
				</tr>
				<tr>
					<td>${user.fio}</td>
					<td>${user.mail}</td>
					<td>${user.phone}</td>
				</tr>
			</table>
			<br><br>
			<table>
				<tr>
					<th colspan="4">История заказов</th>
				</tr>
				<tr>
					<th>Номер</th>
					<th>Дата</th>
					<th>Статус</th>
					<th>Сумма</th>
				</tr>
			</table>
			<c:forEach items="${orders}" var="order">
				<table class="orders">
					<tr>
						<td id="numOrder">
						${status.status}<td><a class="basket_link"
							onclick="toTheBasket(${order.idOrder})" href='#'>№ - ${order.idOrder}</a></td>
						</td>
						<td id="dateOrder">${order.dateOrder}</td>
						<td id="stOrder">${order.status}</td>
						<td id="sumOrder">${order.sumOrder}</td>
					</tr>
				</table>
			</c:forEach>
		</div>
		<aside>
			<div class="widget">
				<h3 class="widget-title">Фильтр</h3>
				<form action="Orders" method="POST" id="sort">
					<p>Выберите статус заказа:</p>
					<div class="select">
						<select id="statuses" name="id">
							<option value=0></option>
							<c:forEach items="${statuses}" var="status">
								<option value=${status.id}>${status.status}</option>
							
						</c:forEach>
						</select>
					</div>
					<div class="btn-wrapper">
						<input type="submit" value="Поиск">
					</div>
				</form>
			</div>
		</aside>
	</div>
</body>
</html>