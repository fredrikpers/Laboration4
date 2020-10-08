package Uppgift3;

import Utility.ST;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SymbolDigraph {
    private ST<String, Integer> st;  // string -> index
    private String[] keys;           // index  -> string
    private Digraph graph;           // the underlying digraph

    /**
     * Initializes a digraph from a file using the specified delimiter.
     * Each line in the file contains
     * the name of a vertex, followed by a list of the names
     * of the vertices adjacent to that vertex, separated by the delimiter.
     *
     */
    public SymbolDigraph() throws FileNotFoundException {
        File file = new File("/Users/fredrikpettersson/IdeaProjects/Laboration4/src/Uppgift1/thedatabase.txt");
        Scanner scan = new Scanner(file);
        st = new ST<>(500000);

        //Fyller upp ST med de strings som finns i filen
        while (scan.hasNextLine()) {
            String[] a = scan.nextLine().split(" ");
            for (int i = 0; i < a.length; i++) {
                if(!st.contains(a[i]))  // Om stringen inte redan finns med
                    st.put(a[i], st.size()); // Den första stringen hamnar på 0, den andra på 1 osv....
            }
        }

        //Inverterar ST så att vi får strings som nyckel
        keys = new String[st.size()];
        for (String name : st.keys()) {
            keys[st.get(name)] = name; // En array med alla nycklar
        }


        graph = new Digraph(st.size()); // Skapar en graf med samma storlek som ST
        Scanner scanner = new Scanner(file);// Skapar ny scanner

        while (scanner.hasNextLine()) {
            String[] a = scanner.nextLine().split(" ");
            int v = st.get(a[0]); //Kollar det första elementet på varje rad till de andra
            for (int i = 1; i < a.length; i++) {
                int w = st.get(a[i]);
                graph.addEdge(v, w); //Lägger till vilken koppling elementet har till
            }
        }
    }
    /**
     * Kollar om en given string finns med i grafen
     *
     */
    public boolean contains(String s) {
        return st.contains(s);
    }

    /**
     * Returnerar ett index från till den givna stringen
     */
    public int indexOf(String s) {
        return st.get(s);
    }

    /**
     * Returnerar en strings namn från ett givet index
     */
    public String nameOf(int v) {
        return keys[v];
    }

    /**
     * Returnerar grafen
     * @return grafen
     */
    public Digraph digraph() {
        return graph;
    }
    public String query(){
        Scanner scan = new Scanner(System.in);
        String s = scan.next();
        if(!contains(s)) {
            System.err.println("Wrong input");
            return query();
        }
        return s;
    }

    /**
     * Skriver ut hela grafen
     */
    public void printWholeGraph(){
        StringBuilder sb = new StringBuilder();
        System.out.print(String.format("%70s", "The whole graph!"));

        for(int i = 0; i < graph.V()-1; i++){
            if(i%4 == 0)
                System.out.print("\n");
            sb.append(keys[i]);
            sb.append(" ---> ");
            for(int j : graph.adj()[i]){
                sb.append(keys[j]);
                sb.append(" ");
            }
            System.out.print(String.format("%-35s",sb));
            sb = new StringBuilder();
        }
        System.out.println();

    }

    /**
     * Main metod för testning av uppgift 3
     *
     */
    public static void main(String[] args) throws FileNotFoundException {
        SymbolDigraph sdg = new SymbolDigraph();
        Digraph digraph = sdg.digraph();
        sdg.printWholeGraph();

        System.out.println("\nFrom?");
        String startingPoint = sdg.query();
        int s = sdg.indexOf(startingPoint);

        System.out.println("To?");
        String endPoint = sdg.query();
        int p = sdg.indexOf(endPoint);

        DepthFirstDirectedPaths dfdp = new DepthFirstDirectedPaths(digraph, s);

        System.out.println("Does the route "+ startingPoint+ " to " +endPoint+ " exists");

        if (dfdp.hasPathTo(p)) {
            System.out.println("Such route exists");
            for(int x : dfdp.pathTo(p)){
            if (x == s)
                System.out.print(sdg.nameOf(x));
            else
                System.out.print(" -> " + sdg.nameOf(x));
        }
        System.out.println(" ");


        } else
            System.out.println("No such route exists");

    }
}
