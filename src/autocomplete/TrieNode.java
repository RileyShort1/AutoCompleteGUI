package autocomplete;

public class TrieNode {
    public TrieNode[] children;
    public boolean isTerminal;
    public int weight;

    public TrieNode() {
        children = new TrieNode[256];
        isTerminal = false;
        weight = Integer.MAX_VALUE;
    }
}
