package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Test {
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
		int i = 0;
		while (i < p.size()) {
			String normalize_name = p.get(i).getProductName().toLowerCase();
			if (normalize_name.contains(brand))
				results.add(p.get(i));
			i++;
		}
		if (results.size() > 0) {
			StdOut.println("\nShowing top " + results.size() + " results"
					+ ((filter != null && filter.length() > 0) ? (" by " + filter + " filter:") : ":"));
			for (ProductInfo pr : results) {
				System.out.println("Item: " + pr.getProductName());
				System.out.println("getProductPrice(): " + pr.getProductPrice());
				System.out.println("Financing type: " + pr.getProductPaymentType());
				System.out.println("Link: " + pr.getProductLink() + "\n");
			}
		}
	}

	public static void searchByMemory(String memory, List<ProductInfo> p, String filter) {
		List<ProductInfo> results = new ArrayList<>();
		memory += "gb";
		int i = 0;
		while (i < p.size()) {
			String normalize_name = p.get(i).getProductName().toLowerCase();
			if (normalize_name.contains(memory))
				results.add(p.get(i));
			i++;
		}

		if (results.size() > 0) {
			StdOut.println("\nShowing top " + results.size() + " results"
					+ ((filter != null && filter.length() > 0) ? (" by " + filter + " filter:") : ":"));
			for (ProductInfo pr : results) {
				System.out.println("Item: " + pr.getProductName());
				System.out.println("getProductPrice(): " + pr.getProductPrice());
				System.out.println("Financing type: " + pr.getProductPaymentType());
				System.out.println("Link: " + pr.getProductLink() + "\n");
			}
		}

	}

	public static void searchByBrandAndMemory(String brand, String memory, List<ProductInfo> p, String filter) {
		List<ProductInfo> results = new ArrayList<>();
		memory += "gb";
		int i = 0;
		while (i < p.size()) {
			String normalize_name = p.get(i).getProductName().toLowerCase();
			if (normalize_name.contains(brand) && normalize_name.contains(memory))
				results.add(p.get(i));
			i++;
		}
		i = 3;
		System.out.println("\nShowing top 3 results:");
		for (ProductInfo pr : results) {
			if (i == 0)
				break;
			System.out.println("Item: " + pr.getProductName());
			System.out.println("getProductPrice(): " + pr.getProductPrice());
			System.out.println("Financing type: " + pr.getProductPaymentType());
			System.out.println("Link: " + pr.getProductLink() + "\n");
			i--;
		}

	}

	public static void phoneSearch() throws IOException {
		String url = "https://www.bestbuy.ca/en-ca";
		String productProductInfo = "";
		int searchPage;
		String brand = "";
		String memory = "";
		int user_option = 0;
		int finance_type = 0;

		SpellChecking sp = new SpellChecking();
		int N = 10;
		sp.getVocab();
		sp.setnumberOfSuggestions(N);

		List<ProductInfo> productProductInfos = new ArrayList<>();
		WebCrawler obj = new WebCrawler();

		Scanner sc = new Scanner(System.in);

		System.out.println("*****************************************************");
		System.out.println("************ BEST BUY PRODUCTProductInfoS FETCHER **************");
		System.out.println("*****************************************************");

		// System.out.print("\nSearch productProductInfo: ");
		// productProductInfo = sc.next();
		productProductInfo = "mobile";
		System.out.println("\nCRAWLING FOR RELEVANT LINK.....\n");
		obj.crawl(url, productProductInfo);

		StdOut.println("\nLINK FOUND!!\n");

		StdOut.println("Mobile Store Link: " + obj.resultLink);

		// StdOut.println("Number of pages to search: ");
		// searchPage = sc.nextInt();
		searchPage = 10;
		System.out.println("\nFETCHING PRODUCTProductInfoS.....\n");
		productProductInfos = WebScrapper.getItems(obj.resultLink, productProductInfos, searchPage);
		filter(productProductInfos);

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
						for (int i = 0; i < 10; i++) {
							StdOut.println(s[i]);
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
					while (!DataValidation.memoryValidation(memory)) {
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
						for (int i = 0; i < 10; i++) {
							StdOut.println(s[i]);
						}
						StdOut.println("Enter valid a brand name: ");
						brand = sc.next().toLowerCase();
					}
					SearchFrequency.FrequentCount(brand);
					StdOut.println("Enter memory size (in GB): ");
					memory = sc.next();
					while (!DataValidation.memoryValidation(memory)) {
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
