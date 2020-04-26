package models;

public class Basket {

	private String img;
	private int id_good;
	private String title;
	private int price;
	private int count;
	
	public Basket(String img, int id_good, String title, int price, int count) {
	
		this.img = img;
		this.id_good = id_good;
		this.title = title;
		this.price = price;
		this.count = count;
	}

	
	public String getImg() {
		return img;
	}

	public int getId_good() {
		return id_good;
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

	@Override
	public String toString() {
		return "Картинка: " + img + "\nКод товара: " + id_good + "\nНаименование: " + title + "\nЦена: " + price + "\nКоличество: " + count + "\n\n";
	}
}