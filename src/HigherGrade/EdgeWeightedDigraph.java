package HigherGrade;

import Utility.Bag;
import edu.princeton.cs.algs4.StdIn;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Läser in filen och skapar grafen.
 * Innehåller även main metoden som testar att programmet fungerar
 *
 */
public class EdgeWeightedDigraph {
    private final int V;                         // number of vertices in this digraph
    private int E;                               // number of edges in this digraph
    private final Bag<DirectedEdge>[] adj;       // adj[v] = adjacency list for vertex v

    /**
     * Läser in filen med alla vertices och edges.
     * Lägger sedan in dem i grafen.
     *
     */
    public EdgeWeightedDigraph(File file) throws FileNotFoundException {

        System.setIn(new FileInputStream(file)); //Filen vi ska läsa ifrån

            this.V = StdIn.readInt(); //Läser in första elementet som är antalet vertices
            adj = (Bag<DirectedEdge>[]) new Bag[V]; //Skapar en bag för adjacent
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<>(); // Skapar en bag för alla vertices i grafen
            }

            this.E = StdIn.readInt(); //Läsen in andra elementet som är antalet edges i grafen
            while(!StdIn.isEmpty()){ //Kör igenom hela filen
                int v = StdIn.readInt(); //Läser in "från" elementet
                int w = StdIn.readInt(); //Läser in "till" elementet
                int weight = StdIn.readInt(); //Läser in sträckan mellan v och w
                addEdge(new DirectedEdge(v, w, weight)); //Lägger till en edge mellan dessa
            }
        }

    /**
     * Returnerar antalet vertices.
     * @return antalet vertices
     */
    public int V() {
        return V;
    }

    /**
     * Skapar en edge mellan v och w
     */
    public void addEdge(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        adj[v].add(e);
    }

    /**
     * Returnerar de vertices som har koppling till v
     * Detta görs som en iterable
     */
    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }
    public void printTimeComplexity(){
        System.out.println("\nTidskomplexitet" +
                "\n" +
                "På grund av att vi använder oss av binary heap så kommer worst case att bli O(E logV).\n" +
                "Där E är antalet edges och V är antalet Vertices. Binära träd har O(N logn) tidskomplexitet detta vet vi från tidigare laboration. \n");

    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("/Users/fredrikpettersson/IdeaProjects/Laboration4/src/HigherGrade/NYC.txt");

        Scanner scan = new Scanner(System.in);
        System.out.println("From?");
        int startingPoint = scan.nextInt();
        System.out.println("Passing...");
        int passing = scan.nextInt();
        System.out.println("To?");
        int endPoint = scan.nextInt();

        EdgeWeightedDigraph G = new EdgeWeightedDigraph(file);
        DijkstraSP sp = new DijkstraSP(G,passing);
        int distance = 0;

        if (sp.hasPathTo(startingPoint) && sp.hasPathTo(endPoint)) {

            System.out.print("Route: \n");
            for(DirectedEdge x: sp.pathToReverse(startingPoint))
                System.out.println(x.toStringReverse());

            for(DirectedEdge x : sp.pathTo(endPoint))
                System.out.println(x.toString());

            distance += sp.distTo(endPoint);
            distance += sp.distTo(startingPoint);
            System.out.println("\nTotal distance: "+distance);
        }
        else
            System.err.println("No such route exists");
        G.printTimeComplexity();
    }

}