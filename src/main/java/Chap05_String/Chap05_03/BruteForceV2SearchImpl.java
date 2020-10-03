package Chap05_String.Chap05_03;

/**
 * @author zhaochengming
 * @date 2020/10/3 20:49
 * @desc 显式回退
 */
public class BruteForceV2SearchImpl implements ISearch {
    @Override
    public int search(String pattern, String text) {
        int j, M = pattern.length();
        int i, N = text.length();
        for(i = 0, j = 0; i < N && j < M; i ++) {
            if(text.charAt(i) == pattern.charAt(j))
                j ++;
            else {
                // 回退两个指针的值
                i -= j;     // i指向该次匹配的开始位置的下一个位置 （用j未自增来保证）
                j = 0;      // j回退模式开头
            }
        }

        if(j == M) return i - M;
        return N;
    }
}
