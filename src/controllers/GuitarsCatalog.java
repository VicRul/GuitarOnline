package controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.basket.BasketQuery;
import models.goods.*;

@WebServlet("/GuitarsCatalog")
public class GuitarsCatalog extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("id_good") == null) {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");

			try {
				try {
					request.setAttribute("goods", GoodsQuery.getGoods());
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			request.getRequestDispatcher("WEB-INF/views/Goods.jsp").forward(request, response);
		
		} else {
			int idGood = Integer.parseInt(request.getParameter("id_good"));
			int idOrder = 0;

			if (idGood > 0) {
				try {
					if (BasketQuery.addGoodsToBasket(idGood, idOrder)) {
						request.setCharacterEncoding("UTF-8");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().print("Товар " + GoodsQuery.getGoodNameById(idGood) + " успешно добавлен в корзину!");
					}
				} catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
				}
			}
		}
	}

}