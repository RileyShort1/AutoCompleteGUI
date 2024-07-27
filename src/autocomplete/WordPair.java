package autocomplete;

public class WordPair implements Comparable<WordPair> {
    String word;
    Integer rank;

    public WordPair(String word, int rank) {
        this.word = word;
        this.rank = rank;
    }
    public String getWord() {
        return this.word;
    }

    @Override
    public int compareTo(WordPair o) {
        return this.rank.compareTo(o.rank);
    }

}
