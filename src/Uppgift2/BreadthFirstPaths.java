package Uppgift2;

import Uppgift1.Graph;
import Utility.Queue;
import Utility.Stack;
import Uppgift1.SymbolGraph;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * BFP hittar  den väg från två element som har minst antal edges.
 */
public class BreadthFirstPaths {
    private final boolean[] marked;  // Markerar de besökta elementen
    private final int[] edgeTo;      // Har koppling till
    private final int s;             //Source

    /**
     * Räknar ut den kortast vägen mellan elementet s och alla andra  element
     * i grafen
     *
     */
    public BreadthFirstPaths(Graph G, int s) {
        marked = new boolean[G.vertices()];
        edgeTo = new int[G.vertices()];
        this.s = s;
        bfs(G, s);
    }

    /**
     * Går igenom grafen och hittar kortaste vägen från s till alla andra element som den har koppling till
     * @param G grafen
     * @param s source
     */
    private void bfs(Graph G, int s) {
        Queue<Integer> q = new Queue<>();  //Skapar en kö
        marked[s] = true; //Markerar source som markerad
        q.enqueue(s); //Lägger s i kön

        while (!q.isEmpty()) { //Går igenom kön tills denna är tom
            int v = q.dequeue(); // Tar bort ett element från kön
            for (int w : G.adj(v)) // Går igenom de element som har koppling till v
            {
                if (!marked[w]) // om inte w är markerad dvs redan besökt
                {
                    edgeTo[w] = v;  // w har koppling till v
                    marked[w] = true; //Sätter w som besökt
                    q.enqueue(w); //lägger till w i kön
                }
            }
        }
    }

    /**
     * Kollar om det finns en väg mellan källan s och elementet v.
     * Returnerar true om det finns annars false
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
            path.push(x); //Lägger alla element som är påvägen till v på stacken
        path.push(s);
        return path;
    }

    /**
     * Main metoden
     *
     */
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("/Users/fredrikpettersson/IdeaProjects/Laboration4/src/Uppgift1/thedatabase.txt");

        SymbolGraph sg = new SymbolGraph(file);
        Graph graph = sg.graph();

        sg.printWholeGraph();

        System.out.println("From?");
        String startingPoint = sg.query();
        int s = sg.index(startingPoint);

        System.out.println("To?");
        String endPoint = sg.query();
        int p = sg.index(endPoint);

        BreadthFirstPaths bfs = new BreadthFirstPaths(graph, s);

            if (bfs.hasPathTo(p)) {
                for(int x : bfs.pathTo(p)){
                    if (x == s)
                        System.out.print(sg.name(x));
                    else
                        System.out.print(" ↔ " + sg.name(x));
                }
        }
            else
                System.out.println("No such route exists");
    }
}