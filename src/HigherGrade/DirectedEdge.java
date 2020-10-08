package HigherGrade;

public class DirectedEdge {
    private final int v;
    private final int w;
    private final int weight;

    /**
     * Klass som håller info om alla sträckor som finns i filen
     * @param v vertex vi kommer ifrån
     * @param w vertex vi ska till
     * @param weight sträckan mellan v och w
     */
    public DirectedEdge(int v, int w, int weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    /**
     * Returnerar vertex vi kommer ifrån
     *
     */
    public int from() {
        return v;
    }

    /**
     * Returnerar vertex som vi ska till
     *
     */
    public int to() {
        return w;
    }


    /**
     * Returnerar sträckan mellan två vertex
     *
     */
    public int weight() {
        return weight;
    }

    /**
     * Returnerar sträckan mellan två kanter som en String
     *
     */
    public String toString() {
        return v + " - " + w;
    }
    /**
     * Returnerar sträckan mellan två kanter som en String
     * Fast baklänges
     */
    public String toStringReverse() {
        return w + " - " + v;
    }
}
