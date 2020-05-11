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
<script src="http://code.jquery.com/jquery-1.8.3.js"></script>
<script>
	function result() {
		var fio = $("#fio").val();
    	var mail = $("#mail").val();
		var phone = $("#phone").val();
    	var password = $("#password").val();
        var str = "fio="+fio+"&mail="+mail+"&phone="+phone+"&password="+password;
		$.ajax({
			type : "POST",
			url : "Registration",
            data: str,
			success : function(answer) {
				alert(answer);
				if(answer === "Указанный номер телефона или почтовый ящик уже зарегистрированы") {
					window.location.href = "Registration";
				} else if (answer === "Необходимо заполнить все поля для регистрации") {
					window.location.href = "Registration";
				} else if (answer === "Имя может состоять только из строчных или заглавных русских, английских букв") {
					window.location.href = "Registration";
				} else if (answer === "Введите номер телефона в виде: XXX-XXX-XX-XX") {
					window.location.href = "Registration";
				} else if (answer === "Адрес электронной почты введен некорректно") {
					window.location.href = "Registration";
				} else {
					window.location.href = "GuitarsCatalog";
				}
			}
		});
	}
</script>
</head>
<body>
	<header>
		<nav class="container">
			<a class="logo"
				href="http://localhost:8080/GuitarOnline/GuitarsCatalog"> <span>G</span>
				<span>U</span> <span>I</span> <span>T</span> <span>A</span> <span>R</span>
				<span>S</span>
			</a>
		</nav>
	</header>
	<div class="auth_container">
		<div class="widget">
			<h3 class="widget-title">Регистрация</h3>
			<p>Введите имя (русские, английские строчные и заглавные буквы):</p>
			<div class="input">
				<input type="text" id="fio">
			</div>
			<p>Введите e-mail (XXX@XXX.XXX):</p>
			<div class="input">
				<input type="text" id="mail">
			</div>
			<p>Введите номер телефона (XXX-XXX-XX-XX):</p>
			<div class="input">
				<input type="text" id="phone">
			</div>
			<p>Введите пароль:</p>
			<div class="input">
				<input type="password" id="password">
			</div>
			<div class="btn-wrapper">
				<button onclick=result()>Зарегистрироваться и войти</button>
			</div>
		</div>
	</div>
</body>
</html>