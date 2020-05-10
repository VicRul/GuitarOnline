package models.reviews;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import models.ORM;

public class ReviewQuery {

	private static ArrayList<Review> reviews = new ArrayList<Review>();
	
	/* Добавляем отзыв о товаре */
	public static boolean addReview(int idUser, int idGood, String advantages, String disadvantages, String comment)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {
		
		int idReview = ORM.findMaxId("id_review", "reviews") + 1;
		HashMap<String, String> values = new HashMap<String, String>();
		
		values.put("id_review", Integer.toString(idReview));
		values.put("id_user", Integer.toString(idUser));
		values.put("id_good", Integer.toString(idGood));
		values.put("advantages", advantages);
		values.put("disadvantages", disadvantages);
		values.put("comment", comment);
		
		return ORM.insert("reviews", values);
	}
	
	/* Выводим все отзывы о товаре */
	public static ArrayList<Review> getReviews(int idGood)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {
		
		ResultSet rs = ORM.select("goods g inner join reviews r on g.id_good = r.id_good inner join users u on r.id_user = u.id_user", 
								  new String[] {"u.fio", "g.title", "advantages", "disadvantages", "comment", "date_review"}, "where r.id_good = " + idGood);
		reviews.clear();
		while (rs.next()) {
			String fio = rs.getString("u.fio");
			String title = rs.getString("g.title");
			String advantages = rs.getString("advantages");
			String disadvantages = rs.getString("disadvantages");
			String comment = rs.getString("comment");
			String dateReview = rs.getString("date_review");
			
			reviews.add(new Review(title, fio, advantages, disadvantages, comment, dateReview));
		}
		return reviews;
	}
	
	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {
		
		ArrayList<Review> values = getReviews(2);
		
		for(Review value : values) {
			System.out.println(value);
		}
	}	
}