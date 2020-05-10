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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();

		int idGood = 0;
		idGood = (int) session.getAttribute("idGood");
		System.out.println("Получили idGood = " + idGood);
		if (session.getAttribute("idOrder") == null) {
			if (request.getParameter("ClickButton") != null && request.getParameter("ClickButton").equals("true")) {
				response.getWriter().print("Для совершения покупки необходимо авторизоваться!");
				System.out.println("idOrder пустой");
				System.out.println("Для совершения покупки необходимо авторизоваться!");
			} else {
				try {
					request.setAttribute("reviews", ReviewQuery.getReviews(idGood));
					System.out.println("Сформировали список комментариев");
					request.setAttribute("product", GoodsQuery.getProductById(idGood));
					System.out.println("Получили товар");
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException | NoSuchMethodException
						| SecurityException | SQLException e1) {
					e1.printStackTrace();
				}

				request.getRequestDispatcher("WEB-INF/views/CardProduct.jsp").forward(request, response);
			}

		} else {
			if (request.getParameter("ClickButton") != null && request.getParameter("ClickButton").equals("true")) {
				System.out.println("idOrder не пустой");
				int idOrder = (int) session.getAttribute("idOrder");
				try {
					if (BasketQuery.addGoodsToBasket(idGood, idOrder)) {
						response.getWriter()
								.print("Товар " + GoodsQuery.getGoodNameById(idGood) + " успешно добавлен в корзину!");
						System.out.println(
								"Товар " + GoodsQuery.getGoodNameById(idGood) + " успешно добавлен в корзину!");
					}
				} catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException | NoSuchMethodException
						| SecurityException e) {
					e.printStackTrace();
				}
			} else {
				try {
					request.setAttribute("reviews", ReviewQuery.getReviews(idGood));
					System.out.println("Сформировали список комментариев");
					request.setAttribute("product", GoodsQuery.getProductById(idGood));
					System.out.println("Получили товар");
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException | NoSuchMethodException
						| SecurityException | SQLException e1) {
					e1.printStackTrace();
				}

				request.getRequestDispatcher("WEB-INF/views/CardProduct.jsp").forward(request, response);
			}
		}
	}
}
