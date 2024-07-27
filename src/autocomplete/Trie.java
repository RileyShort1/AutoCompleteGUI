package autocomplete;

import java.io.*;
import java.util.ArrayList;

public class Trie {
    private TrieNode root = new TrieNode();

    public void insert(String word, int weight) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i); // index
            if (current.children[c] == null) {
                // create new node here
                current.children[c] = new TrieNode();
            }
            // progress to next layer
            current = current.children[c];
        }

        current.isTerminal = true;
        current.weight = weight;
    }
    public boolean search(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i); // index
            if (current.children[c] == null) {
                return false;
            }
            else {
                // progress
                current = current.children[c];
            }
        }

        return current.isTerminal;
    }

    public void helper(TrieNode root, String prefix, ArrayList<WordPair> wordPairs) {
        if (root.isTerminal) {
            //System.out.println(strToComplete + " -> " + prefix + ", weight = " + root.weight);
            wordPairs.add(new WordPair(prefix, root.weight));
        }
        String s = prefix;
        for (int i = 0; i < 256; i++) {
            prefix = s;
            if (root.children[i] != null) {
                prefix = prefix + String.valueOf((char)i);
                helper(root.children[i], prefix, wordPairs);
            }
        }
    }

    public ArrayList<WordPair> completeString(String prefix){
        TrieNode current = root;
        ArrayList<WordPair> wordPairs = new ArrayList<>();
        for (int i = 0; i < prefix.length(); i++) {
            int c = prefix.charAt(i);
            if (current.children[c] != null) {
                current = current.children[c];
            }
            else {
                //System.out.println(prefix + " not in trie");
                return wordPairs;
            }
        }

        helper(current, prefix, wordPairs);
        return wordPairs;
    }
    public void checkElementsAtPrefix(String prefix) { // debug only
        TrieNode current = root;
        for (int i = 0; i < prefix.length(); i++) {
            int c = prefix.charAt(i);
            if (current.children[c] != null) {
                current = current.children[c];
            }
        }

        for (int i = 0 ; i < current.children.length ; i++) {
            if (current.children[i] != null) {
                System.out.println("Space Not Null: " + String.valueOf((char)i));
            }
        }
    }

    public void fillTrie() {
        int count = 0;
        try {
            InputStream stream = getClass().getResourceAsStream("mostCommonWords.txt");

            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(",");
                this.insert(words[0], Integer.parseInt(words[1]));
                count++;
            }
        }
        catch (IOException e) {
            System.out.println(e.toString());
        }

        System.out.println("Added " + count + " words\n");
    }
}


