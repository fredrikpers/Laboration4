package HigherGrade;

import Utility.Queue;
import Utility.Stack;

/**
 * Räknar ut den kortaste sträckan mellan source elementet och alla andra element i grafen
 *
 */
public class DijkstraSP {
    private final double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private final DirectedEdge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
    private final IndexMinPQ<Double> pq;    // priority queue of vertices

    /**
     * Räknar ut den kortaste sträckan mellan source s och alla andra vertex i grafen
     *
     */
    public DijkstraSP(EdgeWeightedDigraph G, int s) {

        distTo = new double[G.V()]; //Skapar distTo och sätter dess längd till antal vertices
        edgeTo = new DirectedEdge[G.V()]; //Skapar edgeTo och sätter dess längd till antal vertices
        pq = new IndexMinPQ<>(G.V()); // Skapar priority kön

        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY; //Sätter sträckan till alla vertex till infinity
        distTo[s] = 0.0; // Stäckan till source sätter vi till 0

        pq.insert(s, distTo[s]); // Vi sätter source på kön
        while (!pq.isEmpty()) {
            int v = pq.delMin(); // Tar bort den edge som är närmast source från kön
            for (DirectedEdge e : G.adj(v)) // Alla element som är kopplade (adjacent) till source
                relax(e); // Relaxa dem
        }
    }

    private void relax(DirectedEdge e) {
        int v = e.from(); //element från
        int w = e.to(); // element till

        if (distTo[w] > distTo[v] + e.weight()) { //Om den gamla sträckan till w är större än den nya sträckan till v + weight(sträckan)

            distTo[w] = distTo[v] + e.weight(); //Uppdaterar dist till w
            edgeTo[w] = e; //uppdaterar edgeto till det nya värdet
            if (pq.contains(w)) // Om kön innehåller w så har vi en ny kortare väg till w
                pq.decreaseKey(w, distTo[w]); //Minskar nyckeln till w
            else
                pq.insert(w, distTo[w]); //Om den inte finns i kön så sätter vi in den i kön
        }
    }

    /**
     * Returnerar längden mellan source s och vertex v
     *
     */
    public double distTo(int v) {
        return distTo[v];
    }


    /**
     * Returnerar true om det finns en väg mellan source s och v
     * Annars returneras false
     */
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returnerar kortaste sträckan mellan source och v.
     * Detta genom en stack vilken är FIFO
     */
    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v))
            return null;
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    /**
     * Returnerar kortaste sträckan mellan source och v.
     * Detta genom en queue vilken är LIFO
     */
    public Iterable<DirectedEdge> pathToReverse(int v) {
        if (!hasPathTo(v))
            return null;

        Queue<DirectedEdge> path = new Queue<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.enqueue(e);
        }
        return path;
    }
}