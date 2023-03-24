package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * DONE BY HOA TO
 * 
 * Web Scrapper class
 * 
 * use function getItems() by:
 * - input a store's start URL
 * - input the number of pages to search
 * 
 */

public class WebScrapper {
	// Best buy html element that stores the product list
	private static final String PRODUCT_LIST_ITEM = "col-xs-12_198le col-sm-4_13E9O col-lg-3_ECF8k x-productListItem productLine_2N9kG";

	// Best buy html element that contains product name
	private static final String PRODUCT_NAMES = "div.productItemName_3IZ3c";

	// Best buy html element that stores pricing-related information
	private static final String PRODUCT_PRICES = "div.productPricingContainer_3gTS3";

	// Best buy html element that contains the product's price
	private static final String PRODUCT_INDIVIDUAL_PRICE = "span[data-automation=product-price] > div";

	// Function to check if product pricing is monthly or
	public static void checkProductInfo(ProductInfo p) {
		if (p.getProductName().contains("Monthly"))
			p.setProductPaymentType("Monthly - 24 months");
		else
			p.setProductPaymentType("Unlocked - One time pay");
	}

	// Function to check if product already existed in list
	public static boolean hasItem(List<ProductInfo> products, ProductInfo p) {
		for (ProductInfo pro : products) {
			if (pro.equals(p))
				return true;
		}
		return false;
	}

	// Scrap items page by page
	public static List<ProductInfo> getItems(String start_url, List<ProductInfo> products, int numSearch) {
		// Append the url with page 0
		start_url = start_url + "/?pages=0";

		// Scrapping the product url page by page
		for (int i = 1; i <= numSearch; i++) {
			// Replace page number
			start_url = start_url.substring(0, start_url.length() - 1);
			start_url = start_url.substring(0, start_url.length() - 1);
			start_url = start_url + Integer.toString(i);

			// Scrapping the name, price and link to buy of products
			try {
				Document bestBuy = Jsoup.connect(start_url).get();

				Elements items = bestBuy.getElementsByClass(PRODUCT_LIST_ITEM);

				for (Element item : items) {
					ProductInfo p = new ProductInfo();
					Elements name = item.select(PRODUCT_NAMES);
					if (!name.isEmpty()) {
						// System.out.println("Item: "+name.text());
						p.setProductName(name.text());
					}

					Elements priceContainer = item.select(PRODUCT_PRICES);
					Element price = priceContainer.select(PRODUCT_INDIVIDUAL_PRICE).first();
					if (!priceContainer.isEmpty()) {
						if (!priceContainer.isEmpty()) {
							double priceValue = Double.parseDouble(price.text().replace(",", "").substring(1));
							// System.out.println("Price: "+price.text());
							p.setProductFormattedPrice(price.text());
							p.setProductPrice(priceValue);
						}

						Elements link = item.select("a[href]");
						if (!link.isEmpty()) {
							p.setProductLink(link.first().attr("href"));
							// System.out.println("Link: "+link.attr("href")+"\n");
						}
						checkProductInfo(p);
						if (hasItem(products, p))
							continue;
						products.add(p);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return (products);
	}

	public static void main(String[] args) {
		List<ProductInfo> products = new ArrayList<>();

		String url = "https://www.bestbuy.ca/en-ca/category/best-buy-mobile/20006";
		products = getItems(url, products, 10);

		for (ProductInfo p : products) {
			System.out.println("Item: " + p.getProductName());
			System.out.println("Price: " + p.getProductFormattedPrice());
			System.out.println("Financing type: " + p.getProductPaymentType());
			System.out.println("Link: " + p.getProductLink() + "\n");
		}
	}

}
