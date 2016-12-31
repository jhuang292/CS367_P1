
///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            CS 367 Programming Assignment p1
// Files:            
// Semester:         CS367 Summer 2016
//
// Author:           Junxiong Huang
// Email:            jhuang292@wisc.edu
// CS Login:         junxiong
// Lecturer's Name:  Amanda
// Lab Section:      
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//                   CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                   If allowed, learn what PAIR-PROGRAMMING IS, 
//                   choose a partner wisely, and complete this section.
//
// Pair Partner:     Dalong Geng
// Email:            dgeng@wisc.edu
// CS Login:         dalong
// Lecturer's Name:  Amanda
// Lab Section:      
//
//                   STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR PARTNER
// Credits:          
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.*;
import java.io.File;
import java.io.IOException;

public class InteractiveDBTester {
	public static void main(String[] args) {

		// *** Add code for steps 1 - 3 of the main method ***
		CustomerDatabase customerDatabase = new CustomerDatabase();
		// Initial CustomerDatabase

		try {
			if (args.length != 1)
				throw new IllegalArgumentException();

			try {
				File inputFile = new File(args[0]);
				if (!(inputFile.exists() || inputFile.canRead()))
					throw new IOException();
				Scanner dbFileScan = null;
				dbFileScan = new Scanner(inputFile);
				while (dbFileScan.hasNextLine()) {
					String orgLine = dbFileScan.nextLine();
					String[] customerData = orgLine.split(",");
					customerDatabase.addCustomer(customerData[0].toLowerCase());
					for (int i = 1; i < customerData.length; i++) {
						customerDatabase.addProduct(customerData[0].toLowerCase(), customerData[i].toLowerCase());
					}
				}
				dbFileScan.close();
			} catch (IOException e) {
				System.out.println("Error: Cannot access input file");
				System.exit(0);
			}

		} catch (IllegalArgumentException e) {
			System.out.println("Please provide input file as command-line argument");
			System.exit(0);
		}

		Scanner stdin = new Scanner(System.in); // for reading console input
		printOptions();
		boolean done = false;
		while (!done) {
			System.out.print("Enter option ( dfhisqr ): ");
			String input = stdin.nextLine();
			input = input.toLowerCase(); // convert input to lower case

			// only do something if the user enters at least one character
			if (input.length() > 0) {
				char choice = input.charAt(0); // strip off option character
				String remainder = ""; // used to hold the remainder of input
				if (input.length() > 1) {
					// trim off any leading or trailing spaces
					remainder = input.substring(1).trim();
				}

				switch (choice) {

				case 'd':
					// *** Add code to implement this option ***
					if (customerDatabase.containsProduct(remainder)) {

						customerDatabase.removeProduct(remainder);

						System.out.println("product discontinued");

					} else {
						System.out.println("product not found");
					}
					break;

				case 'f':
					// *** Add code to implement this option ***
					if (customerDatabase.containsCustomer(remainder)) {
						if (customerDatabase.getProducts(remainder).size() == 0) {
							System.out.print("none");
						} else {
							List<String> tempProduct = customerDatabase.getProducts(remainder);
							Iterator<String> itr = tempProduct.iterator();
							System.out.print(remainder + ":");
							System.out.print(itr.next());
							while (itr.hasNext()) {
								System.out.print("," + itr.next());
							}
							System.out.print("");
						}
						System.out.println();
					} else {
						System.out.println("customer not found");
					}
					break;

				case 'h':
					printOptions();
					break;

				case 'i':
					// *** Add code to implement this option ***
					/*
					 * Method to calculate the total number of customers and
					 * products
					 */
					int numCustomers = customerDatabase.size();
					List<String> allProducts = new ArrayList<String>();
					Iterator<Customer> itr = customerDatabase.iterator();
					while (itr.hasNext()) {
						Customer temp = itr.next();
						Iterator<String> itr1 = customerDatabase.getProducts(temp.getUsername()).iterator();
						while (itr1.hasNext()) {
							String product = itr1.next();
							// When the new list is empty, add the product to
							// the list directly
							// Otherwise, create a new iterator to point to the
							// element of the list (Cannot create an iterator
							// point to an empty list)
							if (allProducts.size() == 0) {
								allProducts.add(product);
							} else {
								boolean isProductAlreadyInList = false;
								Iterator<String> tmpSearch = allProducts.iterator();
								while (tmpSearch.hasNext()) {
									String compareProduct = tmpSearch.next();
									if (compareProduct.equals(product)) {
										isProductAlreadyInList = true;
										break;
									}
								}
								if (!isProductAlreadyInList) {
									allProducts.add(product);
								}
							}
						}
					}
					System.out.println("Customers: " + numCustomers + ", " + "Products: " + allProducts.size());

					/**
					 * Method to calculate the most, least and average number of
					 * products per customer
					 */
					itr = customerDatabase.iterator();
					int mostProducts = itr.next().getWishlist().size();
					int leastProducts = mostProducts;
					int totalProducts = mostProducts;
					while (itr.hasNext()) {
						int currentProducts = itr.next().getWishlist().size();

						if (currentProducts > mostProducts) {
							mostProducts = currentProducts;
						}
						if (currentProducts < leastProducts) {
							leastProducts = currentProducts;
						}
						totalProducts += currentProducts;
					}
					itr = customerDatabase.iterator();
					int average = (int) Math.round((double) totalProducts / customerDatabase.size());
					System.out.println("# of products/customer: most " + mostProducts + ", least " + leastProducts
							+ ", average: " + average);

					/**
					 * Method to calculate the most, least and average number of
					 * customers per product
					 */
					Iterator<String> itr2 = allProducts.iterator();
					int mostCustomer = customerDatabase.getCustomers(itr2.next()).size();
					int leastCustomer = mostCustomer;
					List<String> MostPopular = new ArrayList<String>();
					while (itr2.hasNext()) {
						int currentProduct = customerDatabase.getCustomers(itr2.next()).size();
						if (currentProduct > mostCustomer) {
							mostCustomer = currentProduct;
						}
						if (currentProduct < leastCustomer) {
							leastCustomer = currentProduct;
						}
					}

					int averageCustomer = (int) Math.round((double) totalProducts / allProducts.size());
					System.out.println("# of customers/product: most " + mostCustomer + ", least " + leastCustomer
							+ ", average: " + averageCustomer);

					/**
					 * Method to get the most popular product
					 */
					Iterator<String> mostCustomerItr = allProducts.iterator();

					int numMostCustomerPerProduct = customerDatabase.getCustomers(mostCustomerItr.next()).size();
					while (mostCustomerItr.hasNext()) {
						String tmpProduct = mostCustomerItr.next();
						int currentMostProduct = customerDatabase.getCustomers(tmpProduct).size();

						if (currentMostProduct >= numMostCustomerPerProduct) {

							numMostCustomerPerProduct = currentMostProduct;
							if (numMostCustomerPerProduct == mostCustomer) {
								MostPopular.add(tmpProduct);
								MostPopular.add(",");
							}
						}
					}
					// System.out.println("Most popular product: " + MostPopular
					// + " [" + mostCustomer + "]");
					System.out.print("Most popular product: ");
					MostPopular.remove(MostPopular.size() - 1);
					Iterator<String> mostPopularItr = MostPopular.iterator();

					while (mostPopularItr.hasNext()) {
						System.out.print(mostPopularItr.next());
					}
					System.out.println(" [" + mostCustomer + "]");

					break;

				case 's':
					// *** Add code to implement this option ***
					if (customerDatabase.containsProduct(remainder)) {
						List<String> tempUsername = customerDatabase.getCustomers(remainder);
						Iterator<String> itr4 = tempUsername.iterator();
						System.out.print(remainder + ":");
						System.out.print(itr4.next());
						while (itr4.hasNext()) {
							System.out.print("," + itr4.next());
						}
						System.out.println();
					} else {
						System.out.println("product not found");
					}
					break;

				case 'q':
					done = true;
					System.out.println("quit");
					break;

				case 'r':
					// *** Add code to implement this option ***
					if (customerDatabase.containsCustomer(remainder)) {
						customerDatabase.removeCustomer(remainder);
						System.out.println("customer removed");
					} else {
						System.out.println("customer not found");
					}
					break;

				default: // ignore any unknown commands
					break;
				}
			}
		}

		stdin.close();
	}

	/**
	 * Prints the list of command options along with a short description of one.
	 * This method should not be modified.
	 */
	private static void printOptions() {
		System.out.println("d <product> - discontinue the given <product>");
		System.out.println("f <customer> - find the given <customer>");
		System.out.println("h - display this help menu");
		System.out.println("i - display information about this customer database");
		System.out.println("s <product> - search for the given <product>");
		System.out.println("q - quit");
		System.out.println("r <customer> - remove the given <customer>");
	}
}
