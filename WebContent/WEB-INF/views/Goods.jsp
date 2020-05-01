<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Каталог товаров</title>
		<style type="text/css">
		TABLE {
			text-align: center;
			width: 50%;
			height: 30%;
			border: 2px solid #000; 
			border-collapse: collapse; 
			margin: auto;
		}
		
		TD, TH {
			padding: 5px; 
			border: 1px solid #000;
			width: 5%; 
		}
		
		</style>
		<script src="http://code.jquery.com/jquery-1.8.3.js"> </script>
		<script>
			function addBasket(id){
				var dataStr = "id_good="+id;
				$.ajax(
					{
						type:"POST",
						url:"catalog",
						data:dataStr,
						success:function(msg){
							$("h1").html(msg);						
						}
					}		
				);
			}
		</script>
		<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	</head>
	<body>
		<div>
			
		</div>
		<table>
			<caption>Каталог товаров</caption>
			<tr>
				<c:forEach items="${goods}" var="good">
					<tr>
						<td><img width="100 px" src=${good.img}></td>
						<td>${good.title}</td>
						<td>${good.price} ₽</td>
						<td>${good.info}</td>
						<td><a onclick="addBasket(${good.idGood})" href='#'>Добавить в корзину</a></td>
					</tr>
				</c:forEach>
			</tr>
		</table>
		<a href="Basket" title="В корзину"><img width="100 px" src="img\buttons\to_basket.png"></a>
	</body>
</html>