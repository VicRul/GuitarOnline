package models;

public class GoodsModels {

	private int idModel;
	private String model;
	
	public GoodsModels(int idModel, String model) {
		this.idModel = idModel;
		this.model = model;
	}

	@Override
	public String toString() {
		return "\n" + idModel + ") " + model;
	}
}