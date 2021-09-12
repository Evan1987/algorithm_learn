package hanlplearn.collection.trie.bintrie;

import hanlplearn.collection.trie.bintrie.util.ArrayTool;

/**
 * 深度大于等于2的子节点
 * */
@SuppressWarnings("unchecked")
public class Node<V> extends BaseNode<V> {

    public Node(char c, Status status, V value) {
        this.c = c;
        this.status = status;
        this.value = value;
    }


    protected int indexOf(BaseNode<V> node) {
        return indexOf(node.getChar());
    }

    protected int indexOf(char c) {
        return ArrayTool.binarySearch(this.children, c);
    }

    @Override
    protected boolean addChild(BaseNode<V> child) {
        boolean add = false;
        if (this.children == null) {
            this.children = new BaseNode[0];
        }

        int index = indexOf(child);
        if (index >= 0) {
            BaseNode<V> target = this.children[index];
            switch (child.status) {
                case UNDEFINED:
                    if (target.status != Status.NOT_WORD) {
                        target.status = Status.NOT_WORD;
                        target.setValue(null);
                        add = true;
                    }
                    break;
                case NOT_WORD:
                    if (target.status == Status.WORD_END) {
                        target.status = Status.WORD_MIDDLE;
                    }
                    break;
                case WORD_END:
                    if (target.status != Status.WORD_END) {
                        target.status = Status.WORD_MIDDLE;
                    }
                    if (target.getValue() == null) {
                        add = true;
                    }
                    target.setValue(child.getValue());
                    break;
            }
        } else {
            BaseNode<V>[] newChildren = new BaseNode[this.children.length + 1];
            int insert = -(index + 1);
            System.arraycopy(this.children, 0, newChildren, 0, insert);
            newChildren[insert] = child;
            System.arraycopy(this.children, insert, newChildren, insert + 1,this.children.length - insert);
            this.children = newChildren;
            add = true;
        }

        return add;
    }

    @Override
    protected BaseNode<V> getChild(char c) {
        if (this.children == null || this.children.length == 0) return null;
        int index = indexOf(c);
        if (index < 0) return null;
        return this.children[index];
    }
}
