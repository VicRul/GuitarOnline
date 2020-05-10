<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Каталог товаров</title>
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
			function addBasket(id){
				var dataStr = "id_good="+id;
				$.ajax(
					{
						type:"POST",
						url:"GuitarsCatalog",
						data:dataStr
					}		
				);
			}
		</script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
</head>
<body>
	<header>
		<nav class="container">
			<a class="logo" href=""> <span>G</span> <span>U</span> <span>I</span>
				<span>T</span> <span>A</span> <span>R</span> <span>S</span>
			</a>
			<c:if test="${sessionScope.idUser != null}">
				<ul id="menu">
					<li><a href="">Личный кабинет</a></li>
					<li><a href="Exit">Выход</a></li>
				</ul>
				<form action="Basket" method="get" id="searchform">
					<button type="submit">
						<i class="fa fa-cart-arrow-down"></i>
					</button>
				</form>
			</c:if>
			<c:if test="${sessionScope.idUser == null}">
				<ul id="menu">
					<li><a href="Authorization">Вход</a></li>
					<li><a href="Registration">Регистрация</a></li>
				</ul>
			</c:if>
		</nav>
	</header>
	<div class="container">
		<div class="posts-list">
			<c:forEach items="${goods}" var="good">
				<table>
					<tr>
						<td rowspan="4"><img width="10%" src=${good.img}></td>
						<td colspan="2"><h3>${good.title}</h3></td>
					</tr>
					<tr>
						<td colspan="2">${good.info}</td>
					</tr>
					<tr>
						<td><a class="good_link" href='CardProduct'>Отзывы</a></td>
						<td><a class="good_link" onclick="addBasket(${good.idGood})"
							href='#'>В корзину</a><span class="result"></span></td>
					</tr>
					<tr>
						<td colspan="2">Цена: ${good.price}₽</td>
					</tr>
				</table>
			</c:forEach>
		</div>
		<aside>
			<div class="widget">
				<h3 class="widget-title">Поиск</h3>
				<form action="GuitarsCatalog" method="POST" id="sort">
					<p>Выберите производителя:</p>
					<div class="select">
						<select id="models" name="model">
							<option value=0></option>
							<c:forEach items="${models}" var="model">
								<option value=${model.idModel}>${model.model}</option>
							</c:forEach>
						</select>
					</div>
					<p>Выберите тип:</p>
					<div class="select">
						<select id="types" name="type">
							<option value=0></option>
							<c:forEach items="${types}" var="type">
								<option value=${type.idType}>${type.type}</option>
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