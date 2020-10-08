package Uppgift1;

import Utility.ST;
import java.lang.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SymbolGraph {
    private final ST<String, Integer> st;  // string -> index
    private final String[] keys;           // index  -> string
    private final Graph graph;             // the underlying graph

    /**
     * Initializes a graph from a file using the specified delimiter.
     * Each line in the file contains
     * the name of a vertex, followed by a list of the names
     * of the vertices adjacent to that vertex, separated by the delimiter.
     */
    public SymbolGraph(File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file); //Skapar en scanner för att läsa från filen
        st = new ST<>(500000); //Skapar en symbol tabel

        //Fyller upp ST med de strings som finns i filen
        while (scan.hasNextLine()) {
            String[] a = scan.nextLine().split(" "); //String
            for (int i = 0; i < a.length; i++) {
                if(!st.contains(a[i]))  // Om stringen inte redan finns med
                st.put(a[i], st.size()); // Den första stringen hamnar på 0, den andra på 1 osv....
            }
        }
        //Inverterar ST så att vi får strings som nyckel
        keys = new String[st.size()];
        for(String name: st.keys())
            keys[st.get(name)] = name; // En array med alla nycklar

        graph = new Graph(st.size()); // Skapar en graf med samma storlek som ST
        Scanner scanner = new Scanner(file); // Skapar ny scanner

        //Bygger grafen och kollar alla element som har kopplingar till varandra
         while(scanner.hasNextLine()){
            String[] a = scanner.nextLine().split(" ");
            int v = st.get(a[0]); //Kollar det första elementet på varje rad till de andra
            for(int i = 1; i < a.length; i++)
                graph.addEdge(v, st.get(a[i])); //Lägger till vilken koppling elementet har till
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
     * Returnerar index av en given string
     *
     */
    public int index(String s) {
        return st.get(s);
    }

    /**
     * Returnerar namnet på ett element
     *
     */
    public String name(int v) {
        return keys[v];
    }

    /**
     * returnerar grafen
     *
     */
    public Graph graph() {
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

            for(int i = 0; i < graph.vertices()-1; i++){
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
     * Main metod
     *
     */
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("/Users/fredrikpettersson/IdeaProjects/Laboration4/src/Uppgift1/thedatabase.txt");
        SymbolGraph sg = new SymbolGraph(file);
        Graph graph = sg.graph();
        sg.printWholeGraph();

        System.out.println("\nFrom?");
        String startingPoint = sg.query();
        int s = sg.index(startingPoint);

        System.out.println("To?");
        String endPoint = sg.query();
        int p = sg.index(endPoint);

        DepthFirstPaths dfp = new DepthFirstPaths(graph,s);

            if (dfp.hasPathTo(p)){
                System.out.println("Route");
                for(int x : dfp.pathTo(p)){
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
