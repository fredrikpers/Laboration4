package Uppgift1;

import Utility.Bag;

public class Graph {
    private final int vertices; // Antalet hörn
    private final Bag<Integer>[] adj; // Bag som håller de element som har koppling till ett visst element

    /**
     * Skapar en tom graf med hörn och 0 kanter
     * Parametern vertices är antalet hörn
     * @param  vertices antalet hörn
     *
     */
    public Graph(int vertices) {
        this.vertices = vertices;
        adj = (Bag<Integer>[]) new Bag[vertices];
        for (int v = 0; v < vertices; v++)
            adj[v] = new Bag<>();
    }

    /**
     * Returnerar antalet hörn i grafen.
     * @return antalet hörn i grafen.
     *
     */
    public int vertices() {
        return vertices;
    }

    /**
     * Skapar en oriktad koppling mellan v och W i grafen.
     * @param  v ett element i kanten
     * @param  w det andra elementet i kanten
     *
     */
    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
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