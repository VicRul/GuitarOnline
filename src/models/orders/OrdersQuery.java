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
		int idOrder = ORM.findMaxId("id_order", "orders") + 1;

		HashMap<String, String> values = new HashMap<String, String>();
		values.put("id_order", Integer.toString(idOrder));
		values.put("id_user", Integer.toString(idUser));
		ORM.insert("orders", values);

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

	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		
		
		ArrayList<Orders> values = getAllUserOrders(1, 2);

		for (Orders value : values) {
			System.out.println(value);
		}
	}
}