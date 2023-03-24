package main;

import java.util.*;
import java.io.*;

public class InvertedIndex {

	// array list for holding files from the path directory
	static ArrayList<String> documents = new ArrayList<String>();

	// method for accessing the inverted indexing
	public static void readFile() {

		// Hashmap for holding the indexes of the the occurrences of word in a
		// particular doc
		// Word String as key and list of indexes as value
		HashMap<String, List<Integer>> occurencesOfWord = new HashMap<String, List<Integer>>();

		// final hash map handling the document with each word and its occurrences
		HashMap<String, HashMap<String, List<Integer>>> resultMap = new HashMap<String, HashMap<String, List<Integer>>>();

		// scanner for scanning file from the directory
		Scanner scanFile;
		//
		String readWord;
		//
		File pathOfFolder = new File("text/");
		// for loop to add files in the documents
		for (File fileEntry : pathOfFolder.listFiles())
			documents.add(fileEntry.getName());

		// variable for looping the documents
		int x = 0;
		// list holding the indexes
		List<Integer> occurencesList = null;

		while (x < documents.size()) { // loop for accessing the documents

			try {
				// scanning the files based on the index
				scanFile = new Scanner(new FileReader("text/" + documents.get(x)));
			} catch (FileNotFoundException e) { // exception if the file is not found
				System.err.println(e);
				return;
			}
			int index = 0; // variable for holding the index of the words in the document
			while (scanFile.hasNext()) {
				readWord = scanFile.next(); // string holding the scanned word from the file
				scanFile.useDelimiter("[^a-zA-Z]+"); //
				readWord = readWord.toLowerCase(); // ignoring cases to handle the count/occurrences

				// if the word is already present in the map get the data and add the new index
				if (occurencesOfWord.containsKey(readWord)) {
					occurencesList = occurencesOfWord.get(readWord);
					occurencesList.add(index);
				}

				// otherwise creating a new key with word and also store the index
				else {
					occurencesList = new ArrayList<Integer>();
					occurencesList.add(index);
				}
				// increasing value for holding the index
				index = index + readWord.length();
				// adding the word and new list of indexes
				occurencesOfWord.put(readWord, occurencesList);
				// emptying the occurrences list for restarting fresh
				occurencesList = new ArrayList<Integer>();
			} // end of while for a document

			// adding the data for final result map with document name as key and words with
			// its list of indexes
			resultMap.put(documents.get(x), occurencesOfWord);
			// StdOut.println(documents.get(x));
			// StdOut.println(resultMap);

			// emptying the hash map for restarting another document
			occurencesOfWord = new HashMap<String, List<Integer>>();
			// incrementing the document index for fetching the next file in the documents
			x++;
		}
		StdOut.println(resultMap);
	}

	// main method for this class
	public static void main(String[] args) {

		// calling the fileaccessories method for indexing the files
		readFile();
	}

}