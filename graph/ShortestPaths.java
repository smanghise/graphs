package graph;

/* See restrictions in Graph.java. */

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

/** The shortest paths through an edge-weighted graph.
 *  By overrriding methods getWeight, setWeight, getPredecessor, and
 *  setPredecessor, the client can determine how to represent the weighting
 *  and the search results.  By overriding estimatedDistance, clients
 *  can search for paths to specific destinations using A* search.
 *  @author Sasha Manghise
 */
public abstract class ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
        _fringe = new PriorityQueue<Integer>(_G.vertexSize(),
                new CompareWeight());

    }

    /** Initialize the shortest paths.  Must be called before using
     *  getWeight, getPredecessor, and pathTo. */
    public void setPaths() {
        _fringe.add(_source);
        for (int i  = 1; i <= _G.vertexSize(); i++) {
            setWeight(i, Integer.MAX_VALUE);
        }
        setWeight(_source, 0);

        while (!_fringe.isEmpty()) {
            int v = _fringe.poll();

            if (v == _dest) {
                break;
            }

            Iteration<Integer> it = _G.successors(v);
            while (it.hasNext()) {
                int w = it.next();
                if (getWeight(v) + getWeight(v, w) < getWeight(w)) {
                    setWeight(w, getWeight(v) + getWeight(v, w));
                    _fringe.add(w);
                    setPredecessor(w, v);
                }
            }
        }
    }

    /** Returns the starting vertex. */
    public int getSource() {
        return _source;
    }

    /** Returns the target vertex, or 0 if there is none. */
    public int getDest() {
        return _dest;
    }

    /** Returns the current weight of vertex V in the graph.  If V is
     *  not in the graph, returns positive infinity. */
    public abstract double getWeight(int v);

    /** Set getWeight(V) to W. Assumes V is in the graph. */
    protected abstract void setWeight(int v, double w);

    /** Returns the current predecessor vertex of vertex V in the graph, or 0 if
     *  V is not in the graph or has no predecessor. */
    public abstract int getPredecessor(int v);

    /** Set getPredecessor(V) to U. */
    protected abstract void setPredecessor(int v, int u);

    /** Returns an estimated heuristic weight of the shortest path from vertex
     *  V to the destination vertex (if any).  This is assumed to be less
     *  than the actual weight, and is 0 by default. */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    protected abstract double getWeight(int u, int v);

    /** Returns a list of vertices starting at _source and ending
     *  at V that represents a shortest path to V. Invalid if there is a
     *  destination vertex other than V. */
    public List<Integer> pathTo(int v) {
        List<Integer> path = new ArrayList<>();
        while (v != _source) {
            path.add(0, v);
            v = getPredecessor(v);
        }
        path.add(0, _source);
        return path;
    }

    /** Returns a list of vertices starting at the source and ending at the
     *  destination vertex. Invalid if the destination is not specified. */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }

    /** The graph being searched. */
    protected final Graph _G;
    /** The starting vertex. */
    private final int _source;
    /** The target vertex. */
    private final int _dest;
    /** The fringe. */
    private PriorityQueue<Integer> _fringe;

    /** Comparator class used for priority queue. */
    private class CompareWeight implements Comparator<Integer> {
        @Override
        public int compare(Integer u1, Integer u2) {
            double w1 = getWeight(u1) + estimatedDistance(u1);
            double w2 = getWeight(u2) + estimatedDistance(u2);

            if (w1 > w2) {
                return 1;
            } else if (w1 < w2) {
                return -1;
            } else {
                return 0;
            }
        }
    }


}
