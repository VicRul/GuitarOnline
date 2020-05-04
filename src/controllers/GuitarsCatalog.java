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

@WebServlet("/GuitarsCatalog")
public class GuitarsCatalog extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int idModel = 0, idType = 0;
		if (request.getParameter("id_good") == null) {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
//======================================================================================================
//Эта часть кода для сортировки товара по модели и типу. Пока что не работает.
//======================================================================================================
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
						} else {
							request.setAttribute("goods", GoodsQuery.getGoodsByModel(idModel));
						}
					} else {
						if (idType > 0) {
							request.setAttribute("goods", GoodsQuery.getGoodsByType(idType));
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
//======================================================================================================
			request.getRequestDispatcher("WEB-INF/views/Goods.jsp").forward(request, response);

		} else {
			int idGood = Integer.parseInt(request.getParameter("id_good"));
			int idOrder = 0;

			if (idGood > 0) {
				try {
					if (BasketQuery.addGoodsToBasket(idGood, idOrder)) {
						request.setCharacterEncoding("UTF-8");
						response.setCharacterEncoding("UTF-8");
						response.getWriter()
								.print("Товар " + GoodsQuery.getGoodNameById(idGood) + " успешно добавлен в корзину!");
					}
				} catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException | NoSuchMethodException
						| SecurityException e) {
					e.printStackTrace();
				}
			}
		}
	}

}