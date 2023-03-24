package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class FrequencyCount {

	// method for accessing the frequency count
	public static void frqcount() {
		// Current directory
		String currentDir = System.getProperty("user.dir");

		// The location for the directory that stored all text versions of html files
		String textLocation = currentDir + "\\text\\";
		File textFiles = new File(textLocation);
		File[] texts = textFiles.listFiles();

		StringBuilder stringReturn = new StringBuilder();
		// File Reading
		for (File t : texts) {
			try {
				BufferedReader in = new BufferedReader(new FileReader(t));
				String temperoryString;
				while ((temperoryString = in.readLine()) != null) {
					stringReturn.append(temperoryString); // appending all the content to the string
				}
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*
		 * try {
		 * // reading the content in file using BufferedReader
		 * BufferedReader in = new BufferedReader(new
		 * FileReader("W3C Web Pages/text/About W3C Standards.txt"));
		 * 
		 * String temperoryString;
		 * while ((temperoryString = in.readLine()) != null) {
		 * stringReturn.append(temperoryString); // appending all the content to the
		 * string
		 * }
		 * in.close(); // closing the scanner (memory leak if not)
		 * } catch (IOException e) { // exception if the file is not loaded correctly
		 * StdOut.println("Unable to read file");
		 * }
		 */

		// Converting file content to string
		String fileContent = stringReturn.toString();

		// breaking the string into tokens based upon space
		StringTokenizer stringTokenizer = new StringTokenizer(fileContent, " ");

		// hash map for holding the frequency of the word
		// String word as key and Integer frequency as value
		HashMap<String, Integer> tableForFrequency = new HashMap<String, Integer>();

		// variable for holding the iterated token from stringTokenizer
		String stringTokenizersword;

		// iterating the string words
		while (stringTokenizer.hasMoreTokens()) {

			stringTokenizersword = stringTokenizer.nextToken(); // holding the next token from stringTokenizer
			// checking whether the word is alphanumeric and not null

			if (stringTokenizersword.matches("^[a-zA-Z0-9]*$") && stringTokenizersword != null) {
				// ignoring the cases

				stringTokenizersword = stringTokenizersword.toLowerCase();
				// if the word is already present in map then increase the frequency

				if (tableForFrequency.containsKey(stringTokenizersword)) {
					tableForFrequency.put(stringTokenizersword, tableForFrequency.get(stringTokenizersword) + 1);
				} // end if

				// otherwise create a new key with word and add 1 as frequency
				else {
					tableForFrequency.put(stringTokenizersword, 1);
				} // end else

			} // end if

		} // end while
		// StdOut.println(tableForFrequency);
		int i = 5;
		for (Entry<String, Integer> s : tableForFrequency.entrySet()) {
			if (i == 0)
				break;
			StdOut.println(s.getKey() + ": " + s.getValue());
			i--;
		}
	}

	// main method for accessing the frequency count
	public static void main(String[] args) {

		// calling the method for accessing frequency
		frqcount();
	}
}