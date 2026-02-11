// Time Complexity : O(N) for constructor and O(L) for input
// Space Complexity : O(N)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

// Your code here along with comments explaining your approach
// Using a Trie to store the sentences and a map to store each sentence's frequency
// At each node of a Trie we store the top 3 sentences to return
// at # => When we insert a new sentence, we iterate through the Trie and update each node's top 3 
// For search prefix is O(L) and return top3 is O(1)

import java.util.HashMap;

public class AutocompleteSystem {

    TrieNode root; // space O(N)
    HashMap<String, Integer> freqMap; // space O(N)
    StringBuilder sb; // space O(L)

    class TrieNode {
        HashMap<Character, TrieNode> children;
        List<String> top3;

        TrieNode() {
            children = new HashMap<>();
            top3 = new ArrayList<>();
        }
    }

    Comparator<String> comp = (a, b) -> {
        int freqA = freqMap.get(a);
        int freqB = freqMap.get(b);

        if (freqA != freqB) {
            return freqB - freqA;
        }
        return a.compareTo(b);
    };

    private void insert(String word) {

        TrieNode curr = root;
        for (char c : word.toCharArray()) { // O(L)
            curr.children.putIfAbsent(c, new TrieNode());
            curr = curr.children.get(c);

            List<String> list = curr.top3;
            list.add(word);
            Collections.sort(list, comp); // O(1) since only 3
            if (list.size() > 3) {
                list.remove(list.size() - 1); // O(1)
            }
        }
    }

    private List<String> search(String prefix) {
        TrieNode curr = root;
        for (char c : prefix.toCharArray()) { // O(L)
            if (!curr.children.containsKey(c))
                return new ArrayList<>();
            curr = curr.children.get(c);
        }
        return curr.top3; // O(1)
    }

    public AutocompleteSystem(String[] sentences, int[] times) {
        root = new TrieNode();
        sb = new StringBuilder();
        freqMap = new HashMap<>();
        for (int i = 0; i < times.length; i++) { // O(N)
            freqMap.put(sentences[i], times[i]);
            insert(sentences[i]);
        }
    }

    public List<String> input(char c) {
        if (c == '#') { // O(L)
            String newWord = sb.toString();
            freqMap.put(newWord, freqMap.getOrDefault(newWord, 0) + 1);
            insert(newWord);
            sb = new StringBuilder();
            return new ArrayList<>();
        }

        sb.append(c);
        return search(sb.toString()); // O(L)
    }
}
