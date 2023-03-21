package main;


public class TrieNode {
	boolean isEnd;
	TrieNode[] children = new TrieNode[26];
	
	TrieNode() {
		isEnd = false;
		for(int i = 0 ; i < 26 ; i++) {
			children[i] = null;
		}
	}
}