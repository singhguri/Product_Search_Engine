package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler {
	// Create a static field to store all crawled links
	private static HashSet<String> urlLinks;

	// Create a static field to store the link that contains the keyword
	public static String resultLink;

	// Create a static field to store the number of pages crawled
	public static int numPage = 0;

	// Create a constructor to initialize the set of crawled links
	public WebCrawler() {
		urlLinks = new HashSet<>();
	}

	public static void crawl(String URL, String keyword) throws IOException {

		// Adds the current URL to the visited URLs
		urlLinks.add(URL);
		// Connects to the current URL and retrieve the HTML document
		// using the JSoup library
		Document document = Jsoup.connect(URL).get();

		// Defines the regular expresion for the URLs that the web crawler would follow
		String patternCrawl = "^(https?)://(www\\.)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*(\\.)(ca|com)[-a-zA-Z0-9+&@#/%=~_|]*";
		Pattern pattern = Pattern.compile(patternCrawl);

		// Retrieves the tags that contain the href links
		Elements pageLinks = document.select("a[href]");

		String url;
		long duplicateURL = 0, notMatch = 0;

		// Iterates through each anchor tag and extracts the URL from the href attribute
		// Checks whether the URL has been visited or it matches the pattern.
		// If visited the duplicate URL counter is incremented
		// Else if the pattern does not match the noMtach counter is incremented
		// Else if the pattern has not been visited but matches the pattern it adds the
		// URL to the visited list of the URLs
		for (Element currentPage : pageLinks) {
			url = currentPage.attr("abs:href");

			Matcher matcher = pattern.matcher(url);

			if (urlLinks.contains(url)) {
				duplicateURL++;
			}

			else if (!matcher.find()) {
				// System.out.println("break"+matcher.find());
				notMatch++;
			}

			else {
				urlLinks.add(currentPage.attr("abs:href"));
				// System.out.println(url+ " URL will be crawled");
			}

			// Checks whether the current URL contains the keyword being searched for
			if (url.contains(keyword)) {
				// System.out.println(url);
				resultLink = url;
				break;
			}
		}
	}

	public static void crawl(String URL, int maxSearch) {
		// Regex pattern for https links
		String patternCrawl = "^(https?)://(www\\.)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*(\\.)(ca|com)[-a-zA-Z0-9+&@#/%=~_|]*";
		Pattern pattern = Pattern.compile(patternCrawl);
		Matcher matcher = pattern.matcher(URL);

		// Checks if the starting URL has not been crawled
		if (((!urlLinks.contains(URL) && (numPage < maxSearch))) && (matcher.find())) {
			System.out.println("Page " + (numPage + 1) + ": " + URL);
			try {
				urlLinks.add(URL); // Adds the starting URl to the hashset
				Document document = Jsoup.connect(URL).get(); // Retrieves the HTML document of the starting URL

				// All href links in the root url
				Elements pageLinks = document.select("a[href]");
				numPage++;
				for (Element currentPage : pageLinks) {
					crawl(currentPage.attr("abs:href"), maxSearch);
				}

			}
			// Cathes any IOExecption that may occur while trying to retrieve the HTML
			// documents
			catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void saveURLasText() throws IOException {
		String currentDir = System.getProperty("user.dir"); // Gets the current working directory and stores it

		// COntains the path to the directory where the text files would be stored
		String textLocation = currentDir + "\\text\\";
		File textFiles = new File(textLocation);

		// Creates a directory for all text files if the directory doesnt exist
		if (textFiles.mkdir() == true) {
			System.out.println("Text files directory has been created successfully");
		} else {
			System.out.println("Text files directory cannot be created");
		}
		String URL, text, fileLocation, fileNamePattern;

		// Regular expression for a valid file name
		fileNamePattern = "[^a-zA-Z0-9_-]";

		Iterator<String> iterator = urlLinks.iterator();
		// Goes through each element in the urlLinks sets and assigns it to the URL
		// variable
		while (iterator.hasNext()) {
			URL = iterator.next();

			Document document = Jsoup.connect(URL).get(); // Connects to the specified URL using JSoup

			text = document.body().text(); // Extracts the body text of the HTML document and stores it
			// Adds the .txt extension to the HTMl document and removes any characters not
			// valid in a file name
			String textFileName = document.title().replaceAll(fileNamePattern, "") + ".txt";
			// Creates a new file by concatenating the location of the text and the nale of
			// the file
			File html = new File(textLocation + textFileName);
			// Write the URL and the text from the webpage to a text file in the specified
			// directory
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(html, true));
			bufferedWriter.write(URL + " " + text);
			bufferedWriter.close();
		}
	}

	public static void main(String[] args) throws IOException {
		WebCrawler obj = new WebCrawler();
		// Defines the URL to crawl and the maximum pages to crawl
		obj.crawl("https://www.bestbuy.ca/en-ca", 50);
		obj.saveURLasText(); // Saves the crawled URLs as text
	}
}
