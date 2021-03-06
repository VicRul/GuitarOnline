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
					var dataStr = "id_good="+id+"&ClickButton=true";
					$.ajax(
						{
							type:"GET",
							url:"CardProduct",
							data:dataStr,
							success : function(answer) {
								alert(answer);
								if(answer === "Для совершения покупки необходимо авторизоваться!") {
									window.location.href = "Authorization";
								} else {
									window.location.href = "CardProduct";
								}
							}
						}		
					);
				}
				function feedback() {
					var advantages = $("#advantages").val();
			    	var disadvantages = $("#disadvantages").val();
			    	var comment = $("#comment").val();
			        var str = "advantages="+advantages+"&disadvantages="+disadvantages+"&comment="+comment;
					$.ajax({
						type : "POST",
						url : "CardProduct",
			            data: str,
						success : function(answer) {
							if(answer === "Авторизуйтесь, чтобы добавить отзыв!") {
								alert(answer);
								window.location.href = "Authorization";
							} else if(answer === "Для добавления отзыва необходимо заполнить все поля") {
								alert(answer);
								window.location.href = "CardProduct";
							} else {
								window.location.href = "CardProduct";
							}
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
			<table>
				<tr>
					<td rowspan="4"><img width="10%" src=${product.img}></td>
					<td colspan="2"><h3>${product.title}</h3></td>
				</tr>
				<tr>
					<td colspan="2">${product.info}</td>
				</tr>
				<tr>
					<td colspan="2"><a class="good_link"
						onclick="addBasket(${product.idGood})" href='#'>В корзину</a><span
						class="result"></span></td>
				</tr>
				<tr>
					<td colspan="2">Цена: ${product.price}₽</td>
				</tr>
			</table>
			<br>
			<div class="widget">
				<h3 class="widget-title">Оставить отзыв</h3>
				<p>Плюсы</p>
				<div class="input">
					<input type="text" id="advantages">
				</div>
				<p>Минусы</p>
				<div class="input">
					<input type="text" id="disadvantages">
				</div>
				<p>Комментарий</p>
				<div class="input">
					<textarea id="comment" cols="30" rows="10"></textarea>
				</div>
				<div class="btn-wrapper">
					<button onclick=feedback()>Сохранить</button>
				</div>
			</div>
			<c:forEach items="${reviews}" var="rev">
				<table class="review">
					<tr>
						<th>${rev.fio}</th>
						<th>${rev.dateReview}</th>
					</tr>
					<tr>
						<td colspan="2"><span class="review_span">Плюсы: </span>${rev.advantages}
						</td>
					</tr>
					<tr>
						<td colspan="2"><span class="review_span">Минусы: </span>${rev.disadvantages}
						</td>
					</tr>
					<tr>
						<td colspan="2"><span class="review_span">Комментарий:
						</span>${rev.comment}</td>
					</tr>
				</table>
			</c:forEach>
		</div>
	</div>
</body>
</html>