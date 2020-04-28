package models.users;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.ORM;

public class UsersQuery {

	/* Регистрация нового пользователя */
	public static boolean registrationUsers(String fio, String mail, String phone, String password)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		ResultSet rs = ORM.select("Users", new String[] {}, "where mail = '" + mail + "' or phone = '" + phone + "'");
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
		values.put("password", password);

		rs.close();
		return ORM.insert("users", values);
	}

	/* Вход в учетную запись пользователя */
	public static boolean loggedIn(String mail, String password)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		boolean isLoggedIn = false;
		ResultSet rs = ORM.select("users", new String[] {},
				"where mail = '" + mail + "' and password = '" + password + "'");
		if (rs.next()) {
			isLoggedIn = true;
			HashMap<String, String> values = new HashMap<String, String>();
			values.put("auth_status", "1");
			ORM.update("users", values, "where mail = '" + mail + "'");
		}
		rs.close();
		return isLoggedIn;
	}

	/* Выход из учетной записи пользователя */
	public static boolean loggedOut(int idUser)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		boolean isLoggedOut = false;
		ResultSet rs = ORM.select("users", new String[] {}, "where id_user = '" + idUser + "' and auth_status = 1");

		if (rs.next()) {
			isLoggedOut = true;
			HashMap<String, String> values = new HashMap<String, String>();
			values.put("auth_status", "0");
			ORM.update("users", values, "where id_user = '" + idUser + "'");
		}
		rs.close();
		return isLoggedOut;
	}

	/* Поиск id_user по e-mail */
	public static int findUserId(String mail)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {

		int id = 0;
		ResultSet rs = ORM.select("users", new String[] { "id_user" }, "where mail = '" + mail + "'");

		if (rs.next()) {
			id = rs.getInt("id_user");
		}

		rs.close();
		return id;
	}

	/* Проверка на авторизацию пользователя */
	public static boolean UserExist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		if (session.getAttribute("id_user") != null) {
			return true;
		}
		return false;
	}
}