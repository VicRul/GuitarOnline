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

@WebServlet("/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/views/Registration.jsp").forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        String fio = request.getParameter("fio");
        String mail = request.getParameter("mail");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        int idUser = 0;
        
        if(fio.equals("") || mail.equals("") || phone.equals("") || password.equals("")) {
        	response.getWriter().print("Необходимо заполнить все поля для регистрации");
        } else {
        	try {
				if (UsersQuery.registrationUsers(fio, mail, phone, password)) {
					HttpSession session = request.getSession();
					
					idUser = UsersQuery.findUserId(mail);
					session.setAttribute("idUser", idUser);
					
					int idOrder = OrdersQuery.createOrder(idUser);
					session.setAttribute("idOrder",idOrder);
					response.getWriter().print("Добро пожаловать!");
					System.out.println("Авторизация прошла");
				} else {
					response.getWriter().print("Указанный номер телефона или почтовый ящик уже зарегистрированы");
				}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException | SQLException e) {
				
				e.printStackTrace();
			}
        }	 
	}
}