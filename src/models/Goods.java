package models;

public class Goods {
	
	private int idGood;
	private String title;
	private String model;
	private String type;
	private int price;
	private String img;
	private String info;
	
	public Goods(int idGood, String title, String model, String type, int price, String img, String info) {
		
		this.idGood = idGood;
		this.title = title;
		this.model = model;
		this.type = type;
		this.price = price;
		this.img = img;
		this.info = info;
	}

	@Override
	public String toString() {
		return "Гитара №" + idGood + "\nМодель: " + title + "\nПроизводитель: " + model + "\nТип: " + type + "\nЦена: " + price + "\nКартинка: " + img + "\nИнформация: " + info + "\n\n";
	}
}