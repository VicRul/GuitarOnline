<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Регистрация</title>
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
</head>
<body>
	<header>
		<nav class="container">
			<a class="logo" href=""> <span>G</span> <span>U</span> <span>I</span>
				<span>T</span> <span>A</span> <span>R</span> <span>S</span>
			</a>
		</nav>
	</header>
	<div class="auth_container">
			<div class="widget">
				<h3 class="widget-title">Регистрация</h3>
				<form action="Registration" method="POST" id="sort">
					<p>Введите имя:</p>
					<div class="auth_input">
						<input type="text" name="fio">
					</div>
					<p>Введите e-mail:</p>
					<div class="auth_input">
						<input type="text" name="mail">
					</div>
					<p>Введите номер телефона:</p>
					<div class="auth_input">
						<input type="text" name="phone">
					</div>
					<p>Введите пароль:</p>
					<div class="auth_input">
						<input type="password" name="password">
					</div>
					<div class="btn-wrapper">
						<input type="submit" value="Зарегистрироваться и войти">
					</div>
				</form>
			</div>
	</div>
</body>
</html>