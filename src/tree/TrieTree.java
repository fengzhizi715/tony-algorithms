package tree;

import java.util.ArrayList;
import java.util.List;

/**
 * TrieTree算法
 * Created by tony on 16/8/24.
 */
public class TrieTree {

    private TrieNode root = new TrieNode();

    public class TrieNode{
        protected int words; // 单词个数
        protected int prefixes; // 前缀个数
        protected TrieNode[] child; // 子节点

        TrieNode() {
            this.words = 0;
            this.prefixes = 0;
            child = new TrieNode[26];
            for (int i = 0; i < child.length; i++) {
                child[i] = null;
            }
        }
    }

    /**
     * 获取trie树中所有的词
     *
     * @return
     */
    public List<String> getAllWords() {

        List<String> words = new ArrayList<String>();
        TrieNode[] edges = root.child;

        for (int i = 0; i < edges.length; i++) {
            if (edges[i] != null) {
                String word = "" + (char) ('a' + i);
                depthFirstSearchWords(words, edges[i], word);
            }
        }
        return words;
    }

    /**
     *
     * @param words
     * @param node
     * @param wordSegment
     */
    private void depthFirstSearchWords(List words, TrieNode node,
                                       String wordSegment) {
        if (node.words != 0) {
            words.add(wordSegment);
        }

        TrieNode[] edges = node.child;
        for (int i = 0; i < edges.length; i++) {
            if (edges[i] != null) {
                String newWord = wordSegment + (char) ('a' + i);
                depthFirstSearchWords(words, edges[i], newWord);
            }
        }
    }

    /**
     * 判断trie树中是否有以prefix为前缀的单词
     * @param prefix
     * @return
     */
    public boolean startsWith(String prefix) {
        TrieNode node = root;
        int length = prefix.length();
        for(int i=0; i<length; ++i)
            if(node!=null)
                node = node.child[prefix.charAt(i)-'a'];
            else
                return false;

        return node != null;
    }

    /**
     * 计算指定前缀单词的个数
     *
     * @param prefix
     * @return
     */
    public int countPrefixes(String prefix) {
        return countPrefixes(root, prefix);
    }

    private int countPrefixes(TrieNode node, String prefixSegment) {
        if (prefixSegment.length() == 0) { // reach the last character of the
            // word
            return node.prefixes;
        }

        char c = prefixSegment.charAt(0);
        int index = c - 'a';
        return node.child[index] == null ? 0 : countPrefixes(node.child[index], prefixSegment.substring(1));
    }

    /**
     * 计算完全匹配单词的个数
     *
     * @param word
     * @return
     */
    public int countWords(String word) {
        return countWords(root, word);
    }

    private int countWords(TrieNode node, String wordSegment) {
        if (wordSegment.length() == 0) { // reach the last character of the word
            return node.words;
        }

        char c = wordSegment.charAt(0);
        int index = c - 'a';
        return node.child[index] == null ? 0 : countWords(node.child[index], wordSegment.substring(1));
    }

    /**
     * 向tire树添加一个词
     *
     * @param word
     *
     */

    public void addWord(String word) {
        addWord(root, word);
    }

    /**
     * Add the word from the specified node.
     *
     * @param node
     *            The specified node.
     * @param word
     *            The word to be added.
     */

    private void addWord(TrieNode node, String word) {
        if (word.length() == 0) { // if all characters of the word has been
            // added
            node.words++;
        } else {
            node.prefixes++;
            char c = word.charAt(0);
            c = Character.toLowerCase(c);
            int index = c - 'a';
            if (node.child[index] == null) { // if the edge does NOT exist
                node.child[index] = new TrieNode();
            }

            addWord(node.child[index], word.substring(1)); // go the the next
            // character
        }
    }

    /**
     * 向tire树删除一个词
     *
     * @param word
     *
     */
    public void removeWord(String word) {

        // 如果word不在trie中,则返回
        if (countWords(word)==0) {
            return;
        }

        removeWord(root, word);
    }

    /**
     *
     * @param node
     * @param word
     */
    private void removeWord(TrieNode node, String word) {
        if (word.length()==0) {
            if (node!=null && node.words!=0) {
                node.words--;
            }

            return;
        } else {
            node.prefixes--;
            char c = word.charAt(0);
            c = Character.toLowerCase(c);
            int index = c - 'a';
            removeWord(node.child[index], word.substring(1));
        }
    }

    /**
     * 返回指定字段前缀匹配最长的单词。
     *
     * @param word
     * @return
     */
    public String getMaxMatchWord(String word) {
        String s = "";
        String temp = "";// 记录最近一次匹配最长的单词
        char[] w = word.toCharArray();
        TrieNode vertex = root;
        for (int i = 0; i < w.length; i++) {
            char c = w[i];
            c = Character.toLowerCase(c);
            int index = c - 'a';
            if (vertex.child[index] == null) {// 如果没有子节点
                if (vertex.words != 0)// 如果是一个单词，则返回
                    return s;
                else
                    // 如果不是一个单词则返回null
                    return null;
            } else {
                if (vertex.words != 0)
                    temp = s;
                s += c;
                vertex = vertex.child[index];
            }
        }
        // trie中存在比指定单词更长（包含指定词）的单词
        if (vertex.words == 0)//
            return temp;
        return s;
    }

    public static void main(String args[]) {
        TrieTree trie = new TrieTree();
        trie.addWord("abcedfddddddd");
        trie.addWord("a");
        trie.addWord("ba");
        trie.addWord("abce");
        trie.addWord("abcedfdddd");
        trie.addWord("abcef");
        trie.addWord("abce");
        trie.removeWord("a");

        String maxMatch = trie.getMaxMatchWord("abceffff");
        System.out.println(maxMatch);
    }
}
