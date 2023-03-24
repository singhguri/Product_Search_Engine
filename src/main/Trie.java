package main;

public class Trie {
    // This line declares a private instance variable root of type TrieNode which
    // will be used as the root of the Trie data structure.
    public static TrieNode root;

    /*
     * This is a constructor method for the Trie class.
     * It creates a new instance of the TrieNode class and assigns it to the root
     * instance variable.
     */
    public Trie() {
        root = new TrieNode();
    }

    /*
     * This method is used to insert a new word into the Trie data structure.
     * It takes a string word as input and begins by setting the node variable to
     * the root of the Trie.
     * Then, it iterates over each character in the word and for each character,
     * it computes an index value by subtracting the ASCII value of 'a' from the
     * ASCII value of the current character.
     * It then checks if the current node has a child node at the computed index,
     * and if not, it creates a new child node at that index.
     * Finally, it sets the isEndOfWord flag of the last node in the path to true,
     * indicating that the word has been fully inserted into the Trie
     */
    public static void insert(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isEndOfWord = true;
    }
    /*
     * This method is used to search for a word in the Trie data structure.
     * It takes a string word as input and begins by setting the node variable to
     * the root of the Trie.
     * Then, it iterates over each character in the word and for each character,
     * it computes an index value by subtracting the ASCII value of 'a' from the
     * ASCII value of the current character.
     * It then checks if the current node has a child node at the computed index,
     * and if not, it returns false.
     * Finally, it returns true if the isEndOfWord flag of the last node in the path
     * is true,
     * indicating that the word has been found in the Trie.
     */

    public boolean search(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - 'a';
            if (node.children[index] == null) {
                return false;
            }
            node = node.children[index];
        }
        return node.isEndOfWord;
    }

    /*
     * This is a private inner class named TrieNode which represents a node in the
     * Trie data structure.
     * It contains a boolean variable isEndOfWord which is true if the node marks
     * the end of a word, and an array of 26 TrieNode objects,
     * which represent the children of the current node.
     * The constructor initializes the isEndOfWord flag to false.
     * 
     */
    public static class TrieNode {
        boolean isEndOfWord;
        TrieNode[] children = new TrieNode[26];

        TrieNode() {
            isEndOfWord = false;
        }
    }
}
