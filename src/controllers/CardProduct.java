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

		HttpSession session = request.getSession(); // Включили сессию

		int idGood = 0;
		idGood = (int) session.getAttribute("idGood"); // Получили ID товара
		if (session.getAttribute("idOrder") == null) {
			if (request.getParameter("ClickButton") != null) {
				response.getWriter().print("Для совершения покупки необходимо авторизоваться!"); // Если не прошли
																									// авторизацию и
																									// нажали на кнопку
																									// "Добавить в
																									// корзину" -
																									// предупрежедение
			} else {
				try {
					request.setAttribute("reviews", ReviewQuery.getReviews(idGood)); // Подгатавливаем список отзывов
					request.setAttribute("product", GoodsQuery.getProductById(idGood)); // Получаем данные товара
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException | NoSuchMethodException
						| SecurityException | SQLException e1) {
					e1.printStackTrace();
				}

				request.getRequestDispatcher("WEB-INF/views/CardProduct.jsp").forward(request, response);
			}

		} else {
			if (request.getParameter("ClickButton") != null) {
				int idOrder = (int) session.getAttribute("idOrder"); // Если прошли авторизацию получаем ID заказа и
																		// нажали на кнопку "Добавить в корзину"
				try {
					if (BasketQuery.addGoodsToBasket(idGood, idOrder)) { // Добавляем товар в корзину
						response.getWriter()
								.print("Товар " + GoodsQuery.getGoodNameById(idGood) + " успешно добавлен в корзину!"); // Выводим
																														// сообщение
					}
				} catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException | NoSuchMethodException
						| SecurityException e) {
					e.printStackTrace();
				}
			} else {
				try {
					request.setAttribute("reviews", ReviewQuery.getReviews(idGood)); // Подгатавливаем список отзывов
					request.setAttribute("product", GoodsQuery.getProductById(idGood)); // Получаем данные товара
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException | NoSuchMethodException
						| SecurityException | SQLException e1) {
					e1.printStackTrace();
				}

				request.getRequestDispatcher("WEB-INF/views/CardProduct.jsp").forward(request, response); // Выводим на
																											// страницу
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(); // Получаем текущую сессию
		if (session.getAttribute("idUser") != null) {
			int idGood = (int) session.getAttribute("idGood"); // Получаем товар
			int idUser = (int) session.getAttribute("idUser"); // Получаем пользователя
			String advantages = request.getParameter("advantages"); // Получаем введенные данные
			String disadvantages = request.getParameter("disadvantages");
			String comment = request.getParameter("comment");

			if (advantages.equals("") || disadvantages.equals("") || comment.equals("")) {
				response.getWriter().print("Для добавления отзыва необходимо заполнить все поля");
			} else {
				try {
					ReviewQuery.addReview(idUser, idGood, advantages, disadvantages, comment); // Добавляем отзыв
					request.setAttribute("reviews", ReviewQuery.getReviews(idGood)); // Обновляем список отзывов
					request.setAttribute("product", GoodsQuery.getProductById(idGood)); // Подготавливаем инфо о товаре
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException | NoSuchMethodException
						| SecurityException | SQLException e) {
					e.printStackTrace();
				}
				request.getRequestDispatcher("WEB-INF/views/CardProduct.jsp").forward(request, response); // Выводим на
																											// страницу
			}
		} else {
			response.getWriter().print("Авторизуйтесь, чтобы добавить отзыв!"); // Если попытались добавить отзыв
																				// непройдя авторизацию - уведомление
		}
	}
}
