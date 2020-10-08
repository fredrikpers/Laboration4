package Uppgift3;
import Utility.Stack;

class DepthFirstDirectedPaths {
    private final boolean [] marked;    // Markerar de besökta elementen
    private final int[] edgeTo;        // Har koppling till
    private final int s;       // source vertex

    /**
     * Räknar ut väg mellan source s till alla andra vertex i grafen
     */
    public DepthFirstDirectedPaths(Digraph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s);
    }

    /**
     * Markera elemenetet som besökt
     * Gå till de element som har koppling (adjacent) till det elementet och markera dem som besökta
     * Vilket element den kollar på först spelar ingen roll.
     * Algoritmen körs tills att alla element som v har koppling till är besökta.
     *
     */
    private void dfs(Digraph G, int v) {
        marked[v] = true; //Markerar vertex v som besökt
        for (int w : G.adj(v)) { //Går igenom alla vertex i listan
            if (!marked[w]) { //Om w inte har besökts än så kör
                edgeTo[w] = v;
                dfs(G, w); //Rekursivt så besöker vi alla vertex i listan
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
