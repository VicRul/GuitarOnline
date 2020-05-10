package models.basket;

public class Basket {

	private String img;
	private int idOrder;
	private String title;
	private int price;
	private int count;
	private int sum;
	
	public Basket(int idOrder, String img, String title, int price, int count, int sum) {

		this.idOrder = idOrder;
		this.img = img;
		this.title = title;
		this.price = price;
		this.count = count;
		this.sum = sum;
	}

	
	public String getImg() {
		return img;
	}

	public int getIdOrder() {
		return idOrder;
	}

	public String getTitle() {
		return title;
	}

	public int getPrice() {
		return price;
	}

	public int getCount() {
		return count;
	}

	public int getSum() {
		return sum;
	}

	@Override
	public String toString() {
		return "Картинка: " + img + "\nКод заказа: " + idOrder + "\nНаименование: " + title + "\nЦена: " + price + "\nКоличество: " + count + "\nСумма:" + sum + "\n\n";
	}
}