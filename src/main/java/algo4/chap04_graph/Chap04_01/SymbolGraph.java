package algo4.chap04_graph.Chap04_01;

import algo4.chap04_graph.Chap04_01.paths.BreadFirstPathsImpl;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Iterables;
import edu.princeton.cs.algs4.In;

import java.util.*;

/**
 * @author Evan
 * @date 2020/5/12 12:01
 */
@SuppressWarnings("WeakerAccess")
public class SymbolGraph extends Graph{
    private Map<String, Integer> st;
    private Map<Integer, String> inverseST;
    private String[] keys;

    public SymbolGraph(In in, String delimiter, int V){
        super(V);
        this.st = new HashMap<>();
        while(!in.isEmpty()){
            String[] line = in.readLine().split(delimiter);
            int v = this.dynamicGetKey(line[0]);
            for(String key: line){
                int w = this.dynamicGetKey(key);
                this.addEdge(v, w);
            }
        }
        this.inverseST = HashBiMap.create(this.st).inverse();
    }

    public boolean contains(String s){
        return this.st.containsKey(s);
    }

    // Get the vertex index of specified key
    public int getVertexIndex(String s){
        if(this.contains(s)) return this.st.get(s);
        throw new IllegalArgumentException("Key " + s + " not in this graph.");
    }

    // Get the key name of specified vertex index
    public String nameOf(int v){
        this.validateVertex(v);
        return this.inverseST.get(v);
    }

    public Integer getSeparateDegree(String source, String target){
        int sourceVertex = this.getVertexIndex(source);
        int targetVertex = this.getVertexIndex(target);
        BreadFirstPathsImpl search = new BreadFirstPathsImpl(this, sourceVertex);
        if(search.hasPath(targetVertex)) return Iterables.size(search.pathTo(targetVertex));
        return null;
    }

    private int dynamicGetKey(String key){
        if(!this.st.containsKey(key)) this.st.put(key, this.st.size());
        return this.st.get(key);
    }
}
