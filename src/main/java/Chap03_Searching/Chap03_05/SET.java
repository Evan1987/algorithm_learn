package Chap03_Searching.Chap03_05;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;

/**
 * @author Evan
 * @date 2020/5/6 18:15
 */
public class SET<Key extends Comparable<? extends Key>> implements Iterable<Key> {

    private TreeSet<Key> set;

    public SET(){
        this.set = new TreeSet<>();
    }

    public SET(SET<Key> x){
        this.set = new TreeSet<>(x.set);
    }

    private void nullKeyCheck(Key key){
        if(key == null) throw new IllegalArgumentException("null key is invalid!");
    }

    private void emptyCheck(){
        if(this.isEmpty()) throw new NoSuchElementException("the set is empty!");
    }

    public boolean add(Key key){
        nullKeyCheck(key);
        return this.set.add(key);
    }

    public boolean contains(Key key){
        nullKeyCheck(key);
        return this.set.contains(key);
    }

    public boolean delete(Key key){
        nullKeyCheck(key);
        return this.set.remove(key);
    }

    public int size(){
        return this.set.size();
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public Key max(){
        emptyCheck();
        return this.set.last();
    }

    public Key min(){
        emptyCheck();
        return this.set.first();
    }

    public Key ceiling(Key key){
        nullKeyCheck(key);
        return this.set.ceiling(key);
    }

    public Key floor(Key key){
        nullKeyCheck(key);
        return this.set.floor(key);
    }

    public SET<Key> union(SET<Key> that){
        if(that == null) throw new IllegalArgumentException("another set is null");
        SET<Key> c = new SET<>();
        for(Key x: this) c.add(x);
        for(Key x: that) c.add(x);
        return c;
    }

    public SET<Key> intersects(SET<Key> that){
        if(that == null) throw new IllegalArgumentException("another set is null");
        SET<Key> c = new SET<>();
        if(this.size() < that.size()){
            for(Key x: this){
                if(that.contains(x)) c.add(x);
            }
        }
        else{
            for(Key x: that){
                if(this.contains(x)) c.add(x);
            }
        }
        return c;
    }

    @Override
    public boolean equals(Object other) {
        if(other == this) return true;
        if(other == null) return false;
        if(other.getClass() != this.getClass()) return false;
        SET that = (SET) other;
        return this.set.equals(that.set);
    }

    @NotNull
    @Override
    public Iterator<Key> iterator() {
        return this.set.iterator();
    }
}
