package leetcode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : zhaochengming
 * @date : 2023/8/2 14:20
 * @description : https://leetcode.cn/problems/people-whose-list-of-favorite-companies-is-not-a-subset-of-another-list/
 * 收藏清单，找到其清单不是其他人清单子集的下标集合
 */
public class L1452_NotASubSetOfOthers {
    static class Solution {

        public List<Integer> peopleIndexes(List<List<String>> favoriteCompanies) {
            int n = favoriteCompanies.size();

            // 构造反查map
            Map<String, List<Integer>> company2Indexes = new HashMap<>();
            for (int i = 0; i < n; i ++) {
                for (String company: favoriteCompanies.get(i)) {
                    company2Indexes.computeIfAbsent(company, k -> new LinkedList<>()).add(i);
                }
            }

            // 按照公司数量降序
            // 越大的越不容易成为子集
            Integer[] idx = new Integer[n];
            for (int i = 0; i < n; i ++) {
                idx[i] = i;
            }

            Arrays.sort(idx, (a, b) -> favoriteCompanies.get(b).size() - favoriteCompanies.get(a).size());

            List<Integer> ans = new ArrayList<>();
            for (int id: idx) {
                List<String> companies = favoriteCompanies.get(id);

                // 不断取交集
                Set<Integer> candidates = new HashSet<>(company2Indexes.get(companies.get(0)));
                for (int j = 1; j < companies.size() && candidates.size() > 1; j ++) {
                    candidates = company2Indexes.get(companies.get(j)).stream().filter(candidates::contains).collect(Collectors.toSet());
                }

                if (candidates.size() == 1) {
                    ans.add(id);
                }

            }

            ans.sort(Integer::compareTo);

            return ans;
        }
    }
}
