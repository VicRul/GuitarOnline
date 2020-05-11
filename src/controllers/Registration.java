package controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.orders.OrdersQuery;
import models.users.UsersQuery;

@WebServlet("/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	final static String EMAIL = "^[a-zA-Z0-9-_.]+@[a-zA-Z0-9-_]+\\.[a-zA-Z0-9]+$";
	final static String PHONE = "^\\d{3}-\\d{3}-\\d{2}-\\d{2}$";
	final static String FIO = "^[А-Яа-яA-Za-z]+$";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/views/Registration.jsp").forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        String fio = request.getParameter("fio"); // Получаем введенные значения
        String mail = request.getParameter("mail");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        int idUser = 0;
        
        if(fio.equals("") || mail.equals("") || phone.equals("") || password.equals("")) {
        	response.getWriter().print("Необходимо заполнить все поля для регистрации"); // Проверяем поля на ввод
        } else {
        	if(!testFio(fio)) {
        		response.getWriter().print("Имя может состоять только из строчных или заглавных русских, английских букв");
        	} else if(!testPhone(phone)) {
        		response.getWriter().print("Введите номер телефона в виде: XXX-XXX-XX-XX");
        	} else if(!testEmail(mail)) {
        		response.getWriter().print("Адрес электронной почты введен некорректно");
        	} else {
        		try {
    				if (UsersQuery.registrationUsers(fio, mail, "+7 " + phone, password)) { // Если все ок - регистрируем
    					HttpSession session = request.getSession(); // Получаем сессию
    					
    					idUser = UsersQuery.findUserId(mail); // Авторизируем пользователя. Добавляем в аттрибуты сессии ID пользователя и ID заказа
    					session.setAttribute("idUser", idUser);
    					
    					int idOrder = OrdersQuery.createOrder(idUser);
    					session.setAttribute("idOrder",idOrder);
    					response.getWriter().print("Добро пожаловать!"); // Выводим уведомление
    				} else {
    					response.getWriter().print("Указанный номер телефона или почтовый ящик уже зарегистрированы");
    						// Уведомляем если такие данные уже есть в БД
    				}
    			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
    					| InvocationTargetException | NoSuchMethodException | SecurityException | SQLException e) {
    				
    				e.printStackTrace();
    			}
        	}       	
        }	 
	}
	
	static boolean testEmail(String email) {
		Pattern p = Pattern.compile(EMAIL); 
		Matcher m = p.matcher(email);
		return m.matches();
		
	}
	
	static boolean testPhone(String phone) {
		Pattern p = Pattern.compile(PHONE);
		Matcher m = p.matcher(phone);
		return m.matches();
		
	}
	
	static boolean testFio(String fio) {
		Pattern p = Pattern.compile(FIO); 
		Matcher m = p.matcher(fio);
		return m.matches();
		
	}
}