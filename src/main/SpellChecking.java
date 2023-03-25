package main;

import java.io.*;
import java.util.*;

public class SpellChecking {

	private static ArrayList<String> dictionary = new ArrayList<>();
	private static HashMap<String, Integer> hashMap;
	public static String[] suggestions;
	private static Map<String, Integer> hashMap2;
	private static List<Map.Entry<String, Integer>> linkedList;
	private static HashMap<String, Integer> tempLinkedList;

	private static int numberOfSuggestions;

	// Constructor
	SpellChecking() {
		numberOfSuggestions = 10;
		hashMap = new HashMap<>();
		hashMap = new HashMap<>();
		dictionary = new ArrayList<>();
		suggestions = new String[numberOfSuggestions];
	}

	// How many incorrect word suggestions are required?
	public void setnumberOfSuggestions(int N) {
		numberOfSuggestions = N;
		suggestions = new String[numberOfSuggestions];
	}

	// checking if the string is correct
	public boolean isCorrect(String query) throws IOException {
		// Returns true if the query string is found in the dictionary, else returns
		// false.
		if (dictionary.contains(query))
			return true;
		return false;
	}

	public static int Minimum(int a, int b) {
		if (a < b)
			return a;
		return b;
	}

	// using edit distance concept between two words

	// using edit distance concept between two words
	public static int getEditDistance(String word1, String word2) {
		int length1 = word1.length();
		int length2 = word2.length();
		int[][] dp = new int[length1 + 1][length2 + 1]; // Edit distance table

		for (int i = 0; i <= length1; i++) {
			dp[i][0] = i;
		}
		for (int j = 0; j <= length2; j++) {
			dp[0][j] = j;
		}

		for (int i = 0; i < length1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < length2; j++) {
				char c2 = word2.charAt(j);

				if (c1 == c2) {
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;

					int min = Minimum(replace, insert);
					min = Minimum(delete, min);
					dp[i + 1][j + 1] = min;
				}
			}
		}
		return dp[length1][length2];
	}

	// This will be used to debug the system.
	public static void printDictionary() {
		int len = dictionary.size();
		StdOut.println("The dictionary contains: ");
		for (int i = 0; i < len; i++) {
			StdOut.println(dictionary.get(i));
		}
		return;
	}

	// get all the words from the dictionary
	public void getVocabolary() throws IOException {
		// initializing the dictionary into file object
		File dictionaryFile = new File(Constants.dictionaryFile);

		StringBuilder stringBuilder = new StringBuilder();
		StringTokenizer stringToken;

		BufferedReader bufferedReader = new BufferedReader(new FileReader(dictionaryFile));
		String string;

		// adding each string in the String Builder in
		while ((string = bufferedReader.readLine()) != null) {
			stringBuilder.append(string);
			stringBuilder.append(" ");
		}
		bufferedReader.close();
		String allwords = stringBuilder.toString();

		stringToken = new StringTokenizer(allwords, "0123456789 ,[\\~\\(\\)\\{\\}\\_\\@\\>\\<\\=\\+\\[\\]\\\\\\/\\?\\;\\&\\#\\-\\.\\!\\:\\\"\\'\\n\\t\\r]*$\n");

		while (stringToken.hasMoreTokens()) {
			String token = stringToken.nextToken().toLowerCase(Locale.ROOT);
			if (!dictionary.contains(token)) {
				dictionary.add(token);
			}
		}
	}

	// receives suggestions for a false word
	public String[] getSimilarWords(String input) {

		hashMap = new HashMap<>(); // new hash map to store words from the dictionary.

		// putting the similar words and there edit distance value in the hash map.
		for (int i = 0; i < dictionary.size(); i++) {
			String dictionaryString = dictionary.get(i);
			int editDistance = getEditDistance(input, dictionaryString); // calculating edit distance between the input
																			// and dictionary words.
			hashMap.put(dictionaryString, editDistance);
		}

		linkedList = new LinkedList<>(hashMap.entrySet());

		linkedList.sort(Map.Entry.comparingByValue());

		// Data from a sorted list will then be added to a hash map.
		tempLinkedList = new LinkedHashMap<>();
		for (Map.Entry<String, Integer> aa : linkedList) {
			tempLinkedList.put(aa.getKey(), aa.getValue());
		}
		hashMap2 = tempLinkedList;

		int wordLimit = 0;
		for (Map.Entry<String, Integer> entry : hashMap2.entrySet()) {
			if (entry.getValue() != 0) {
				suggestions[wordLimit] = entry.getKey();
				wordLimit++;
				if (wordLimit == 10) {
					break;
				}
			}
		}
		return suggestions;
	}

	public static void main(String[] args) throws IOException {

		// This will be used to debug the system.
		SpellChecking sp = new SpellChecking();
		int N = 10;
		sp.getVocabolary();
		sp.setnumberOfSuggestions(N);
		String s[] = sp.getSimilarWords("computer");
		StdOut.println(s.length);
		// String s[] = sp.getAltWords("digi");
		for (int i = 0; i < 10; i++) {
			StdOut.println(s[i]);
		}
	}
}
