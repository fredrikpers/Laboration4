package Uppgift3;

import Utility.Bag;

public class Digraph {

    private final int vertices; // Antalet hörn
    private int edges;                 // number of edges in this digraph
    private final Bag<Integer>[] adj; // Bag som håller de element som har koppling till ett visst element

    /**
     * Skapar en tom digraph med antalet vertices v och edges 0
     *
     */
    public Digraph(int V) {
        this.vertices = V;
        this.edges = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    /**
     * Returnerar antalet vertices i grafen
     * @return antalet vertices
     */
    public int V() {
        return vertices;
    }

    /**
     * Returnerar antalet edges i grafen
     * @return antalet edges
     */
    public int E() {
        return edges;
    }

    /**
     * Lägger till den direkta kopplingen v till W i digraph
     *
     */
    public void addEdge(int v, int w) {

        adj[v].add(w); //lägger till w till v's lista av kopplingar.
        edges++; // Antalet edges
    }
    public Bag<Integer> [] adj(){
        return adj;

    }

    /**
     * Returnerar de element som har koppling till elementet v
     *
     * @param  v elementet
     * @return de hörn som är kopplade till v som en iterable
     */
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

}