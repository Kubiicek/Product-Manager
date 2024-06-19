package productmanagement.application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import productmanagement.model.entites.ImportedProduct;
import productmanagement.model.entites.Product;
import productmanagement.model.entites.UsedProduct;
import productmanagement.model.exceptions.InvalidInput;
import productmanagement.model.exceptions.InvalidNumber;

public class Program {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);

		try (Scanner sc = new Scanner(System.in)) {
			System.out.print("Enter the number of products: ");
			int nProducts = sc.nextInt();
			sc.nextLine();

			List<Product> products = new ArrayList<>();

			for (int i = 1; i <= nProducts; i++) {
				try {
					products.add(readProductData(sc, i));
				} catch (InvalidInput | InvalidNumber | ParseException e) {
					System.err.println(e.getMessage());
					i--; 
				}
			}

			System.out.println("PRICE TAGS:");
			products.forEach(p -> System.out.println(p.priceTag()));
		}
	}

	private static Product readProductData(Scanner sc, int index) throws InvalidInput, InvalidNumber, ParseException {
		System.out.println("Product #" + index + " data:");
		System.out.print("Common, used or imported (c/u/i)? ");
		char cui = Character.toUpperCase(sc.next().charAt(0));
		sc.nextLine();

		System.out.print("Name: ");
		String name = sc.nextLine();

		System.out.print("Price: ");
		double price = checkNumber(sc);
		sc.nextLine();

		return switch (cui) {
		case 'I' -> {
			System.out.print("Customs fee: ");
			double customsFee = checkNumber(sc);
			yield new ImportedProduct(name, price, customsFee);
		}
		case 'U' -> {
			System.out.print("Manufacture date (DD/MM/YYYY): ");
			String dateStr = sc.nextLine();
			Date date = sdf.parse(dateStr);
			yield new UsedProduct(name, price, date);
		}
		case 'C' -> new Product(name, price);

		default -> throw new InvalidInput("Invalid Option");
		};
	}

	public static double checkNumber(Scanner scanner) throws InvalidNumber {
		try {
			return scanner.nextDouble();
		} catch (InputMismatchException e) {
			scanner.next();
			throw new InvalidNumber("Invalid Input");
		}
	}
}