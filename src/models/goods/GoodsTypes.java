package models.goods;

public class GoodsTypes {

	private int idType;
	private String type;
	
	public GoodsTypes(int idType, String type) {
		this.idType = idType;
		this.type = type;
	}

	
	public int getIdType() {
		return idType;
	}

	public String getType() {
		return type;
	}
}