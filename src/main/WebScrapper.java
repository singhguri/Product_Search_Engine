package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * 
 * Web Scrapper class
 * 
 */

public class WebScrapper {
		// HTML element or class of Best buy website that stores the products list
		private static final String PRODUCT_LIST_ITEMS = "col-xs-12_198le col-sm-4_13E9O col-lg-3_ECF8k x-productListItem productLine_2N9kG";

		// HTML element or class of Best buy website that contains product name
		private static final String PRODUCT_NAMES_INFO = "div.productItemName_3IZ3c";

		// HTML element or class of Best buy website that stores pricing-related information
		private static final String PRODUCT_PRICES_INFO = "div.productPricingContainer_3gTS3";

		// HTML element or class of Best buy website that contains the product's price
		private static final String PRODUCT_INDIVIDUAL_PRICE_INFO = "span[data-automation=product-price] > div";


	// Function to check if a ProductInfo's pricing is monthly or one time pay option.
	// Input is ProductInfo class
	// its sets the payment type of the ProductInfo in the ProductInfo class.
	public static void checkProductInfoType(ProductInfo ProductInfo) {
		if (ProductInfo.getProductName().contains("Monthly"))
			ProductInfo.setProductPaymentType("Monthly - 24 months");
		else
			ProductInfo.setProductPaymentType("Unlocked - One time pay");
	}

	// Function to check if ProductInfo already exists in the ProductInfo list
	public static boolean ProductInfoExists(List<ProductInfo> ProductInfos, ProductInfo ProductInfo) {
		for (ProductInfo element : ProductInfos) {
			
			if (element.getProductName() == ProductInfo.getProductName()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	// Scrap items page by page
	public static List<ProductInfo> getProductItemsInfo(String initialUrl, List<ProductInfo> products, int pagesToSearch) {
		// Append the url with page 0
		initialUrl = initialUrl + "/?pages=0";
		initialUrl = initialUrl.substring(0, initialUrl.length() - 1);
		initialUrl = initialUrl + Integer.toString(1);

			// Extracting the ProductInfo details like name, price, type, and link to buy of
			// ProductInfos using the Jsoup library to scrap data from the URLs
			try {
				Document websiteHTMLDoc = Jsoup.connect(initialUrl).get();
				Elements items = websiteHTMLDoc.getElementsByClass(PRODUCT_LIST_ITEMS);

				for (Element item : items) {
					ProductInfo newProductInfo = new ProductInfo();
					Elements name = item.select(PRODUCT_NAMES_INFO);

					if (!name.isEmpty()) {
						newProductInfo.setProductName(name.text());
					}

					Elements priceData = item.select(PRODUCT_PRICES_INFO);
					Element ProductInfoPrice = priceData.select(PRODUCT_INDIVIDUAL_PRICE_INFO).first();

					if (!priceData.isEmpty()) {
						double priceValue = Double.parseDouble(ProductInfoPrice.text().replace(",", "").substring(1));
						newProductInfo.setProductFormattedPrice(ProductInfoPrice.text());
						newProductInfo.setProductPrice(priceValue);
					}

					Elements ProductInfoLink = item.select("a[href]");
					if (!ProductInfoLink.isEmpty()) {
						newProductInfo.setProductLink(initialUrl + ProductInfoLink.first().attr("href"));
					}
					checkProductInfoType(newProductInfo);
					if (!ProductInfoExists(products, newProductInfo)) {
						products.add(newProductInfo);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		return (products);
	}

	public static void main(String[] args) {
		List<ProductInfo> products = new ArrayList<>();

		String url = "https://www.bestbuy.ca/en-ca/category/best-buy-mobile/20006";
		products = getProductItemsInfo(url, products, 2);

		for (ProductInfo p : products) {
			System.out.println("Item: " + p.getProductName());
			System.out.println("Price: " + p.getProductFormattedPrice());
			System.out.println("Financing type: " + p.getProductPaymentType());
			System.out.println("Link: " + p.getProductLink() + "\n");

		}
	}

}
