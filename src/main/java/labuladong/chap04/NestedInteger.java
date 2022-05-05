package labuladong.chap04;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/5 10:42
 * @description : 嵌套列表， N叉树
 */
public class NestedInteger implements Iterator<Integer> {

    private final Integer val;
    private final List<NestedInteger> list;
    private final NestedIntegerIterator iterator;

    public NestedInteger(Integer val) {
        this.val = val;
        this.list = null;
        this.iterator = new NestedIntegerIterator(this);
    }

    public NestedInteger(List<NestedInteger> list) {
        this.list = list;
        this.val = null;
        this.iterator = new NestedIntegerIterator(this.list);
    }

    public boolean isInteger() {
        return this.val != null;
    }

    public Integer getInteger() {
        return this.val;
    }

    public List<NestedInteger> getList() {
        return this.list;
    }

    private static class NestedIntegerIterator implements Iterator<Integer> {

        private LinkedList<NestedInteger> list;

        NestedIntegerIterator(List<NestedInteger> nestedList) {
            this.list = new LinkedList<>(nestedList);
        }

        NestedIntegerIterator(NestedInteger nested) {
            this.list = new LinkedList<>();
            this.list.add(nested);
        }


        @Override
        public boolean hasNext() {
            // 保证list第一个元素始终是整数
            while (!this.list.isEmpty() && !this.list.get(0).isInteger()) {
                LinkedList<NestedInteger> first = new LinkedList<>(this.list.remove(0).getList());
                // 按顺序添加到表头
                while (!first.isEmpty()) {
                    this.list.addFirst(first.pollLast());
                }
            }

            return !this.list.isEmpty();
        }

        @Override
        public Integer next() {
            return this.list.remove(0).getInteger();
        }
    }


    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public Integer next() {
        return this.iterator.next();
    }

    public static void main(String[] args) {
        NestedInteger node1 = new NestedInteger(1);
        NestedInteger node2 = new NestedInteger(2);
        NestedInteger node3 = new NestedInteger(3);
        NestedInteger node4 = new NestedInteger(4);
        NestedInteger node5 = new NestedInteger(5);

        NestedInteger node12 = new NestedInteger(Arrays.asList(node1, node2));
        NestedInteger node45 = new NestedInteger(Arrays.asList(node4, node5));
        NestedInteger root = new NestedInteger(Arrays.asList(node12, node3, node45));

        while (root.hasNext()) {
            System.out.println(root.next());  // 1, 2, 3, 4, 5
        }
    }
}
