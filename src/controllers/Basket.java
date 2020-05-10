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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		int idOrder = (int) session.getAttribute("idOrder");
		
		if (session.getAttribute("idLastOrder") != null) {
			if (idOrder != (int) session.getAttribute("idLastOrder")) {
				idOrder = (int) session.getAttribute("idLastOrder");
			}
		}
		
		session.setAttribute("idCurentOrder", idOrder);
			
		try {
			if (request.getParameter("DelPos") != null) {
				int idGood = Integer.parseInt(request.getParameter("idGood"));
				System.out.println("idGood для удаления = " + idGood);
				BasketQuery.removePositionFromBasket(idGood, idOrder);
			}
			request.setAttribute("goods", BasketQuery.getGoodsFromBasket(idOrder));
			request.setAttribute("totalSum", BasketQuery.getTempSumOrder(idOrder));
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException | SQLException e) {
			
			e.printStackTrace();
		}
		request.getRequestDispatcher("WEB-INF/views/Basket.jsp").forward(request, response);
		if(request.getParameter("SubOrder") != null) {
			try {
				OrdersQuery.submitOrder(idOrder);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException | SQLException e) {
			
				e.printStackTrace();
			}
		}
	}
}