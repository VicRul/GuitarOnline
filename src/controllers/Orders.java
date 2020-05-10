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

		HttpSession session = request.getSession();

		if (request.getParameter("id_last_order") != null) {
			int idLastOrder = Integer.parseInt(request.getParameter("id_last_order"));
			session.setAttribute("idLastOrder", idLastOrder);
		}
		
		int idUser = (int) session.getAttribute("idUser");
		System.out.println("idUser = " + idUser);
		int idStatus = 0;
		try {
			request.setAttribute("statuses", OrdersQuery.getOrderStatuses());
			System.out.println("Статусы подготовлены");
			if (request.getParameter("id") != null) {
				idStatus = Integer.parseInt(request.getParameter("id"));
				System.out.println("idStatus = " + idStatus);
				if (idStatus > 0) {
					request.setAttribute("orders", OrdersQuery.getAllUserOrders(idUser, idStatus));
					System.out.println("Фильтрованный список заказов готов");
				} else {
					request.setAttribute("orders", OrdersQuery.getAllUserOrders(idUser));
					System.out.println("Список заказов готов");}
			} else {
				request.setAttribute("orders", OrdersQuery.getAllUserOrders(idUser));
				System.out.println("Список заказов готов");
			}
			request.setAttribute("user", UsersQuery.getInfoAboutUser(idUser));
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException | SQLException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("WEB-INF/views/Orders.jsp").forward(request, response);
	}
}