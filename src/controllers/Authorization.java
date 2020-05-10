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
			session.removeAttribute("idGood");
			if (UsersQuery.loggedIn(mail, pass)) {
				
				idUser = UsersQuery.findUserId(mail);
				session.setAttribute("idUser", idUser);
				
				idOrder = OrdersQuery.createOrder(idUser);
				session.setAttribute("idOrder",idOrder);
				System.out.println("Авторизация прошла");
				response.sendRedirect("http://localhost:8080/GuitarOnline/GuitarsCatalog");
			} else {
				System.out.println("Авторизация не прошла");
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException | SQLException e) {
			e.printStackTrace();
		}
	}
}