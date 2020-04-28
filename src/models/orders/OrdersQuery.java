package models.orders;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import models.ORM;

public class OrdersQuery {
	
	private static ArrayList<Orders> orders = new ArrayList<Orders>();
	private static ArrayList<OrderStatuses> orderStatuses = new ArrayList<OrderStatuses>();

		/* Проверка, открыта ли корзина у пользователя. Если нет то открываем. */
	public static int openBasket(int idUser)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		int idBasket = 0;
		ResultSet rs = ORM.select("all_baskets", new String[] { "id_basket" }, "where id_user = '" + idUser + "'");

		if (rs.next()) {
			idBasket = rs.getInt("id_basket");
			rs.close();
			return idBasket;
		}

		idBasket = ORM.findMaxId("id_basket", "all_baskets") + 1;
		HashMap<String, String> values = new HashMap<String, String>();
		values.put("id_basket", Integer.toString(idBasket));
		values.put("id_user", Integer.toString(idUser));
		ORM.insert("all_baskets", values);
		rs.close();
		return idBasket;
	}

	/* Список статусов для заказа */	
	public static ArrayList<OrderStatuses> getOrderStatuses()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		ResultSet rs = ORM.select("status", new String[] {}, "");

		orderStatuses.clear();
		while (rs.next()) {

			int id = rs.getInt("id_status");
			String status = rs.getString("status");
			orderStatuses.add(new OrderStatuses(id, status));
		}

		rs.close();
		return orderStatuses;
	}

	/* Оформляем заказ */
	public static int createOrder(int idBasket)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {
		int idOrder = ORM.findMaxId("id_order", "orders") + 1;
		
		HashMap<String, String> values = new HashMap<String, String>();
		values.put("id_order", Integer.toString(idOrder));
		values.put("id_basket", Integer.toString(idBasket));
		ORM.insert("orders", values);		
		
		return idOrder;
	}
	
	/* Получаем все заказы конкретного пользователя */
}
