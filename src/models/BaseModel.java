package models;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class BaseModel {
	
	
	static ArrayList<Goods> goods = new ArrayList<Goods>();
	static ArrayList<Basket> basket = new ArrayList<Basket>();
	static ArrayList<Users> users = new ArrayList<Users>();

	public static ArrayList<Goods> getGoods()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		ResultSet rs = ORM.select("goods", new String[] {}, "");
		goods.clear();

		while (rs.next()) {
			int idGood = rs.getInt("id_good");
			String title = rs.getString("title");
			int price = rs.getInt("price");
			String img = "img\\" + rs.getString("img");
			String info = rs.getString("info");
			goods.add(new Goods(idGood, title, price, img, info));
		}
		rs.close();
		return goods;
	}

	public static boolean addGoodsToBasket(int idGood)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		ResultSet rs = ORM.select("basket", new String[] { "id_good", "count" }, "where id_good = " + idGood);
		HashMap<String, String> values = new HashMap<String, String>();
		
		if (rs.next()) {
			int c = rs.getInt("count");
			values.put("count", Integer.toString(++c));
			return ORM.update("basket", values, "where id_good = " + idGood);
		}

		values.put("id_good", Integer.toString(idGood));
		values.put("count", "1");
		rs.close();
		return ORM.insert("basket", values);
	}

	public static ArrayList<Basket> getGoodsFromBasket()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		String[] fields = new String[] { "goods.id_good", "img", "title", "price", "img", "count" };
		ResultSet rs = ORM.select("goods inner join basket on goods.id_good=basket.id_good", fields, "");
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
	
	public static boolean registrationUsers(String mail, String phone) {
		
		return false;
	}

	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		addGoodsToBasket(2);
		addGoodsToBasket(1);
		addGoodsToBasket(6);
		
		ArrayList<Basket> values = getGoodsFromBasket();

		for (Basket value : values) {
			System.out.println(value);
		}
	}
}