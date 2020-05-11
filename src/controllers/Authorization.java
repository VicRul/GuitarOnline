package controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.goods.GoodsQuery;
import models.orders.OrdersQuery;
import models.users.User;
import models.users.UsersQuery;

@WebServlet("/Authorization")
public class Authorization extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/views/Auth.jsp").forward(request,response);
		//doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		String mail = request.getParameter("mail"); // Получаем почту
		String pass = request.getParameter("pass"); // Получаем пароль

		int idUser = 0, idOrder = 0;
		HttpSession session = request.getSession(); // Получаем сессию

		try {
			if (UsersQuery.loggedIn(mail, pass)) {
				
				idUser = UsersQuery.findUserId(mail);
				session.setAttribute("idUser", idUser); // Находим ID пользователя по почте в БД и записываем в аттрибут сессии
				
				idOrder = OrdersQuery.createOrder(idUser);
				session.setAttribute("idOrder",idOrder); // Создаем заказ и записываем в аттрибут сессии
				
				response.getWriter().print("Добро пожаловать!"); // Выводим сообщение
			} else {
				response.getWriter().print("Учетные данные некорректны, попробуйте еще раз."); // Выводим сообщение
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException | SQLException e) {
			e.printStackTrace();
		}
	}
}