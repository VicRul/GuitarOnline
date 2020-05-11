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

import models.orders.OrdersQuery;
import models.users.UsersQuery;

@WebServlet("/Orders")
public class Orders extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(); // Получили сессию

		if (request.getParameter("id_last_order") != null) {
			int idLastOrder = Integer.parseInt(request.getParameter("id_last_order"));
			session.setAttribute("idLastOrder", idLastOrder); // Если нажали на старый заказ в личном кабинете запоминем
																// его ID в сессии
		}

		int idUser = (int) session.getAttribute("idUser"); // Получаем пользователя
		int idStatus = 0;
		try {
			request.setAttribute("statuses", OrdersQuery.getOrderStatuses()); // Получаем список статусов заказов
			if (request.getParameter("id") != null) { // Если отфильтровали
				idStatus = Integer.parseInt(request.getParameter("id"));
				if (idStatus > 0) { // И Если ID статуса больше 0 (В БД 1: назначено, 2: в работе и т.д.). Статус 0 нужен для отмены фильтра
					request.setAttribute("orders", OrdersQuery.getAllUserOrders(idUser, idStatus)); // выводим отфильтрованный список по статусу
				} else { // Если меньше выводим все заказы
					request.setAttribute("orders", OrdersQuery.getAllUserOrders(idUser));
				}
			} else {
				request.setAttribute("orders", OrdersQuery.getAllUserOrders(idUser));// Иначе выводим все заказы
			}
			request.setAttribute("user", UsersQuery.getInfoAboutUser(idUser)); // Получаем информацию о пользователе
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException | SQLException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("WEB-INF/views/Orders.jsp").forward(request, response); // Выводим на страницу
	}
}