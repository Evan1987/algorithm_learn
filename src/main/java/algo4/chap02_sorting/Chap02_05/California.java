package algo4.chap02_sorting.Chap02_05;

import java.util.Comparator;

/**
 * @author Evan
 * @date 2020/3/7 14:28
 */
public class California {
    public static class CaliforniaComparator implements Comparator<String> {
        private static final String ORDER = "RWQOJMVAHBSGZXNTCIEKUPDYFL";

        @Override
        public int compare(String o1, String o2) {
            int l1 = o1.length(), l2 = o2.length();
            int m = Math.min(l1, l2);
            for(int i = 0; i < m; i ++){
                int index1 = ORDER.indexOf(o1.charAt(i));
                int index2 = ORDER.indexOf(o2.charAt(i));
                int cmp = Integer.compare(index1, index2);
                if(cmp != 0) return cmp;
            }
            return l1 - l2;
        }
    }
}
