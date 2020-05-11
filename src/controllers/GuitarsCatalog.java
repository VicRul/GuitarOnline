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

		HttpSession session = request.getSession(); // Получаем текущую сессию
		if (request.getParameter("id_good") == null) {
			if (session.getAttribute("idOrder") != null) {
				idOrder = (int) session.getAttribute("idOrder"); // Если пользователь авторизован получаем ID заказа
			}

			try {
				request.setAttribute("types", GoodsQuery.getTypes()); // Получаем список типов товаров и список производителей для фильтрации на странице
				request.setAttribute("models", GoodsQuery.getModels());

				if (request.getParameter("model") == null) {
					if (request.getParameter("type") == null) {
						request.setAttribute("goods", GoodsQuery.getGoods()); // Если фильтры не установлены выводим весь список товаров
					}
				} else { // Иначе, в зависимости от выбранных значений формируем списко товаров для вывода на страницу
					idModel = Integer.parseInt(request.getParameter("model"));
					idType = Integer.parseInt(request.getParameter("type"));

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
			request.getRequestDispatcher("WEB-INF/views/Goods.jsp").forward(request, response); //Выводим на страницу
		} else {
			int idGood = Integer.parseInt(request.getParameter("id_good")); // Получаем ID товара
			if (UsersQuery.UserExist(request, response)) { // Если пользователь авторизован
				if (request.getParameter("inBasket") != null) { // И нажал на кнопку "добавить в корзину"
					try {
						idOrder = (int) session.getAttribute("idOrder"); // Получаем из сессии ID заказа
						if (BasketQuery.addGoodsToBasket(idGood, idOrder)) { // Добавляем товар в корзину
							response.getWriter().print(
									"Товар " + GoodsQuery.getGoodNameById(idGood) + " успешно добавлен в корзину!"); // Выводи сообщение
						}
					} catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException
							| IllegalArgumentException | InvocationTargetException | NoSuchMethodException
							| SecurityException e) {
						e.printStackTrace();
					}
				}
			} else {
				response.getWriter().print("Для совершения покупки необходимо авторизоваться!"); // Иначе просим пройти авторизацию
				idGood = 0;
			}
			if (request.getParameter("toCard") != null) { // Если нажали на кнопку перехода в карточку товара
				idGood = Integer.parseInt(request.getParameter("id_good"));
				session.setAttribute("idGood", idGood); // Получаем ID этого товара и запоминаем в сессии
			}
		}
	}
}