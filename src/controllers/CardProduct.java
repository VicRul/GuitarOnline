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
import models.goods.GoodsQuery;
import models.reviews.Review;
import models.reviews.ReviewQuery;
import models.users.User;
import models.users.UsersQuery;

@WebServlet("/CardProduct")
public class CardProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();	

		int idGood = Integer.parseInt(request.getParameter("id_good"));
		try {
			request.setAttribute("reviews", ReviewQuery.getReviews(idGood));
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException | SQLException e1) {
			e1.printStackTrace();
		}

		try {
			request.setAttribute("goods", GoodsQuery.getGoods());
			request.setAttribute("reviews", ReviewQuery.getReviews(idGood));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {

			e.printStackTrace();
		}
		request.getRequestDispatcher("WEB-INF/views/Goods.jsp").forward(request, response);

		if (request.getParameter("id_good") != null) {
			idGood = Integer.parseInt(request.getParameter("id_good"));
			int idOrder = 0;
			System.out.println("idGood = " + idGood);
			if (idGood > 0) {
				if (UsersQuery.UserExist(request, response)) {
					try {
						if (BasketQuery.addGoodsToBasket(idGood, idOrder)) {
							request.setCharacterEncoding("UTF-8");
							response.setCharacterEncoding("UTF-8");
							User user = UsersQuery.getInfoAboutUser((int)session.getAttribute("idUser"));
							System.out.println(user);
							request.setAttribute("user", user);
							response.getWriter().print(
									"Товар " + GoodsQuery.getGoodNameById(idGood) + " успешно добавлен в корзину!");
							System.out.println(
									"Товар " + GoodsQuery.getGoodNameById(idGood) + " успешно добавлен в корзину!");
						}
					} catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException
							| IllegalArgumentException | InvocationTargetException | NoSuchMethodException
							| SecurityException e) {
						e.printStackTrace();
					}
				} else {
					response.getWriter().print("Для совершения покупки необходимо авторизоваться!");
					System.out.println("Для совершения покупки необходимо авторизоваться!");
					idGood = 0;
				}
			}
		}
		
	}
}