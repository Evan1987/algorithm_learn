package leetcode;


import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/16 23:17
 * @description : https://leetcode.cn/problems/minimum-time-difference/
 */
public class L0539_MinimumTimeDifference {
    static class Solution {
        public int findMinDifference(List<String> timePoints) {
            int n = timePoints.size();
            if (n > 1440) {
                return 0;
            }
            int[] minutes = new int[n];

            int i = 0;
            for (String timeString: timePoints) {
                int sepIndex = timeString.indexOf(':');
                int hour = Integer.parseInt(timeString.substring(0, sepIndex));
                int minute = Integer.parseInt(timeString.substring(sepIndex + 1));
                minutes[i ++] = hour * 60 + minute;
            }

            Arrays.sort(minutes);

            int curr = minutes[0];
            int ans = diffMinutes(curr, minutes[n - 1]);

            for (int k = 1; k < n; k ++) {
                int diff = diffMinutes(curr, minutes[k]);
                if (diff == 0) return 0;
                ans = Math.min(diff, ans);
                curr = minutes[k];
            }

            return ans;
        }

        private static int diffMinutes(int lo, int hi) {
            int diff = hi - lo;
            return diff > 720 ? 1440 - diff : diff;
        }


    }
}
