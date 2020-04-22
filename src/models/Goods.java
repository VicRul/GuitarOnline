package models;

public class Goods {
	
	private int idGood;
	private String title;
	private int price;
	private String img;
	private String info;
	
	public Goods(int idGood, String title, int price, String img, String info) {
		
		this.idGood = idGood;
		this.title = title;
		this.price = price;
		this.img = img;
		this.info = info;
	}

	@Override
	public String toString() {
		return "Гитара №" + idGood + "\nМодель: " + title + "\nЦена: " + price + "\nКартинка: " + img + "\nИнформация: " + info + "\n\n";
	}
}