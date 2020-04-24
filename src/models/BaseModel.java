package models;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class BaseModel {

	private static ArrayList<Goods> goods = new ArrayList<Goods>();
	private static ArrayList<Basket> basket = new ArrayList<Basket>();
	private static ArrayList<Users> users = new ArrayList<Users>();

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

	public static boolean addGoodsToBasket(int idGood, int idBasket)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		ResultSet rs = ORM.select("basket", new String[] { "id_good", "count" }, "where id_good = " + idGood);
		HashMap<String, String> values = new HashMap<String, String>();

		if (rs.next()) {
			int c = rs.getInt("count");
			values.put("count", Integer.toString(++c));
			return ORM.update("basket", values, "where id_good = " + idGood);
		}
		int id = ORM.findMaxId("id_purchase", "basket") + 1;
		values.put("id_purchase", Integer.toString(id));
		values.put("id_good", Integer.toString(idGood));
		values.put("count", "1");
		values.put("id_basket", Integer.toString(idBasket));
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

	public static boolean registrationUsers(String fio, String mail, String phone, String login, String password)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		int id = ORM.findMaxId("id_user", "users") + 1;
		HashMap<String, String> values = new HashMap<String, String>();
		values.put("id_user", Integer.toString(id));
		values.put("fio", fio);
		values.put("mail", mail);
		values.put("phone", phone);
		values.put("login", login);
		values.put("password", password);

		return ORM.insert("users", values);
	}

	public static ArrayList<Users> getUsers()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		ResultSet rs = ORM.select("users", new String[] {}, "");
		users.clear();
		while (rs.next()) {
			int idUser = rs.getInt("id_user");
			String fio = rs.getString("fio");
			String mail = rs.getString("mail");
			String phone = rs.getString("phone");
			String login = rs.getString("login");
			String password = rs.getString("password");
			users.add(new Users(idUser, fio, mail, phone, login, password));
		}
		rs.close();
		return users;
	}

	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		registrationUsers("Виктор", "vic.1@mail.ru", "8-800-555-55-35", "VicRul", "VicRul");
		registrationUsers("Александр", "al.1@mail.ru", "8-800-575-25-75", "AlOm", "AlOm");
		registrationUsers("Егор", "eg.1@mail.ru", "8-800-566-51-15", "EgorAmur", "EgorAmur");

		ArrayList<Users> values = getUsers();

		for (Users value : values) {
			System.out.println(value);
		}
	}
}