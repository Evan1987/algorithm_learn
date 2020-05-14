package Chap04_Graph.Chap04_01;

import Chap04_Graph.Chap04_01.paths.BreadFirstPathsImpl;
import Chap04_Graph.Graph;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Iterables;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Evan
 * @date 2020/5/12 12:01
 */
@SuppressWarnings("WeakerAccess")
public class SymbolGraph {
    private Map<String, Integer> st;
    private Map<Integer, String> inverseST;
    private String[] keys;
    private Graph graph;

    public SymbolGraph(In in, String delimiter){
        this.st = new HashMap<>();
        this.graph = new Graph();
        while(!in.isEmpty()){
            String[] line = in.readLine().split(delimiter);
            int v = this.dynamicGetKey(line[0]);
            for(String key: line){
                int w = this.dynamicGetKey(key);
                this.graph.addEdge(v, w);
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

    public Graph G(){
        return this.graph;
    }

    // Get the key name of specified vertex index
    public String nameOf(int v){
        this.validateVertex(v);
        return this.inverseST.get(v);
    }

    public Integer getSeparateDegree(String source, String target){
        int sourceVertex = this.getVertexIndex(source);
        int targetVertex = this.getVertexIndex(target);
        BreadFirstPathsImpl search = new BreadFirstPathsImpl(this.graph, sourceVertex);
        if(search.hasPath(targetVertex)) return Iterables.size(search.pathTo(targetVertex));
        return null;
    }

    private void validateVertex(int v){
        int V = this.graph.V();
        if(v < 0 || v >= V) throw new IllegalArgumentException("vertex " + v + " should be between 0 and " + (V - 1));
    }


    private int dynamicGetKey(String key){
        if(!this.st.containsKey(key)) this.st.put(key, this.st.size());
        return this.st.get(key);
    }
}
