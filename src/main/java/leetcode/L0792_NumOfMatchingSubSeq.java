package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2023/4/26 14:14
 * @description : https://leetcode.cn/problems/number-of-matching-subsequences/
 * 匹配子序列的单词数
 */
public class L0792_NumOfMatchingSubSeq {
    static class Solution {

        private static class Pair {
            int index;
            int progress;

            Pair(int index, int progress) {
                this.index = index;
                this.progress = progress;
            }

            Pair(int index) {
                this(index, 0);
            }

            static Pair of(int index, int progress) {
                return new Pair(index, progress);
            }
        }

        // 多指针分桶
        public int numMatchingSubseq(String s, String[] words) {
            // 按words各个字符串的起始字母进行分桶，保存其所在words的index和匹配进度(已匹配的长度)
            Queue<Pair>[] bins = new Queue[26];
            for (int i = 0; i < 26; i ++) {
                bins[i] = new LinkedList<>();
            }

            for (int i = 0; i < words.length; i ++) {
                int binIndex = words[i].charAt(0) - 'a';
                bins[binIndex].offer(Pair.of(i, 0));
            }

            int ans = 0;
            for (int i = 0; i < s.length(); i ++) {
                int binIndex = s.charAt(i) - 'a';
                int size = bins[binIndex].size();
                while (size > 0) {
                    // 命中
                    Pair t = bins[binIndex].poll();
                    size --;
                    t.progress ++;
                    // 已经完全命中
                    if (t.progress == words[t.index].length()) {
                        ans ++;
                    } else {
                        // 进度更新，加入下一个字符所在的分桶里
                        int targetBinIndex = words[t.index].charAt(t.progress) - 'a';
                        bins[targetBinIndex].offer(t);
                    }
                }
            }

            return ans;
        }
    }

    static class SolutionNaive {
        public int numMatchingSubSeq(String s, String[] words) {
            int ans = 0;
            for (String word: words) {
                if (isSubSeq(s, word)) ans ++;
            }
            return ans;
        }

        public boolean isSubSeq(String s, String word) {
            int n = s.length(), m = word.length();
            if (m > n) return false;
            int i = 0, j = 0;
            while (i < n && j < m) {
                if (s.charAt(i) == word.charAt(j)) {
                    j ++;
                }
                i ++;
            }

            return j == m;
        }
    }

    static class SolutionBinarySearch {
        public int numMatchingSubseq(String s, String[] words) {
            // 储存各个字符的所在序号
            int n = s.length();
            List<Integer>[] pos = new List[26];
            for (int i = 0; i < 26; i ++) {
                pos[i] = new ArrayList<>();
            }

            for (int i = 0; i < n; i ++) {
                pos[s.charAt(i) - 'a'].add(i);
            }

            int ans = 0;
            for (String word: words) {
                int m = word.length();
                if (m > n) {
                    continue;
                }

                int i = -1;
                int j = 0;
                for (; j < m; j ++) {
                    int letter = word.charAt(j) - 'a';
                    i = binarySearch(pos[letter], i);
                    if (i == -1) {
                        break;
                    }
                }

                if (j == m) ans ++;
            }
            return ans;
        }

        // list中找到大于target的指针位置
        private int binarySearch(List<Integer> list, int target) {
            if (list == null || list.isEmpty() || list.get(list.size() - 1) <= target) return -1;
            int n = list.size();
            int left = 0, right = n - 1;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (list.get(mid) <= target) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            return list.get(left);
        }
    }

    public static void main(String[] args) {
        String s = "abcde";
        String[] words = {"a", "bb"};
        Solution sol = new Solution();
        System.out.println(sol.numMatchingSubseq(s, words));
    }

}
