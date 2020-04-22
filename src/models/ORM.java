package models;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.HashMap;

public class ORM {
	private Connection conn;
	private ResultSet rs;
	private PreparedStatement stmt;
	private static ORM instance;
	
	private ORM() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		String url = "jdbc:mysql://localhost/guitarshop?serverTimezone=Europe/Moscow&useSSL=false";
		Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		conn = DriverManager.getConnection(url, "root", "");
		instance = this;
	}

	public static ORM getInstance()
			throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		if (instance == null) {
			new ORM();
		}
		return instance;
	}

	public static ResultSet select(String table, String[] fields, String where)
			throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		return getInstance().selectQuery(table, fields, where);
	}

	private ResultSet selectQuery(String table, String[] fields, String where) throws SQLException {

		if (conn != null && !conn.isClosed()) {
			String selectFields = "";
			int length = fields.length;
			if (length == 0) {
				selectFields = "*";
			} else {
				for (int i = 0; i < length; i++) {
					selectFields += fields[i] + (i == length - 1 ? "" : ",");
				}
			}
			String query = "SELECT " + selectFields + " FROM " + table + " " + where;
			stmt = conn.prepareStatement(query);
			return stmt.executeQuery(query);
		}

		return null;
	}

	public static boolean insert(String table, HashMap<String, String> values)
			throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		return getInstance().insertQuery(table, values);
	}

	private boolean insertQuery(String table, HashMap<String, String> values) throws SQLException {

		if (conn != null && !conn.isClosed()) {
			String sqlQuery = "INSERT INTO " + table, sqlValues = "", sqlColumns = "";
			if (values.size() > 0 && table.length() > 0) {
				int i = 0, size = values.size();
				for (String key : values.keySet()) {
					sqlColumns += key + (i < size - 1 ? "," : "");
					sqlValues += "'" + values.get(key) + "'" + (i < size - 1 ? "," : "");
					i++;
				}
				sqlQuery += "(" + sqlColumns + ") VALUES (" + sqlValues + ")";
				stmt = conn.prepareStatement(sqlQuery);
				return stmt.executeUpdate(sqlQuery) > 0;
			}
			return false;
		}

		return false;
	}

	public static boolean delete(String table, String field, int id)
			throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		return getInstance().deleteQuery(table, field, id);
	}

	private boolean deleteQuery(String table, String field, int id) throws SQLException {

		if (conn != null && !conn.isClosed()) {
			String sqlQuery = "DELETE FROM " + table + " WHERE " + field + "=" + id;
			stmt = conn.prepareStatement(sqlQuery);
			return stmt.executeUpdate(sqlQuery) > 0;
		}

		return false;
	}

	public static boolean update(String table, HashMap<String, String> values, String where)
			throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		return getInstance().updateQuery(table, values, where);
	}

	private boolean updateQuery(String table, HashMap<String, String> values, String where) throws SQLException {

		if (conn != null && !conn.isClosed()) {
			String sqlQuery = "UPDATE " + table, sqlValues = "";
			if (values.size() > 0 && table.length() > 0) {
				int i = 0, size = values.size();
				for (String key : values.keySet()) {
					sqlValues += key + "='" + values.get(key) + "'" + (i < size - 1 ? "," : "");
					i++;
				}
				sqlQuery += " SET " + sqlValues + " " + where;
				stmt = conn.prepareStatement(sqlQuery);
				return stmt.executeUpdate(sqlQuery) > 0;
			}
			return false;
		}

		return false;
	}
}