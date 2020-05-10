package models.orders;

public class Orders {

	private int idOrder;
	private int sumOrder;
	private String status;
	private String dateOrder;
		
	public Orders(int idOrder, int sumOrder, String status, String dateOrder) {
		this.idOrder = idOrder;
		this.sumOrder = sumOrder;
		this.status = status;
		this.dateOrder = dateOrder;
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
	
	public String getDateOrder() {
		return dateOrder;
	}


	@Override
	public String toString() {
		return "\n\nOrders \nidOrder=" + idOrder + "\nsumOrder=" + sumOrder + "\nstatus=" + status;
	}
}