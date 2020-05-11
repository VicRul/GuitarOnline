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

import models.basket.BasketQuery;
import models.orders.OrdersQuery;

@WebServlet("/Basket")
public class Basket extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(); // Включаем сессию

		int idOrder = (int) session.getAttribute("idOrder"); // Получаем ID заказа из текущей сессии

		if (session.getAttribute("idLastOrder") != null) {
			if (idOrder != (int) session.getAttribute("idLastOrder")) {
				idOrder = (int) session.getAttribute("idLastOrder"); // Если из личного кабинета нажали на просмотр
																		// старого заказа, то получаем
																		// аттрибут сессии idLastOrder с выбранным ID
																		// заказа, присваеваем его текущему значению
																		// idOrder и работаем с ним.
			}
		}

		session.setAttribute("idCurentOrder", idOrder); // Текущее значение значение ID заказа добавлем в новый аттрибут
														// сессии.
														// Он будет нужен для отображения элементов на странице
		if (request.getParameter("SubOrder") == null) {
			try {
				if (request.getParameter("DelPos") != null) { // Если нажали на кнопку удаления позиции
					int idGood = Integer.parseInt(request.getParameter("idGood")); // Получаем ID этой позиции
					BasketQuery.removePositionFromBasket(idGood, idOrder); // Удаляем
				}
				request.setAttribute("goods", BasketQuery.getGoodsFromBasket(idOrder)); // Выводим список товаров из
																						// корзины
				request.setAttribute("totalSum", BasketQuery.getTempSumOrder(idOrder)); // // Выводим сумму заказа
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException | SQLException e) {

				e.printStackTrace();
			}
			request.getRequestDispatcher("WEB-INF/views/Basket.jsp").forward(request, response); // ОТправляем на
																									// страницу
		} else { // Если нажали на кнопку "оформить"
			try {
				if (OrdersQuery.orderNotEmpty(idOrder)) {
					OrdersQuery.submitOrder(idOrder); // Оформляем заказ
					int idUser = (int) session.getAttribute("idUser");
					session.removeAttribute("idOrder");
					idOrder = OrdersQuery.createOrder(idUser);
					session.setAttribute("idOrder", idOrder); // Создаем заказ и записываем в аттрибут сессии
					response.getWriter().print("Оформление завершено");
				} else {
					response.getWriter().print("Оформление не возможно, карзина пуста!");
				}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException | SQLException e) {

				e.printStackTrace();
			}
		}
	}
}