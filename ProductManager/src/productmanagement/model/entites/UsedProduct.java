package productmanagement.model.entites;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UsedProduct extends Product {

	private Date manufactureDate;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static final DecimalFormat df = new DecimalFormat("0.00");

	public UsedProduct() {
		super();
	}

	public UsedProduct(String name, Double price, Date manufactureDate) {
		super(name, price);
		this.manufactureDate = manufactureDate;
	}

	public Date getManufactureDate() {
		return manufactureDate;
	}

	@Override
	public String priceTag() {
		String formattedDate = sdf.format(manufactureDate);
		String formattedPrice = df.format(super.getPrice());
		return String.format("%s (used) $ %s (Manufacture date: %s)", super.getName(), formattedPrice, formattedDate);
	}
}
