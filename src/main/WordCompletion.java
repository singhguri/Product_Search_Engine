package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import main.Trie.TrieNode;

public class WordCompletion {
	// Initializes the current directory and returns the path of the current working
	// directory as a string
	private static String currentDir = System.getProperty("user.dir");
	// Creates the full path to the directory where text files will be stored
	private static String textLocation = currentDir + "\\text\\";
	private static Trie trie = new Trie(); // Creates an empty trie data structure that will be used to store words from
											// text files

	public static void initializeTrie() throws IOException {
		File directory = new File(textLocation); // Creates a handle to the directory where the text files are stored
		String dataFromFile = readData(directory); // Reads the contents of all text files in the specified directory
		createTrie(dataFromFile); // Creates a trie data structure and populating it with the words contained in
									// the text files
	}

	public static String completeWord(String word) {
		// Checks if the word is empty and if empty returns an empty string
		if (word == "") {
			return "";
		}

		String finalResult = "";
		TrieNode currNode = trie.root; // Returns the root node of the trie

		// Processes the characters of the input word string one by one
		for (int i = 0; i < word.length(); i++) {
			int index = word.charAt(i) - 'a';
			if (currNode.children[index] != null) {
				finalResult += word.charAt(i);
				currNode = currNode.children[index];
			} else {
				return word;
			}
		}

		// Processes the remaining characters in the trie after the end of the input
		// word
		while (!currNode.isEndOfWord) {
			for (int i = 0; i < 26; i++) {
				if (currNode.children[i] != null) {
					finalResult += (char) ('a' + i);
					currNode = currNode.children[i];
					break;
				}
			}
		}
		return finalResult;
	}

	public static void createTrie(String data) {
		// Tokenizes the data string using space as the delimiter and converts each
		// token into lowercase
		StringTokenizer token = new StringTokenizer(data, " ");
		while (token.hasMoreTokens()) {
			String word = token.nextToken().toLowerCase();
			// Checking if the word is alphabets
			if (word != null && word.matches("^[a-zA-Z]*$")) {
				trie.insert(word);
			}
		}
	}

	// Loops through each file in the directory using a for-each loop.
	// For each file, it reads the content of the file using a BufferedReader and
	// appends it to the
	// content string with a space separator
	static String readData(File directory) throws IOException {
		StringBuilder content = new StringBuilder();
		String data;
		for (File fileEntry : directory.listFiles()) {
			String currentfileName = fileEntry.getName();
			try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textLocation + currentfileName))) {
				while ((data = bufferedReader.readLine()) != null) {
					content.append(data);
					content.append(" ");
				}
			}
		}
		return content.toString();
	}

	public static void main(String[] args) throws IOException {
		initializeTrie();
		System.out.println(completeWord("the"));
	}
}
