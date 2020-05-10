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
import models.goods.*;
import models.users.User;
import models.users.UsersQuery;

@WebServlet("/GuitarsCatalog")
public class GuitarsCatalog extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int idModel = 0, idType = 0, idOrder = 0;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		if (request.getParameter("id_good") == null) {
			if (session.getAttribute("idOrder") != null) {
				idOrder = (int) session.getAttribute("idOrder");
				System.out.println("idOrder = " + idOrder);
			}

			try {
				request.setAttribute("types", GoodsQuery.getTypes());
				request.setAttribute("models", GoodsQuery.getModels());

				if (request.getParameter("model") == null) {
					if (request.getParameter("type") == null) {
						System.out.println("model and type == null");
						request.setAttribute("goods", GoodsQuery.getGoods());
					}
				} else {
					idModel = Integer.parseInt(request.getParameter("model"));
					idType = Integer.parseInt(request.getParameter("type"));
					System.out.println(idModel + " " + idType);

					if (idModel > 0) {
						if (idType > 0) {
							request.setAttribute("goods", GoodsQuery.getGoodsByTypeAndModel(idType, idModel));
							idModel = 0;
							idType = 0;
						} else {
							request.setAttribute("goods", GoodsQuery.getGoodsByModel(idModel));
							idModel = 0;
						}
					} else {
						if (idType > 0) {
							request.setAttribute("goods", GoodsQuery.getGoodsByType(idType));
							idType = 0;
						} else {
							request.setAttribute("goods", GoodsQuery.getGoods());
						}
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {

				e.printStackTrace();
			}
			request.getRequestDispatcher("WEB-INF/views/Goods.jsp").forward(request, response);
		} else {
			int idGood = Integer.parseInt(request.getParameter("id_good"));
			System.out.println("idGood = " + idGood);
			if (UsersQuery.UserExist(request, response)) {
				if (request.getParameter("inBasket") != null) {
					try {
						idOrder = (int) session.getAttribute("idOrder");
						if (BasketQuery.addGoodsToBasket(idGood, idOrder)) {
							User user = UsersQuery.getInfoAboutUser((int) session.getAttribute("idUser"));
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
				}
			} else {
				response.getWriter().print("Для совершения покупки необходимо авторизоваться!");
				System.out.println("Для совершения покупки необходимо авторизоваться!");
				idGood = 0;
			}
			if (request.getParameter("toCard") != null) {
				idGood = Integer.parseInt(request.getParameter("id_good"));
				session.setAttribute("idGood", idGood);
			}
		}
	}
}