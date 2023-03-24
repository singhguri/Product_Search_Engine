package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import main.Trie.TrieNode;

public class WordCompletion {

	private static String currentDir = System.getProperty("user.dir");
	private static String textLocation = currentDir + "\\text\\";

	private static Trie trie = new Trie();

	public static String completeWord(String word) throws IOException {
		if (word == "")
			return "";

		File Directory = new File(textLocation);
		String dataFromFile = readData(Directory);
		createTrie(dataFromFile);

		String finalResult = "";
		TrieNode currNode = trie.root;
		;

		for (int i = 0; i < word.length(); i++) {
			int index = word.charAt(i) - 'a';
			if (currNode.children[index] != null) {
				finalResult += word.charAt(i);
				currNode = currNode.children[index];
			} else {
				return word;
			}
		}

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
		StringTokenizer token = new StringTokenizer(data, " ");
		while (token.hasMoreTokens()) {
			String word = token.nextToken().toLowerCase();
			// Checking if the word is alphabets

			if (word != null && word.matches("^[a-zA-Z]*$")) {
				Trie.insert(word);
			}
		}
	}

	static String readData(File Directory) throws IOException {
		StringBuilder content = new StringBuilder();
		String data;
		for (File fileEntry : Directory.listFiles()) {
			String currentfileName = fileEntry.getName();
			BufferedReader bufferedReader = new BufferedReader(new FileReader(textLocation + currentfileName));

			while ((data = bufferedReader.readLine()) != null) {
				content.append(data);
				content.append(" ");
			}
		}
		return content.toString();
	}

	public static void main(String[] args) throws IOException {

		StdOut.println(completeWord("he"));

	}

}