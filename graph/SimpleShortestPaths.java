package graph;

/* See restrictions in Graph.java. */

/** A partial implementation of ShortestPaths that contains the weights of
 *  the vertices and the predecessor edges.   The client needs to
 *  supply only the two-argument getWeight method.
 *  @author Sasha Manghise
 */
public abstract class SimpleShortestPaths extends ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public SimpleShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public SimpleShortestPaths(Graph G, int source, int dest) {
        super(G, source, dest);
        dist = new double[G.vertexSize()];
        pred = new int[G.vertexSize()];
        for (int i = 0; i < pred.length; i++) {
            pred[i] = 0;
            dist[i] = Double.MAX_VALUE;
        }
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    @Override
    protected abstract double getWeight(int u, int v);

    @Override
    public double getWeight(int v) {
        return dist[v - 1];
    }

    @Override
    protected void setWeight(int v, double w) {
        dist[v - 1] = w;
    }

    @Override
    public int getPredecessor(int v) {
        return pred[v - 1];
    }

    @Override
    protected void setPredecessor(int v, int u) {
        pred[v - 1] = u;
    }

    /** Predecessors of vertices. */
    private int[] pred;

    /** Shortest paths to each vertex. */
    private double[] dist;

}
