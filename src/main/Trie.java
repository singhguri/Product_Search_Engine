package main;

public class Trie {
	
	static TrieNode root;

	Trie() {
		root = new TrieNode();
	}
	
	static void add(String word)
    {
        TrieNode currNode = root;
      
        for (int i = 0; i < word.length(); i++)
        {
            int charIndex = word.charAt(i) - 'a';
            if (currNode.children[charIndex] == null)
            	currNode.children[charIndex] = new TrieNode();
      
            currNode = currNode.children[charIndex];
        }
        
        currNode.isEnd = true;
    }
      
    static boolean search(String word)
    {
        TrieNode currNode = root;
      
        for (int i = 0; i < word.length(); i++)
        {
            int charIndex = word.charAt(i) - 'a';
      
            if (currNode.children[charIndex] == null)
                return false;
      
            currNode = currNode.children[charIndex];
        }
      
        return currNode.isEnd;
    }

}