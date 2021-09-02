package algo4.chap02_sorting;

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
    BOTTOM_UP_MERGE("bottom-up-merge"),
    QUICK("array"),

    ;

    private String method;
    SortMethodEnum(String method){
        this.method = method;
    }
}
