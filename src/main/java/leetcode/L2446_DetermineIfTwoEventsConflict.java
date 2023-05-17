package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/17 09:40
 * @description : https://leetcode.cn/problems/determine-if-two-events-have-conflict/
 * 判断两个事件是否存在冲突
 */
public class L2446_DetermineIfTwoEventsConflict {
    static class Solution {
        public boolean haveConflict(String[] event1, String[] event2) {
            return event1[1].compareTo(event2[0]) >= 0 && event1[0].compareTo(event2[1]) <= 0;
        }
    }
}
