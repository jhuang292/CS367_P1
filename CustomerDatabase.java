
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

public class CustomerDatabase {

	private List<Customer> CustomerDatabase;

	/**
	 * Constructs an empty customer database
	 */
	public CustomerDatabase() {

		this.CustomerDatabase = new ArrayList<Customer>();
	}

	/**
	 * Add a customer with the given username c to the end of the database. If a
	 * customer with username c is already in the database, just return
	 */
	public void addCustomer(String c) {
		try {
			if (c == null)
				throw new java.lang.IllegalArgumentException();
			else {
				if (this.containsCustomer(c)) {
					return;
				} else {
					Customer customer = new Customer(c);
					this.CustomerDatabase.add(customer);
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.print("The customer username is null");
		}
	}

	/**
	 * Add the given product p to the wish list for customer c in the database.
	 * If customer c is not in the database throw a
	 * java.lang.IllegalArgumentException. If p is already in the wish list for
	 * customer c, just return
	 */
	public void addProduct(String c, String p) {
		try {
			if (c == null || p == null)
				throw new IllegalArgumentException();
			else {
				if (this.containsCustomer(c)) {
					if (this.hasProduct(c, p)) {
						return;
					} else {
						Iterator<Customer> itr = this.iterator();
						while (itr.hasNext()) {
							Customer tempCustomer = itr.next();
							if (tempCustomer.getUsername().equalsIgnoreCase(c)) {
								tempCustomer.getWishlist().add(p);
							}

						}
					}
				} else {
					throw new java.lang.IllegalArgumentException();
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println("the given product p to the wish list for customer c in the database is null");
		}

	}

	/**
	 * Return true iff customer c is in the database
	 */
	public boolean containsCustomer(String c) {
		boolean containsCustomer = false;
		try {
			if (c == null)
				throw new IllegalArgumentException();
			else {
				Iterator<Customer> itr = this.iterator();

				while (itr.hasNext()) {
					if (itr.next().getUsername().equalsIgnoreCase(c)) {
						containsCustomer = true;
						break;
					}
				}

			}
		} catch (IllegalArgumentException e) {
			System.out.println("customer name in the database is null");
		}
		return containsCustomer;
	}

	/**
	 * Return true iff product p appears in at least one customer's wish list in
	 * the database
	 */
	public boolean containsProduct(String p) {
		boolean containsProduct = false;
		try {
			if (p == null)
				throw new IllegalArgumentException();
			else {
				Iterator<Customer> itr = this.iterator();

				while (itr.hasNext()) {
					Iterator<String> itr2 = itr.next().getWishlist().iterator();
					while (itr2.hasNext()) {
						if (itr2.next().equalsIgnoreCase(p)) {
							containsProduct = true;
							break;
						}
					}
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println("The product name appears in the customer's wish list is null");
		}
		return containsProduct;
	}

	/**
	 * Returns true iff product p is in the wish list for customer c. If
	 * customer c is not in the database, return false
	 */
	public boolean hasProduct(String c, String p) {
		boolean hasProduct = false;
		try {
			if (c == null || p == null)
				throw new IllegalArgumentException();
			else {
				Iterator<Customer> itr = this.iterator();

				while (itr.hasNext()) {
					Customer tempCustomer = itr.next();
					if (tempCustomer.getUsername().equalsIgnoreCase(c)) {
						Iterator<String> itr2 = tempCustomer.getWishlist().iterator();
						while (itr2.hasNext()) {
							if (itr2.next().equalsIgnoreCase(p)) {
								hasProduct = true;
								break;
							}
						}
					}
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println("The product name or the customer name is null.");
		}
		return hasProduct;
	}

	/**
	 * Return the list of customers who have product p in their wish list. If
	 * product p is not in the database, return null
	 */
	public List<String> getCustomers(String p) {
		List<String> getCustomers = null;
		try {
			if (this.containsProduct(p)) {
				getCustomers = new ArrayList<String>();
				Iterator<Customer> itr = this.iterator();
				while (itr.hasNext()) {
					Customer tempCustomer = itr.next();
					if (this.hasProduct(tempCustomer.getUsername(), p)) {
						getCustomers.add(tempCustomer.getUsername());
					}
				}

			}
		} catch (IllegalArgumentException e) {
			System.out.println("The product is null");
		}
		return getCustomers;
	}

	/**
	 * Return the wish list for the customer c. If a customer c is not in the
	 * database, return null
	 */
	public List<String> getProducts(String c) {
		List<String> getProducts = null;
		try {
			if (c == null)
				throw new IllegalArgumentException();
			else {
				getProducts = new ArrayList<String>();
				if (this.containsCustomer(c)) {

					Iterator<Customer> itr = this.iterator();
					while (itr.hasNext()) {
						Customer tempCustomer = itr.next();
						if (tempCustomer.getUsername().equalsIgnoreCase(c)) {
							getProducts = tempCustomer.getWishlist();
						}
					}
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println("The customer is null");
		}
		return getProducts;
	}

	/**
	 * Return an Iterator over the Customer objects in the database. The
	 * customers should be returned in the order they were added to the database
	 * (resulting from the order in which they are in the text file)
	 */
	public Iterator<Customer> iterator() {
		Iterator<Customer> itr = this.CustomerDatabase.iterator();
		return itr;
	}

	/**
	 * Remove customer c from the database. If customer c is not in the
	 * database, return false; otherwise (i.e., the removal is successful)
	 * return true
	 */
	public boolean removeCustomer(String c) {
		boolean remove = false;
		try {
			if (c == null)
				throw new IllegalArgumentException();
			else {
				if (this.containsCustomer(c)) {
					Iterator<Customer> itr = this.iterator();
					int currPos = 0;
					while (itr.hasNext()) {
						if (itr.next().getUsername().equalsIgnoreCase(c)) {
							this.CustomerDatabase.remove(currPos);
							itr = this.iterator();
						} else {
							currPos++;
						}
					}
					remove = true;
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println("The customer is null");
		}
		return remove;
	}

	/**
	 * Remove product p from the database, i.e., remove product p from every
	 * wish list in which it appears. If product p is not in the database,
	 * return false; otherwise (i.e., the removal is successful) return true
	 */
	public boolean removeProduct(String p) {
		boolean remove = false;

		try {
			if (p == null)
				throw new IllegalArgumentException();
			else {
				if (this.containsProduct(p)) {
					List<String> tempCustomers = getCustomers(p);
					Iterator<String> itr = tempCustomers.iterator();
					while (itr.hasNext()) {
						String tempCertainCustomer = itr.next();
						Iterator<String> itr1 = getProducts(tempCertainCustomer).iterator();
						while (itr1.hasNext()) {
							String tempProduct = itr1.next();
							if (tempProduct.equalsIgnoreCase(p)) {
								itr1.remove();
							}
						}
					}
					remove = true;
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Product name is null");
		}
		return remove;
	}

	/**
	 * Return the number of customers in this database
	 */
	public int size() {
		return this.CustomerDatabase.size();
	}
}
