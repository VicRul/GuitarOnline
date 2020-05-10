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

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		
		int idUser = (int)session.getAttribute("idUser");
		int idStatus = 0;
		try {
			request.setAttribute("statuses", OrdersQuery.getOrderStatuses());
			if (request.getParameter("idStatus") != null) {
				idStatus = Integer.parseInt(request.getParameter("idStatus"));
				request.setAttribute("orders", OrdersQuery.getAllUserOrders(idUser, idStatus));
			} else {
				request.setAttribute("orders", OrdersQuery.getAllUserOrders(idUser));
			}
			request.setAttribute("user", UsersQuery.getInfoAboutUser(idUser));
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException | SQLException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("WEB-INF/views/Orders.jsp").forward(request, response);
	}
}