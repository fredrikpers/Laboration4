package Uppgift1;

import Utility.Stack;

public class DepthFirstPaths {
    private final boolean [] marked;    // Markerar de besökta elementen
    private final int[] edgeTo;        // Har koppling till
    private final int s;         // source vertex

    /**
     * Räknar ut en väg från punkten s till alla andra punkter i grafen.
     * @param G grafen
     * @param s punkten s
     *
     */
    public DepthFirstPaths(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.vertices()];
        marked = new boolean[G.vertices()];
        dfs(G, s);
    }

    /**
     * Markera elemenetet som besökt
     * Gå till de element som har koppling (adjacent) till det elementet och markera dem som besökta
     * Vilket element den kollar på först spelar ingen roll.
     * Algoritmen körs tills att alla element som v har koppling till är besökta.
     *
     */
    private void dfs(Graph G, int v) {
        marked[v] = true;  //Markerar v som besökt
        for (int w : G.adj(v)) { //Går igenom alla som har koppling till v
            if (!marked[w]) { // Om w inte är besökt
                edgeTo[w] = v; // w har koppling till v
                dfs(G, w); // Gör samma sak rekursivt med w
            }
        }
    }

    /**
     * Metod som kollar på det finns en väg från x --> y.
     * returnerar true om det finns annars false.
     * @return true eller false.
     */
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    /**
     * Returnerar en väg mellan s och v.
     * @param v elementet vi ska kolla väg till
     * @return returnerar en stack som visar vägen.
     */
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v))
            return null;
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }
}
