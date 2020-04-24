package models;

public class GoodsTypes {

	private int idType;
	private String type;
	
	public GoodsTypes(int idType, String type) {
		this.idType = idType;
		this.type = type;
	}

	@Override
	public String toString() {
		return "\n\n" + idType + ") " + type;
	}
}