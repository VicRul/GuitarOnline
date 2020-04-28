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

	/* Получаем все заказы конкретного пользователя !!! НЕ РАБОТАЕТ !!!*/
	public static ArrayList<Orders> getAllUserOrders(int idUser)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {
		ResultSet rs = ORM.select("status s inner join orders o on s.id_status = o.id_status inner join all_baskets a on a.id_basket = o.id_basket",
				new String[] {"id_order", "status"}, "where a.id_user = " + idUser);
		orders.clear();
		while (rs.next()) {
			int idOrder = rs.getInt("id_order");
			ResultSet sumRs = ORM.select("goods g inner join basket b on g.id_good = b.id_good inner join all_baskets a on a.id_basket = a.id_basket", 
					new String[] {"SUM(price * b.count) as sum"}, "where a.id_user = " + idUser);
			int sumOrder = 0;
			if (sumRs.next()) {
				sumOrder = sumRs.getInt("sum");
			}
			String status = rs.getString("status");
			sumRs.close();
			orders.add(new Orders(idOrder, sumOrder, status));
		}
		
		return orders;
	}
	
	/* Получаем все заказы конкретного пользователя в зависимости от статуса */
	public static ArrayList<Orders> getAllUserOrders(int idUser, int idStatus)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {
		
		return orders;	
	}
	
	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {
		
		ArrayList<Orders> values = getAllUserOrders(1);
		
		for (Orders value : values) {
			System.out.println(value);
		}
	}
}