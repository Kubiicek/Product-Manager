package productmanagement.model.entites;

public class ImportedProduct extends Product{

	private Double customsFee;
	
	public ImportedProduct() {
		super();
	}

	public ImportedProduct(String name, Double price, Double customsFee) {
		super(name, price);
		this.customsFee = customsFee;
	}

	public Double getCustomsFee() {
		return customsFee;
	}
	
	@Override
	public String priceTag() {
		Double totalPrice = super.getPrice() + customsFee;
		return String.format("%s $ %.2f (Customs fee: $ %.2f)", super.getName(), totalPrice, customsFee);
	}
}
