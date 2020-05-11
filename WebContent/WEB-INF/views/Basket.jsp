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
		function delPos(id) {
			var dataStr = "idGood=" + id + "&DelPos=yes";
			$.ajax({
				type : "GET",
				url : "Basket",
				data : dataStr,
				success : function() {
					window.location.href = "Basket";
				}
			});
		}
		function subOrder() {
			var dataStr = "SubOrder=yes";
			$.ajax({
				type : "GET",
				url : "Basket",
				data : dataStr,
				success : function() {
					window.location.href = "Orders";
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
			<br><br>
			<c:forEach items="${goods}" var="good">
				<table class="basketTable">
					<tr>
						<c:if
							test="${sessionScope.idCurentOrder.equals(sessionScope.idOrder)}">
							<td class="gPhoto" rowspan="5"><img width="10%"
								src=${good.img}></td>
						</c:if>
						<c:if
							test="${!sessionScope.idCurentOrder.equals(sessionScope.idOrder)}">
							<td class="gPhoto" rowspan="4"><img width="1%"
								src=${good.img}></td>
						</c:if>
						<th class="gTitle">Наименование:</th>
						<td class="gTitle">${good.title}</td>
					</tr>
					<tr>
						<th class="gPrice">Цена:</th>
						<td class="gPrice">${good.price}₽</td>
					</tr>
					<tr>
						<th class="gCount">Количество:</th>
						<td class="gCount">${good.count}</td>
					</tr>
					<tr>
						<th class="gSum">Сумма:</th>
						<td class="gSum">${good.sum}₽</td>
					</tr>
					<tr>
						<c:if
							test="${sessionScope.idCurentOrder.equals(sessionScope.idOrder)}">
							<td class="gDel" colspan="2"><a class="good_link"
								onclick="delPos(${good.idGood})" href='#'>Удалить</a></td>
						</c:if>
					</tr>
				</table>
			</c:forEach>
			<c:if
				test="${sessionScope.idCurentOrder.equals(sessionScope.idOrder)}">
				<c:if test="${sessionScope.emptyOrder == null}">
					<table class="totalSum">
						<tr>
							<td>Итоговая сумма:</td>
							<td>${totalSum}₽</td>
							<td><a class="good_link" onclick="subOrder()" href='#'>Оформить</a></td>
						</tr>
					</table>
				</c:if>
			</c:if>
		</div>
	</div>
</body>
</html>