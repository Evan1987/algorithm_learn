package Chap02_Sorting.Chap02_05;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * @author Evan
 * @date 2020/3/7 14:13
 * Ex2.5.14 逆域名排序
 */
public class Domain implements Comparable<Domain> {
    private String domain;
    private String[] names;

    public Domain(String domain){
        this.domain = domain;
        this.names = domain.split("\\.");
    }

    @Override
    public String toString() {
        return this.domain;
    }

    @Override
    public int compareTo(@NotNull Domain that) {
        int i = this.names.length - 1;
        int j = that.names.length - 1;
        while(i >= 0 && j >= 0){
            int cmp = this.names[i --].compareTo(that.names[j --]);
            if(cmp != 0) return cmp;
        }
        return i - j;
    }

    public static void main(String[] args) {
        String[] names = {"www.google.com", "mails.163.com", "cs.princeton.com", "github.com"};
        Domain[] domains = new Domain[names.length];
        for(int i = 0; i < names.length; i ++)
            domains[i] = new Domain(names[i]);
        Arrays.sort(domains);
        for(Domain domain: domains)
            System.out.println(domain);
    }
}


