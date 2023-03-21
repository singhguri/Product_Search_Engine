package main;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class SearchFrequency {

	public static void FrequentCount(String keyword) throws IOException {
		keyword.toLowerCase();
		FileReader fr = new FileReader("dictionary/Wordsmemory.txt"); // File Reader Object is created
		FileWriter fw = new FileWriter("dictionary/Wordsmemory.txt", true); // FileWriter object is created
		BufferedReader br = new BufferedReader(fr); // BufferedReader object is created
		String s1;
		String input = keyword; // The input word is looked up
		int count = 0; // Initialize the word to zero
		while ((s1 = br.readLine()) != null) // reading information from a file
		{
			String[] wordsplit = s1.split(" "); // The Splitting the word using space
			for (String word : wordsplit) {
				if (word.equals(input)) // Searching for the given word
				{
					count++; // If it is Present increase the count by one
				}
			}
		}
		if (count != 0) // Check if the count not equal to zero
		{
			System.out.println("*************************************************");
			System.out.println("This word has been searched for " + count + " times.");
			System.out.println("*************************************************");
			fw.write(keyword + "\n");
			fw.close();
		} else {
			System.out.println("*************************************************");
			System.out.println("The given word was not searched before therefore adding the word to the library.");
			System.out.println("*************************************************");

			fw.write(keyword + "\n");
			fw.close();
		}

		fr.close();
	}
}
