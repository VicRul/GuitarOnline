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
	public static int createOrder(int idUser)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {
		
		int idOrder = 0;

		ResultSet rs = ORM.select("orders", new String[] {"id_order"}, "where id_status = 1 and id_user = " + idUser);
		
		if (rs.next()) {
			idOrder = rs.getInt("id_order"); // Если в прошлую сессию пользователь что-то добавлял
											 //в корзину, но не подтвердил заказ и вышел, то в новую сессию
											 //продолжится работа именно с этим заказом, ранее выбранные товары будут уже в корзине
		} else {
			idOrder = ORM.findMaxId("id_order", "orders") + 1; // Иначе создаем новый заказ
			
			HashMap<String, String> values = new HashMap<String, String>();
			values.put("id_order", Integer.toString(idOrder));
			values.put("id_user", Integer.toString(idUser));
			values.put("id_status", "1");
			ORM.insert("orders", values);
		}

		rs.close();
		return idOrder;
	}

	/* Подтверждение заказа */
	public static boolean submitOrder(int idOrder)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		ResultSet rs = ORM.select("basket b inner join goods g on g.id_good = b.id_good",
				new String[] { "SUM(price * b.count)" }, "where id_order = " + idOrder);
		int sumOrder = 0;

		if (rs.next()) {
			sumOrder = rs.getInt(1);
		}

		HashMap<String, String> values = new HashMap<String, String>();
		values.put("sum_order", Integer.toString(sumOrder));
		values.put("id_status", "2");

		rs.close();
		return ORM.update("orders", values, "where id_order = " + idOrder);
	}

	/* Получаем все заказы конкретного пользователя */
	public static ArrayList<Orders> getAllUserOrders(int idUser)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		ResultSet rs = ORM.select("orders o inner join status s on s.id_status = o.id_status",
				new String[] { "id_order", "status", "sum_order" }, "where id_user = " + idUser);
		orders.clear();
		while (rs.next()) {
			int idOrder = rs.getInt("id_order");
			int sumOrder = rs.getInt("sum_order");
			String status = rs.getString("status");

			orders.add(new Orders(idOrder, sumOrder, status));
		}

		return orders;
	}

	/* Получаем все заказы конкретного пользователя в зависимости от статуса */
	public static ArrayList<Orders> getAllUserOrders(int idUser, int idStatus)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		ResultSet rs = ORM.select("orders o inner join status s on s.id_status = o.id_status",
				new String[] { "id_order", "status", "sum_order" }, "where id_user = " + idUser + " and o.id_status = " + idStatus);
		orders.clear();
		while (rs.next()) {
			int idOrder = rs.getInt("id_order");
			int sumOrder = rs.getInt("sum_order");
			String status = rs.getString("status");

			orders.add(new Orders(idOrder, sumOrder, status));
		}

		return orders;
	}
}