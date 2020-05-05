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
			margin-top: -20%;
		}
		
		TD, TH {
			padding: 5px; 
			border: 1px solid #000;
			width: 5%; 
		}
		
		FORM {
			width: 17%;
			height: 20%;
			margin-top: 10%;
			margin-left: 2%;
			margin-bottom: 2%;
			border: 2px solid #000; 
			border-collapse: collapse;
			padding: 10px;
		}
		
		#toTheBasket{
			margin-left: 2%;
		}
		
		SELECT {
			
		}
		
		</style>
		<script src="http://code.jquery.com/jquery-1.8.3.js"> </script>
		<script>
			function addBasket(id){
				var dataStr = "id_good="+id;
				$.ajax(
					{
						type:"POST",
						url:"GuitarsCatalog",
						data:dataStr,
						success:function(msg){
							alert(msg);						
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
		  <div class="auth">
		  	<a href="">Войти/Зарегистрироваться</a>
		  </div>
		</nav>
  		</header>
  		<div class="container">
			<div>
				<form action="GuitarsCatalog" method="POST">
					<p>Выберите производителя:</p>
	        		<select id="models" name="model">
	        		<option value=0></option>
	        			<c:forEach items="${models}" var="model">
	        				<option value=${model.idModel}>${model.model}</option>
	        			</c:forEach>
	       			</select>
	       			<p>Выберите тип:</p>
	        		<select id="types" name="type">
	        		<option value=0></option>
	        			<c:forEach items="${types}" var="type">
	        				<option value=${type.idType}>${type.type}</option>
	        			</c:forEach>
	       			</select>
	       			<input type="submit" value="Поиск">
				</form>	
			</div>
			<div>
				<a id="toTheBasket" href="Basket" title="В корзину"><img width="20%" src="img\buttons\to_basket.png"></a>
			</div>
			<div>
				<table>
					<tr>
						<c:forEach items="${goods}" var="good">
							<tr>
								<td><img width="100 px" src=${good.img}></td>
								<td>${good.title}</td>
								<td>${good.price} ₽</td>
								<td>${good.info}</td>
								<td><a onclick="addBasket(${good.idGood})" href='#'><img width="100%" src="img\buttons\to_basket.png"></a></td>
							</tr>
						</c:forEach>
					</tr>
				</table>
			</div>
		</div>	
	</body>
</html>