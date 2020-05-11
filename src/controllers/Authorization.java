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

		String mail = request.getParameter("mail");
		String pass = request.getParameter("pass");

		int idUser = 0, idOrder = 0;
		HttpSession session = request.getSession();

		try {
			if (UsersQuery.loggedIn(mail, pass)) {
				
				idUser = UsersQuery.findUserId(mail);
				session.setAttribute("idUser", idUser);
				
				idOrder = OrdersQuery.createOrder(idUser);
				System.out.println("idOrder = " + idOrder);
				session.setAttribute("idOrder",idOrder);
				
				if (!OrdersQuery.orderNotEmpty(idOrder)) {
					session.setAttribute("emptyOrder", 1);// Если заказ пустой - добавляем новый аттрибут сессии, чтобы
																// скрыть кнопку "оформить заказ" в корзине"
				} 
				response.getWriter().print("Добро пожаловать!");
				System.out.println("Авторизация прошла");
			} else {
				response.getWriter().print("Учетные данны, попробуйте еще раз.");
				System.out.println("Авторизация не прошла");
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException | SQLException e) {
			e.printStackTrace();
		}
	}
}