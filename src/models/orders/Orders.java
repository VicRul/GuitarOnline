package models.orders;

public class Orders {

	private int idOrder;
	private int sumOrder;
	private String status;
		
	public Orders(int idOrder, int sumOrder, String status) {
		this.idOrder = idOrder;
		this.sumOrder = sumOrder;
		this.status = status;
	}
	

	public int getIdOrder() {
		return idOrder;
	}

	public int getSumOrder() {
		return sumOrder;
	}

	public String getStatus() {
		return status;
	}
}