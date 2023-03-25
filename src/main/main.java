package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class main {
	public static List<ProductInfo> monthly = new ArrayList<>();

	public static List<ProductInfo> unlocked = new ArrayList<>();

	public static void sort() {
		Collections.sort(monthly, new Comparator<ProductInfo>() {
			@Override
			public int compare(ProductInfo p1, ProductInfo p2) {
				return (Double.compare(p1.getProductPrice(), p2.getProductPrice()));
			}
		});

		Collections.sort(unlocked, new Comparator<ProductInfo>() {
			@Override
			public int compare(ProductInfo p1, ProductInfo p2) {
				return (Double.compare(p1.getProductPrice(), p2.getProductPrice()));
			}
		});
	}

	public static void filter(List<ProductInfo> main) {
		for (ProductInfo p : main) {
			String normalize_type = p.getProductPaymentType().toLowerCase();
			if (normalize_type.contains("monthly"))
				monthly.add(p);
			else
				unlocked.add(p);
		}
		sort();
	}

	public static void searchByBrand(String brand, List<ProductInfo> p, String filter) {
		List<ProductInfo> results = new ArrayList<>();
		int index = 0;

		while (index < p.size()) {
			String normalize_name = p.get(index).getProductName().toLowerCase();
			if (normalize_name.contains(brand))
				if (!results.contains(p.get(index)))
					results.add(p.get(index));
			index++;
		}
		if (results.size() > 0) {
			StdOut.println("\nShowing top " + results.size() + " results"
					+ ((filter != null && filter.length() > 0) ? (" by " + filter + " filter:") : ":"));
			for (ProductInfo pr : results) {
				StdOut.println("Item: " + pr.getProductName());
				StdOut.println("Price: " + pr.getProductPrice());
				StdOut.println("Financing type: " + pr.getProductPaymentType());
				StdOut.println("Link: " + pr.getProductLink() + "\n");
			}
		}
	}

	public static void searchByMemory(String memory, List<ProductInfo> p, String filter) {
		List<ProductInfo> results = new ArrayList<>();
		memory += "gb";
		int index = 0;
		while (index < p.size()) {
			String normalize_name = p.get(index).getProductName().toLowerCase();
			if (normalize_name.contains(memory))
				if (!results.contains(p.get(index)))
					results.add(p.get(index));
			index++;
		}

		if (results.size() > 0) {
			StdOut.println("\nShowing top " + results.size() + " results"
					+ ((filter != null && filter.length() > 0) ? (" by " + filter + " filter:") : ":"));
			for (ProductInfo pr : results) {
				StdOut.println("Item: " + pr.getProductName());
				StdOut.println("Price: " + pr.getProductPrice());
				StdOut.println("Financing type: " + pr.getProductPaymentType());
				StdOut.println("Link: " + pr.getProductLink() + "\n");
			}
		}

	}

	public static void searchByBrandAndMemory(String brand, String memory, List<ProductInfo> p, String filter) {
		List<ProductInfo> results = new ArrayList<>();
		memory += "gb";
		int index = 0;
		while (index < p.size()) {
			String normalize_name = p.get(index).getProductName().toLowerCase();
			if (normalize_name.contains(brand) && normalize_name.contains(memory))
				if (!results.contains(p.get(index)))
					results.add(p.get(index));
			index++;
		}
		StdOut.println("\nShowing top " + results.size() + " results:");
		for (ProductInfo pr : results) {
			if (index == 0)
				break;
			StdOut.println("Item: " + pr.getProductName());
			StdOut.println("Price: " + pr.getProductPrice());
			StdOut.println("Financing type: " + pr.getProductPaymentType());
			StdOut.println("Link: " + pr.getProductLink() + "\n");
		}

	}

	public static void phoneSearch() throws IOException {
		String url = "https://www.bestbuy.ca/en-ca";
		String productInfo = "";
		int searchPage;
		String brand = "";
		String memory = "";
		int user_option = 0;
		int finance_type = 0;

		SpellChecking sp = new SpellChecking();
		int N = 10;
		sp.getVocab();
		sp.setnumberOfSuggestions(N);

		List<ProductInfo> products = new ArrayList<>();
		WebCrawler obj = new WebCrawler();

		Scanner sc = new Scanner(System.in);

		StdOut.println("*****************************************************");
		StdOut.println("************ BEST BUY PRODUCTS FETCHER **************");
		StdOut.println("*****************************************************");

		// StdOut.print("\nSearch productProductInfo: ");
		// productProductInfo = sc.next();
		productInfo = "mobile";
		StdOut.println("\nCRAWLING FOR RELEVANT LINK.....\n");
		obj.crawl(url, productInfo);

		StdOut.println("\nLINK FOUND!!\n");

		StdOut.println("Mobile Store Link: " + obj.resultLink);

		// StdOut.println("Number of pages to search: ");
		// searchPage = sc.nextInt();
		searchPage = 1;
		StdOut.println("\nFETCHING PRODUCTS.....\n");

		products = WebScrapper.getProductItemsInfo(obj.resultLink, products, searchPage);
		filter(products);

		while (user_option != 4) {
			StdOut.println("Options: ");
			StdOut.println("1. Search by brand");
			StdOut.println("2. Search by memory");
			StdOut.println("3. Search by brand and memory");
			StdOut.println("4. Quit");

			StdOut.println("Enter option: ");
			user_option = sc.nextInt();
			switch (user_option) {
				case 1: {
					StdOut.println("Enter a brand name: ");
					brand = sc.next().toLowerCase();
					while (!sp.isCorrect(brand)) {
						SearchFrequency.FrequentCount(brand);
						sp.getAltWords(brand);
						String[] s = sp.suggestions;
						StdOut.println("Do you mean: ");
						for (int index = 0; index < 10; index++) {
							StdOut.println(s[index]);
						}
						StdOut.println("Enter a valid brand name: ");
						brand = sc.next().toLowerCase();
					}
					SearchFrequency.FrequentCount(brand);
					searchByBrand(brand, monthly, "monthly payment type");
					searchByBrand(brand, unlocked, "");
					break;
				}
				case 2: {
					StdOut.println("Enter memory size (in GB): ");
					memory = sc.next();
					while (!DataValidator.memoryValidation(memory)) {
						StdOut.println("Invalid input!");
						StdOut.println("Enter a valid memory size (in GB): ");
						memory = sc.next();
					}
					searchByMemory(memory, monthly, "monthly payment type");
					searchByMemory(memory, unlocked, "");

					break;
				}
				case 3: {
					StdOut.println("Enter a brand name: ");
					brand = sc.next().toLowerCase();
					while (!sp.isCorrect(brand)) {
						SearchFrequency.FrequentCount(brand);
						sp.getAltWords(brand);
						String[] s = sp.suggestions;
						StdOut.println("Do you mean: ");
						for (int index = 0; index < 10; index++) {
							StdOut.println(s[index]);
						}
						StdOut.println("Enter valid a brand name: ");
						brand = sc.next().toLowerCase();
					}
					SearchFrequency.FrequentCount(brand);
					StdOut.println("Enter memory size (in GB): ");
					memory = sc.next();
					while (!DataValidator.memoryValidation(memory)) {
						StdOut.println("Invalid input!");
						StdOut.println("Enter a valid memory size (in GB): ");
						memory = sc.next();
					}
					searchByBrandAndMemory(brand, memory, monthly, "monthly payment type");
					searchByBrandAndMemory(brand, memory, unlocked, "");
					break;
				}
				case 4: {
					StdOut.println("EXITING.....");
					break;
				}
				default: {
					StdOut.println("Invalid option! Try again!");
					break;
				}
			}

		}
	}

	/*
	 * Showcase other features
	 * 
	 *
	 */
	public static void pageRankingDemo() throws IOException {
		String url = "";
		int maxSearch = 1;
		Scanner in = new Scanner(System.in);
		WebCrawler obj = new WebCrawler();
		String query = "";
		Map<String, Integer> pageRankMap;
		StdOut.println("*****************************************************");
		StdOut.println("************ PAGE RANKING DEMONSTRATION *************");
		StdOut.println("*****************************************************");

		StdOut.println("Enter starting url: ");
		url = in.next();
		StdOut.println("Enter of maximume pages to crawl: ");
		maxSearch = in.nextInt();
		StdOut.println("\n******************** CRAWLING *********************");
		obj.crawl(url, maxSearch);
		obj.saveURLasText();

		// Word frequency
		StdOut.println("\n************** ALL WORDS' FREQUENCY ***************");
		FrequencyCount.frqcount();
		StdOut.println("\n");
		InvertedIndex.readFile();
		// Page ranking
		StdOut.println("\n***************** PAGE RANKING ********************");
		StdOut.println("\nEnter string to search: ");
		query = in.next();

		StdOut.println("Did you mean: " + WordCompletion.completeWord(query) + "?");
		query = WordCompletion.completeWord(query);
		StdOut.println("Top 10 pages' ranking of the word " + query + "\n");
		PageRanking.calculatePageRank(query);

		/*
		 * for(Map.Entry<String, Integer> entry: pageRankMap.entrySet())
		 * {
		 * StdOut.println(entry.getKey()+": "+entry.getValue());
		 * }
		 */

	}

	public static void main(String[] args) throws IOException {
		// pageRankingDemo();
		phoneSearch();
	}
}
