package main;
 


public class ProductInfo {//This class Stores information about a product's name, price, pricing type and purchasing links

	/*These lines declare private instance variables for the ProductInfo class. 
	These variables are used to store the name, link, formatted price, payment type, and price of a product.
	 The private keyword means that these variables can only be accessed within the class. */
	private String productName;
	private String productLink;
	private String productFormattedPrice;
	private String productPaymentType;
	private double productPrice;
	
	/*
	 * These lines declare a public getter and setter for the productName variable.
	 * The getter returns the value of the productName variable, and the setter sets the value of the productName variable.
	 */
	public String getProductName() {
		return productName;
	}
	 
	public void setProductName(String name) {
		this.productName = name;
	}
/*  
 * These lines declare a public getter and setter for the productLink variable
 * .The getter returns the value of the productLink variable, and the setter sets the value of the productLink variable.
 */
	public String getProductLink() {
		return productLink;
	}

	public void setProductLink(String link) {
		this.productLink = link;
	}
/*
 * 
 *These lines declare a public getter and setter for the productFormattedPrice variable.
  The getter returns the value of the productFormattedPrice variable, and the setter sets the value of the productFormattedPrice variable.
 */
	public String getProductFormattedPrice() {
		return productFormattedPrice;
	}

	public void setProductFormattedPrice(String formattedPrice) {
		this.productFormattedPrice = formattedPrice;
	}
	/*
	 * These lines declare a public getter and setter for the productPaymentType variable.
	 * The getter returns the value of the productPaymentType variable, and the setter sets the value of the productPaymentType variable.
	 */
	public String getProductPaymentType() {
		return productPaymentType;
	}
	
	public void setProductPaymentType(String type) {
		this.productPaymentType = type;
	}
	/*
	 * These lines declare a public getter and setter for the productPrice variable.
	 * The getter returns the value of the productPrice variable, and the setter sets the value of the productPrice variable.
	 */
	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double price) {
		this.productPrice = price;
	}
	
	/*This method checks whether another ProductInfo object is equal to this one. 
	It takes another ProductInfo object as an argument and returns a boolean value.
	 If the productName, productLink, and productFormattedPrice of the otherProduct object are equal to
	  those of this ProductInfo object, it returns true. Otherwise, it returns false. */

	public boolean isProductEqual(ProductInfo otherProduct) {
		if (otherProduct.getProductName().equals(this.productName) &&
			otherProduct.getProductLink().equals(this.productLink) &&
			otherProduct.getProductFormattedPrice().equals(this.productFormattedPrice)) {
			return true;
		} else {
			return false;
		}
	}
}
