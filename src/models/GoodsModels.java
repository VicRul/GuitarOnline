package models;

public class GoodsModels {

	private int idModel;
	private String model;
	
	public GoodsModels(int idModel, String model) {
		this.idModel = idModel;
		this.model = model;
	}
	

	public int getIdModel() {
		return idModel;
	}

	public String getModel() {
		return model;
	}

	@Override
	public String toString() {
		return "\n" + idModel + ") " + model;
	}
}