package models.orders;

public class OrderStatuses {

	private int id;
	private String status;
	
	public OrderStatuses(int id, String status) {
		this.id = id;
		this.status = status;
	}

	
	public int getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}


	@Override
	public String toString() {
		return "\n\nOrderStatuses [id=" + id + "\nstatus=" + status + "]";
	}
	
}