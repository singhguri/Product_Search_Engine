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
/*
 *DONE BY HOA TO
 *
 * WebCrawler class 
 * 
 * Use function crawl() by:
 * - inputing a start URL
 * - input a keyword that the URL contains
 * 
 * crawl() traverse through each URL in the start URL and stop
 * when the first link with keyword is found
 */

public class WebCrawler {
	// Set that stores all crawled links
	private static HashSet<String> urlLinks;

	// Link that contain keyword
	public static String resultLink;

	// Number of pages crawled
	public static int numPage = 0;

	// Initialize set using constructor
	public WebCrawler() {
		urlLinks = new HashSet<>();
	}

	// Crawl function
	public static void crawl(String URL, String keyword) throws IOException {
		// add to crawled links
		urlLinks.add(URL);
		Document document = Jsoup.connect(URL).get();

		// Regex pattern for https links
		String patternCrawl = "^(https?)://(www\\.)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*(\\.)(ca|com)[-a-zA-Z0-9+&@#/%=~_|]*";
		Pattern pattern = Pattern.compile(patternCrawl);

		// All href links in the root url
		Elements pageLinks = document.select("a[href]");

		String url;
		// number of duplicate urls
		long duplicateURL = 0;

		// number of invalid urls - urls that do not match regex string
		long notMatch = 0;

		// Continue to crawl all the links in root url until an url that contains the
		// keyword is found
		for (Element currentPage : pageLinks) {
			url = currentPage.attr("abs:href");

			// check if current link is valid using regex
			Matcher matcher = pattern.matcher(url);

			// if link already crawled do not add to crawled links
			if (urlLinks.contains(url)) {
				// StdOut.println(url+ " | URL previously visited");

				duplicateURL++;
			}
			// if link is invalid do not add to crawled links
			else if (!matcher.find()) {
				// StdOut.println("break"+matcher.find());
				// StdOut.println(url+ " | URL does not matched the pattern requirement");
				notMatch++;
			}

			// else add to crawled links
			else {
				urlLinks.add(currentPage.attr("abs:href"));
				// StdOut.println(url+ " | URL will be crawled");
			}
			// break when keyword is found
			if (url.contains(keyword)) {
				// StdOut.println(url);
				resultLink = url;
				break;
			}
		}

		// StdOut.println("Number of duplicate URLs : "+duplicateURL);
		// StdOut.println("Number of invalid URLs : "+notMatch);
		// StdOut.println("Number of unique URLs : "+urlLinks.size());
	}

	public static void crawl(String URL, int maxSearch) {
		// Regex pattern for https links
		String patternCrawl = "^(https?)://(www\\.)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*(\\.)(ca|com)[-a-zA-Z0-9+&@#/%=~_|]*";
		Pattern pattern = Pattern.compile(patternCrawl);
		Matcher matcher = pattern.matcher(URL);

		// StdOut.print(matcher.find());
		if (((!urlLinks.contains(URL) && (numPage < maxSearch))) && (matcher.find())) {
			StdOut.println("Page " + (numPage + 1) + ": " + URL);
			try {
				urlLinks.add(URL);
				Document document = Jsoup.connect(URL).get();

				// All href links in the root url
				Elements pageLinks = document.select("a[href]");
				numPage++;
				for (Element currentPage : pageLinks) {
					crawl(currentPage.attr("abs:href"), maxSearch);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void saveURLasText() throws IOException {
		// Initialization
		// Current directory
		String currentDir = System.getProperty("user.dir");

		// The location for the directory that stored all text versions of html files
		String textLocation = currentDir + "\\text\\";
		File textFiles = new File(textLocation);

		// Create directory for all text files
		if (textFiles.mkdir() == true) {
			StdOut.println("Text files directory has been created successfully");
		} else {
			StdOut.println("Text files directory cannot be created");
		}
		String URL, text, fileLocation, fileNamePattern;

		// Regex for valid file name
		fileNamePattern = "[^a-zA-Z0-9_-]";

		Iterator<String> iterator = urlLinks.iterator();
		while (iterator.hasNext()) {
			URL = iterator.next();

			Document document = Jsoup.connect(URL).get();

			text = document.body().text();
			// StdOut.println(document.title());
			String textFileName = document.title().replaceAll(fileNamePattern, "") + ".txt";
			File html = new File(textLocation + textFileName);
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(html, true));
			bufferedWriter.write(URL + " " + text);
			bufferedWriter.close();
		}
	}

	public static void main(String[] args) throws IOException {
		WebCrawler obj = new WebCrawler();
		obj.crawl("https://www.bestbuy.ca/en-ca", 50);
		obj.saveURLasText();
		// obj.crawl("https://www.bestbuy.ca/en-ca","mobile");
	}
}
