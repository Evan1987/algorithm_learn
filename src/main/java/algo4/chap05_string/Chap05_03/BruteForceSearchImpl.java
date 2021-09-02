package algo4.chap05_string.Chap05_03;

/**
 * @author zhaochengming
 * @date 2020/10/3 20:40
 */
public class BruteForceSearchImpl implements ISearch {
    @Override
    public int search(String pattern, String text) {
        int M = pattern.length();
        int N = text.length();
        for (int i = 0; i <= N - M; i ++) {
            int j;
            for (j = 0; j < M; j ++)
                if(text.charAt(i + j) != pattern.charAt(j))
                    break;
            if(j == M) return i;    // 找到匹配
        }
        return N;                   // 未找到匹配
    }
}
