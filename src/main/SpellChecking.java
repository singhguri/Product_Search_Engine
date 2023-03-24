package main;

import java.io.*;
import java.util.*;

public class SpellChecking {
	
	private static ArrayList<String> dictionary = new ArrayList<>();
	private static HashMap<String,Integer> mp; 
	public static String[] suggestions; 
	private static Map<String, Integer> mp2; 
	private static List<Map.Entry<String, Integer> > list; 
	private static HashMap<String, Integer> temp; 
	
	private static int numberOfSuggestions; 
	
	//Constructor
	SpellChecking(){
		numberOfSuggestions = 10;
		mp  = new HashMap<>();
		dictionary = new ArrayList<>();
		suggestions = new String[numberOfSuggestions];
	}
	
	// How many incorrect word suggestions are required?
	public void setnumberOfSuggestions(int N) {
		numberOfSuggestions = N;
		suggestions = new String[numberOfSuggestions]; 
	}
	
	
	// checking if the string is correct
	public boolean isCorrect(String query)  throws IOException{
        // Returns true if the query string is found in the dictionary, else returns false.
		if(dictionary.contains(query)) return true;
        return false; 
    }
	
	
	public static int Minimum(int a, int b) {
		if(a < b) return a;
		return b; 
	}
	
	//using edit distance concept between two words
	public static int getEditDistance(String word1, String word2) {
		int length1 = word1.length();
		int length2 = word2.length();
		int[][] dp = new int[length1 + 1][length2 + 1];	// Edit distance table

		for (int i = 0; i <= length1; i++) { dp[i][0] = i;	}
		for (int j = 0; j <= length2; j++) { dp[0][j] = j; }
		
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
		System.out.println("The dictionary contains: "); 
		for(int i=0; i<len; i++) {
			System.out.println(dictionary.get(i)); 
		}
		return; 
	}
		
	// assembles every word from the vocabulary
	public void getVocab() throws IOException 
	{
		File files = new File("C:\\Users\\bhavi\\eclipse-workspace\\Product_Search_Engine\\dictionary\\Oxford English Dictionary.txt");  //This will open the file containing the dictionary words
		//System.out.println("File exist: "+files.exists());
		
		StringBuilder SB = new StringBuilder();
		StringTokenizer STK; 

		BufferedReader BR = new BufferedReader(new FileReader(files));
		String str;
		
		//adding each string in the String Builder in 
		while ((str = BR.readLine()) != null) {    
			SB.append(str);
            SB.append(" "); 
		}
		BR.close(); 
		String allwords = SB.toString(); 
        
		STK = new StringTokenizer(allwords, "0123456789 ,`*$|~(){}_@><=+[]\\?;/&#-.!:\"'\n\t\r");
		
		
		while (STK.hasMoreTokens()) {
			String tk = STK.nextToken().toLowerCase(Locale.ROOT);
			if (!dictionary.contains(tk)) {
				dictionary.add(tk);
			}
		}
	}
    
	//receives suggestions for a false word
	public  String[] getAltWords(String query) {
 
		mp  = new HashMap<>();
		
		for (int i=0; i<dictionary.size(); i++) 
		{
			String s = dictionary.get(i); 
			int editDis = getEditDistance(query, s);
			mp.put(s, editDis);
		}
		
		list = new LinkedList<>(mp.entrySet());

		list.sort(Map.Entry.comparingByValue());

		// Data from a sorted list will then be added to a hash map.
		temp = new LinkedHashMap<>();
		for (Map.Entry<String, Integer> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		mp2 = temp; 
		//System.out.println(mp2.size());
		int rank = 0;
		for (Map.Entry<String, Integer> en : mp2.entrySet()) 
		{
			if (en.getValue() != 0) 
			{
				//System.out.println(en.getKey() +" "+en.getValue()+"\n");
				suggestions[rank] = en.getKey();
				rank++;
				if (rank == 10){ break; }
			}
		}
		return suggestions;
	}

	public static void main(String[] args) throws IOException
	{
		
		//This will be used to debug the system.
		 SpellChecking sp = new SpellChecking();
		 int N = 10;
		 sp.getVocab();
		 sp.setnumberOfSuggestions(N);
		 String s[] = sp.getAltWords("computer");
		 System.out.println(s.length);
		 //String s[] = sp.getAltWords("digi");
         for(int i=0; i<10; i++)
         {
             System.out.println(s[i]); 
         }
	}
}
