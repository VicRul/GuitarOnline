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

		try {
			if (UsersQuery.loggedIn(mail, pass)) {
				HttpSession session = request.getSession();
				
				idUser = UsersQuery.findUserId(mail);
				session.setAttribute("isUser", idUser);
				
				idOrder = OrdersQuery.createOrder(idUser);
				session.setAttribute("idOrder",idOrder);
				response.getWriter().print("Добро пожаловать!");
			} else {
				response.getWriter().print("Учетные данные не корректны!");
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException | SQLException e) {
			e.printStackTrace();
		}
	}
}