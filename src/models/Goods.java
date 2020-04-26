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
	
	
	public int getIdGood() {
		return idGood;
	}

	public String getTitle() {
		return title;
	}

	public String getModel() {
		return model;
	}

	public String getType() {
		return type;
	}

	public int getPrice() {
		return price;
	}

	public String getImg() {
		return img;
	}

	public String getInfo() {
		return info;
	}

	@Override
	public String toString() {
		return "Гитара №" + idGood + "\nМодель: " + title + "\nПроизводитель: " + model + "\nТип: " + type + "\nЦена: " + price + "\nКартинка: " + img + "\nИнформация: " + info + "\n\n";
	}
}