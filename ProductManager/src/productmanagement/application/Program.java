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
	public static void main(String[] args) throws ParseException, InvalidInput {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter the number of products: ");
		int n_products = sc.nextInt();
		sc.nextLine();

		List<Product> products = new ArrayList<>();

		for (int i = 1; i <= n_products; i++) {
			try {
				System.out.println("Product #" + i + " data:");
				System.out.print("Common, used or imported (c/u/i)? ");
				char cui = Character.toUpperCase(sc.next().charAt(0));
				sc.nextLine();

				System.out.print("Name: ");
				String name = sc.nextLine();

				System.out.print("Price: ");
				Double price = sc.nextDouble();
				sc.nextLine();

				switch (cui) {
				case 'I' -> {
					System.out.print("Customs fee: ");
					Double customsFee = checkNumber(sc);
					ImportedProduct ip = new ImportedProduct(name, price, customsFee);
					products.add(ip);
				}
				case 'U' -> {
					System.out.print("Manufacture date (DD/MM/YYYY): ");
					String dateStr = sc.nextLine();
					Date date = sdf.parse(dateStr);
					UsedProduct up = new UsedProduct(name, price, date);
					products.add(up);
				}
				case 'C' -> {
					Product p = new Product(name, price);
					products.add(p);
				}
				default -> throw new InvalidInput("Invalid Option");
				}
			} catch (InvalidInput | InvalidNumber | ParseException e) {
				System.err.println(e.getMessage());
				i--;
			}
		}

		System.out.println("PRICE TAGS:");
		for (Product product : products) {
			System.out.println(product.priceTag());
		}

		sc.close();
	}

	public static Double checkNumber(Scanner scanner) throws InvalidNumber {
		try {
			return scanner.nextDouble();
		} catch (InputMismatchException e) {
			scanner.next();
			throw new InvalidNumber("Invalid Input");
		}
	}
}