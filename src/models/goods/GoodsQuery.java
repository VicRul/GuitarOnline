package models.goods;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import models.ORM;

public class GoodsQuery {

	private static ArrayList<Goods> goods = new ArrayList<Goods>();
	private static ArrayList<GoodsModels> models = new ArrayList<GoodsModels>();
	private static ArrayList<GoodsTypes> types = new ArrayList<GoodsTypes>();


	/* Получить список товаров */
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

	/* Получить список производителей */
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

	/* Получить список типов товаров */
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
}