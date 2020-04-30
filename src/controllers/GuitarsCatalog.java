package controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.goods.*;

@WebServlet("/GuitarsCatalog")
public class GuitarsCatalog extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		try {
			ArrayList<Goods> goods = GoodsQuery.getGoods();
			String outer = "<table>";
			
			for (Goods good : goods) {
				outer += "<tr>"
							+ "<td>" 
								+ "<p class = 'title'>" + good.getTitle() + "</p>" 
								+ "<img src = '" + good.getImg() + "'>" 
								+ "<p class = 'price'>" + good.getPrice() + " ₽</p>" 
						    + "</td>" 
						    + "<td id = 'info'>" + good.getInfo() + "</td>" 
						+ "</tr>";
			}
			
			outer += "</table>";
			response.getWriter().print(outer);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
