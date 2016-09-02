package tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 把trie tree压缩成两个一维数组base,check的DS-Tree(digital search tree)算法，称为double-array trie(简称DAT)
 * base和check的关系满足下述条件：
 * base[s] + c = t
 * check[t] = s
 * 其中s和t代表某个状态在数组中的下标，c代表变量的编号。
 * Created by tony on 16/9/1.
 */
public class DoubleArrayTrie {

    private final char END_CHAR = '\0';

    private int pos = 1;

    private int count = 0;

    private int base[];

    private int check[];

    private char tail[];

    private Map<Character, Integer> map = new HashMap<Character, Integer>();

    public DoubleArrayTrie(int size) {
        base = new int[size];
        check = new int[size];
        tail = new char[size];
        base[1] = 1;
        map.put(END_CHAR, 1);
    }

    private void extendArray() {
        base = Arrays.copyOf(base, base.length * 2);
        check = Arrays.copyOf(check, check.length * 2);
    }

    private void extendsTail() {
        tail = Arrays.copyOf(tail, tail.length * 2);
    }

    private int charCode(char c) {
        Integer code = map.get(c);
        return code != null ? code : compute(c, count++);
    }

    private int compute(char c, int count) {
        if (!map.containsKey(c)) {
            map.put(c, count);
        }
        return count;
    }

    private int copy2TailArray(String s, int p) {
        int pos = this.pos;
        while (s.length() - p + 1 > tail.length - pos) {
            extendsTail();
        }
        for (int i = p; i < s.length(); ++i) {
            tail[pos] = s.charAt(i);
            pos++;
        }
        return pos;
    }

    private int check(Integer[] set) {
        for (int i = 1;; ++i) {
            boolean flag = true;
            for (int j = 0; j < set.length; ++j) {
                int cur_p = i + set[j];
                if (cur_p >= base.length)
                    extendArray();
                if (base[cur_p] != 0 || check[cur_p] != 0) {
                    flag = false;
                    break;
                }
            }
            if (flag)
                return i;
        }
    }

    private ArrayList<Integer> childList(int p) {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for (int i = 1; i <= map.size(); ++i) {
            if (base[p] + i >= check.length)
                break;
            if (check[base[p] + i] == p) {
                ret.add(i);
            }
        }
        return ret;
    }

    private boolean tailContainString(int start, String s2) {
        for (int i = 0; i < s2.length(); ++i) {
            if (s2.charAt(i) != tail[i + start])
                return false;
        }
        return true;
    }

    private boolean tailMatchString(int start, String s2) {
        s2 += END_CHAR;
        for (int i = 0; i < s2.length(); ++i) {
            if (s2.charAt(i) != tail[i + start])
                return false;
        }
        return true;
    }

    public void addWords(List<String> words) {
        for (String word : words) {
            this.addWord(word);
        }
    }

