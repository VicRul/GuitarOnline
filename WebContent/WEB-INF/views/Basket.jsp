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
<script src="http://code.jquery.com/jquery-1.8.3.js">
	
</script>
<script>
	function toTheBasket(id) {
		var dataStr = "id_last_order=" + id + "&inBasket = yes";
		$.ajax({
			type : "POST",
			url : "Orders",
			data : dataStr,
			success : function() {
				window.location.href = "Basket";
			}
		});
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
				<li><a href="Orders">Личный кабинет</a></li>
				<li><a href="Exit">Выход</a></li>
			</ul>
		</nav>
	</header>
	<div class="container">
		<div class="posts-list">
			<table>
				<tr>
					<th>№ Заказа: ${sessionScope.idCurentOrder}
				</tr>
			</table>
			<br> <br>
			<table>
				<tr>
					<th class="gPhoto">Фото</th>
					<th class="gTitle">Наименование</th>
					<th class="gPrice">Цена</th>
					<th class="gCount">Количество</th>
					<th class="gSum">Сумма</th>
					<c:if
						test="${sessionScope.idCurentOrder.equals(sessionScope.idOrder)}">
						<th class="gDel">Удалить</th>
					</c:if>
				</tr>
			</table>
			<c:forEach items="${goods}" var="good">
				<table class="orders">
					<tr>
						<td class="gPhoto"><img width="10%" src=${good.img}></td>
						<td class="gTitle">${good.title}</td>
						<td class="gPrice">${good.price}</td>
						<td class="gCount">${good.count}</td>
						<td class="gSum">${good.sum}</td>
						<c:if test="${sessionScope.idCurentOrder.equals(sessionScope.idOrder)}">
							<td class="gDel"><a class="good_link" onclick="delPos(${good.idGood}, ${sessionScope.idCurentOrder})" href='#'>Удалить</a></td>
						</c:if>
					</tr>
					<br><br>
					<c:if test="${sessionScope.idCurentOrder.equals(sessionScope.idOrder)}">
						<tr>
							<td colspan="4">Итоговая сумма:</td>
							<td>${totalSum}</td>
							<td><a class="good_link" onclick="subOrder(${sessionScope.idCurentOrder})" href='#'>Оформить заказ</a></td>
						</tr>
					</c:if>
				</table>
			</c:forEach>
		</div>
	</div>
</body>
</html>