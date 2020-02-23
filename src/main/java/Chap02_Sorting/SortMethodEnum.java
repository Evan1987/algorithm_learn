package Chap02_Sorting;

/**
 * @author Evan
 * @date 2020/2/23 18:21
 */
public enum SortMethodEnum {

    SELECTION("selection"),
    BUBBLE("bubble"),
    INSERTION("insertion"),
    SHELL("shell"),
    TOP_DOWN_MERGE("top-down-merge"),
    QUICK("array"),
    ;

    private String method;
    private SortMethodEnum(String method){
        this.method = method;
    }
}
