package models;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class BaseModel {

	private static ArrayList<Goods> goods = new ArrayList<Goods>();
	private static ArrayList<Basket> basket = new ArrayList<Basket>();
	private static ArrayList<GoodsModels> models = new ArrayList<GoodsModels>();
	private static ArrayList<GoodsTypes> types = new ArrayList<GoodsTypes>();
	private static ArrayList<Users> users = new ArrayList<Users>();

	public static ArrayList<Goods> getGoods()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		ResultSet rs = ORM.select(
				"goods g inner join good_model m on g.id_model=m.id inner join good_type t on g.id_type = t.id",
				new String[] {}, "");
		goods.clear();

		while (rs.next()) {
			int idGood = rs.getInt("id_good");
			String title = rs.getString("title");
			String model = rs.getString("model");
			String type = rs.getString("type");
			int price = rs.getInt("price");
			String img = "img\\" + rs.getString("img");
			String info = rs.getString("info");
			goods.add(new Goods(idGood, title, model, type, price, img, info));
		}
		rs.close();
		return goods;
	}

	public static ArrayList<GoodsModels> getModels()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		ResultSet rs = ORM.select("good_model", new String[] {}, "");
		models.clear();

		while (rs.next()) {
			int idModel = rs.getInt("id");
			String model = rs.getString("model");
			models.add(new GoodsModels(idModel, model));
		}
		rs.close();
		return models;
	}

	public static ArrayList<GoodsTypes> getTypes()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		ResultSet rs = ORM.select("good_type", new String[] {}, "");
		types.clear();

		while (rs.next()) {
			int idType = rs.getInt("id");
			String type = rs.getString("type");
			types.add(new GoodsTypes(idType, type));
		}
		rs.close();
		return types;
	}

	public static boolean addGoodsToBasket(int idGood, int idBasket)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		ResultSet rs = ORM.select("basket", new String[] { "id_good", "count" },
				"where id_good = " + idGood + " and id_basket = " + idBasket);
		HashMap<String, String> values = new HashMap<String, String>();

		if (rs.next()) {
			int c = rs.getInt("count");
			values.put("count", Integer.toString(++c));
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

	public static boolean removeGoodsFromBasket(int idGood, int idBasket)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		return ORM.delete("basket", "where id_good = '" + idGood + "' and id_basket = '" + idBasket + "'");
	}

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

	public static boolean registrationUsers(String fio, String mail, String phone, String login, String password)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		ResultSet rs = ORM.select("Users", new String[] {},
				"where mail = '" + mail + "' or phone = '" + phone + "' or login = '" + login + "'");
		if (rs.next()) {
			rs.close();
			return false;
		}

		int id = ORM.findMaxId("id_user", "users") + 1;
		HashMap<String, String> values = new HashMap<String, String>();
		values.put("id_user", Integer.toString(id));
		values.put("fio", fio);
		values.put("mail", mail);
		values.put("phone", phone);
		values.put("login", login);
		values.put("password", password);

		rs.close();
		return ORM.insert("users", values);
	}

	public static boolean authUser(String login, String password)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		boolean isLoggedIn = false;
		ResultSet rs = ORM.select("users", new String[] {}, "where login = '" + login + "' and password = '" + password + "'");
		if (rs.next()) {
			isLoggedIn = true;
			HashMap<String, String> values = new HashMap<String, String>();
			values.putIfAbsent("auth_status", "1");
			ORM.update("users", values, "where login = '" + login + "'");
		}
		rs.close();
		return isLoggedIn;
	}

	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		System.out.println(authUser("VicRul", "VicRl"));
	}	
}