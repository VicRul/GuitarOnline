package models.basket;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import models.ORM;

public class BasketQuery {

	private static ArrayList<Basket> basket = new ArrayList<Basket>();

	/* Открываем корзину для авторизованного пользователя */
	public static int openBasket(int idUser)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		/*int idBasket = 0;
		ResultSet rs = ORM.select("all_baskets", new String[] { "id_basket" }, "where id_user = '" + idUser + "'");

		if (rs.next()) {
			idBasket = rs.getInt("id_basket");
			rs.close();
			return idBasket;
		}

		rs.close();*/
		
		
		int idBasket = ORM.findMaxId("id_basket", "all_baskets") + 1;
		HashMap<String, String> values = new HashMap<String, String>();
		values.put("id_basket", Integer.toString(idBasket));
		values.put("id_user", Integer.toString(idUser));
		ORM.insert("all_baskets", values);
		return idBasket;
	}

	/* Добавить товар в корзину */
	public static boolean addGoodsToBasket(int idGood, int idBasket)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		ResultSet rs = ORM.select("basket", new String[] { "id_good", "count" },
				"where id_good = " + idGood + " and id_basket = " + idBasket);
		HashMap<String, String> values = new HashMap<String, String>();

		if (rs.next()) {
			int c = rs.getInt("count");
			values.put("count", Integer.toString(++c));
			rs.close();
			return ORM.update("basket", values, "where id_good = " + idGood + " and id_basket = " + idBasket);
		}
		int id = ORM.findMaxId("id_purchase", "basket") + 1;
		values.put("id_purchase", Integer.toString(id));
		values.put("id_good", Integer.toString(idGood));
		values.put("count", "1");
		values.put("id_basket", Integer.toString(idBasket));
		rs.close();
		return ORM.insert("basket", values);
	}

	/* Удалить товар из корзины */
	public static boolean removeGoodsFromBasket(int idGood, int idBasket)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		ResultSet rs = ORM.select("basket", new String[] { "id_good", "count" },
				"where id_good = " + idGood + " and id_basket = " + idBasket);
		HashMap<String, String> values = new HashMap<String, String>();

		while (rs.next()) {
			int c = rs.getInt("count");
			if (c > 1) {
				values.put("count", Integer.toString(--c));
				rs.close();
				return ORM.update("basket", values, "where id_good = " + idGood + " and id_basket = " + idBasket);
			}
		}
		return ORM.delete("basket", "where id_good = '" + idGood + "' and id_basket = '" + idBasket + "'");
	}

	/* Получить список товаров в корзине */
	public static ArrayList<Basket> getGoodsFromBasket(int idBasket)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		String[] fields = new String[] { "goods.id_good", "img", "title", "price", "img", "count" };
		ResultSet rs = ORM.select("goods inner join basket on goods.id_good=basket.id_good", fields,
				" where id_basket = " + idBasket);
		basket.clear();
		while (rs.next()) {
			String img = "img\\" + rs.getString("img");
			int id_good = rs.getInt("id_good");
			String title = rs.getString("title");
			int price = rs.getInt("price");
			int countGoods = rs.getInt("count");
			basket.add(new Basket(img, id_good, title, price, countGoods));
		}
		rs.close();
		return basket;
	}
}