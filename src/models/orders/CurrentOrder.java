package models.orders;

public class CurrentOrder {

	private String img;
	private String title;
	private int count;
	private int price;
	private int summ;
	private int totalSum;
	
	public CurrentOrder(String img, String title, int count, int price, int summ, int totalSum) {
		this.img = img;
		this.title = title;
		this.count = count;
		this.price = price;
		this.summ = summ;
		this.totalSum = totalSum;
	}

	
	public String getImg() {
		return img;
	}

	public String getTitle() {
		return title;
	}

	public int getCount() {
		return count;
	}

	public int getPrice() {
		return price;
	}

	public int getSumm() {
		return summ;
	}

	public int getTotalSum() {
		return totalSum;
	}
}