    public void addWord(String word) {
        word += END_CHAR;
        int s = 1;
        int t;
        for (int i = 0; i < word.length(); ++i) {
            // 获取状态位置 "t = base[s]+c"
            t = base[s] + charCode(word.charAt(i));
            // 如果长度超过现有，拓展数组
            if (t >= base.length)
                extendArray();

            // 空闲状态
            if (base[t] == 0 && check[t] == 0) {
                base[t] = -pos;
                check[t] = s;
                pos = copy2TailArray(word, i + 1);
                break;
            } else
                // 已存在状态
                if (base[t] > 0 && check[t] == s) {
                    s = t;
                    continue;
                } else
                    // 冲突 1：遇到 base[t]小于0的，即遇到一个被压缩存到Tail中的字符串
                    if (base[t] < 0 && check[t] == s) {
                        int head = -base[t];
                        // 插入重复字符串
                        if (word.charAt(i + 1) == END_CHAR && tail[head] == END_CHAR) {
                            break;
                        }
                        // 公共字母的情况，因为上一个判断已经排除了结束符，所以一定是2个都不是结束符
                        if (tail[head] == word.charAt(i + 1)) {
                            int avail_base = check(new Integer[] { charCode(word.charAt(i + 1)) });
                            base[t] = avail_base;
                            check[avail_base + charCode(word.charAt(i + 1))] = t;
                            base[avail_base + charCode(word.charAt(i + 1))] = -(head + 1);
                            s = t;
                            continue;
                        } else {
                            // 2个字母不相同的情况，可能有一个为结束符
                            int avail_base;
                            avail_base = check(new Integer[] { charCode(word.charAt(i + 1)), charCode(tail[head]) });
                            base[t] = avail_base;
                            check[avail_base + charCode(tail[head])] = t;
                            check[avail_base + charCode(word.charAt(i + 1))] = t;
                            // Tail 为END_FLAG 的情况
                            if (tail[head] == END_CHAR)
                                base[avail_base + charCode(tail[head])] = 0;
                            else
                                base[avail_base + charCode(tail[head])] = -(head + 1);
                            if (word.charAt(i + 1) == END_CHAR)
                                base[avail_base + charCode(word.charAt(i + 1))] = 0;
                            else
                                base[avail_base + charCode(word.charAt(i + 1))] = -pos;
                            pos = copy2TailArray(word, i + 2);
                            break;
                        }
                    } else
                        // 冲突2：当前结点已经被占用，需要调整pre的base
                        if (check[t] != s) {
                            ArrayList<Integer> list1 = childList(s);
                            int toBeAdjust;
                            ArrayList<Integer> list = null;
                            if (true) {
                                toBeAdjust = s;
                                list = list1;
                            }
                            int origin_base = base[toBeAdjust];
                            list.add(charCode(word.charAt(i)));
                            int avail_base = check((Integer[]) list.toArray(new Integer[list.size()]));
                            list.remove(list.size() - 1);
                            base[toBeAdjust] = avail_base;
                            for (int j = 0; j < list.size(); ++j) {
                                // BUG
                                int tmp1 = origin_base + list.get(j);
                                int tmp2 = avail_base + list.get(j);
                                base[tmp2] = base[tmp1];
                                check[tmp2] = check[tmp1];
                                // 有后续
                                if (base[tmp1] > 0) {
                                    ArrayList<Integer> subsequence = childList(tmp1);
                                    for (int k = 0; k < subsequence.size(); ++k) {
                                        check[base[tmp1] + subsequence.get(k)] = tmp2;
                                    }
                                }
                                base[tmp1] = 0;
                                check[tmp1] = 0;
                            }
                            // 更新新的t
                            t = base[s] + charCode(word.charAt(i));
                            if (word.charAt(i) == END_CHAR)
                                base[t] = 0;
                            else
                                base[t] = -pos;
                            check[t] = s;
                            pos = copy2TailArray(word, i + 1);
                            break;
                        }
        }
    }

    /**
     * 判断当前词是否在DoubleArrayTrie中
     * @param word
     * @return
     */
    public boolean exists(String word) {
        int s = 1;
        int t = 0;
        for (int i = 0; i < word.length(); ++i) {
            t = base[s] + charCode(word.charAt(i));
            if (t < check.length && check[t] != s)
                return false;
            if (t < base.length && base[t] < 0) {
                if (tailMatchString(-base[t], word.substring(i + 1)))
                    return true;
                return false;
            }
            s = t;
        }

        return t < base.length && (base[t] + charCode(END_CHAR) < check.length) && check[base[s] + charCode(END_CHAR)] == s;
    }

    private int find(String word) {
        int s = 1;
        int t = 0;
        for (int i = 0; i < word.length(); ++i) {
            // BUG
            t = base[s] + charCode(word.charAt(i));

            if (t < check.length && check[t] != s) {
                return -1;
            }

            if (t < base.length && base[t] < 0) {
                if (tailContainString(-base[t], word.substring(i + 1))) {
                    return t;
                }
                return -1;
            }

            s = t;
        }
        return t;
    }

    public boolean prefix(String word) {
        int p = this.find(word);
        return p < base.length && p > 0;
    }

    public static void main(String[] args) throws Exception {
        DoubleArrayTrie tree = new DoubleArrayTrie(1024);
        tree.addWord("abc");
        tree.addWord("acde");
        tree.addWord("abcde");
        tree.addWord("abcd");
        tree.addWord("accompany");
        tree.addWord("ba");
        tree.addWord("bad");
        String word = "b";
        System.out.println(tree.find(word));
        System.out.println(tree.exists(word));
        System.out.println(tree.prefix(word));
    }
}
