package graph;
import java.util.ArrayList;

/* See restrictions in Graph.java. */

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author Sasha Manghise
 */
abstract class GraphObj extends Graph {

    /** A new, empty Graph. */
    GraphObj() {
        vertices = new ArrayList<Integer>();
        edges = new ArrayList<int[]>();

    }

    @Override
    public int vertexSize() {
        return vertices.size();
    }

    @Override
    public int maxVertex() {
        return vertices.get(vertexSize() - 1);
    }

    @Override
    public int edgeSize() {
        return edges.size();
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        int num = 0;
        for (int i = 0; i < edgeSize(); i++) {
            if (edges.get(i)[0] == v) {
                num++;
            } else if (!isDirected()) {
                if (edges.get(i)[1] == v) {
                    num++;
                }
            }
        }
        return num;
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        return vertices.contains(u);
    }

    @Override
    public boolean contains(int u, int v) {
        if (!contains(u) || !contains(v)) {
            return false;
        }
        for (int i = 0; i < edgeSize(); i++) {
            if (edges.get(i)[0] == u && edges.get(i)[1] == v) {
                return true;
            }
            if (!isDirected()) {
                if (edges.get(i)[0] == v && edges.get(i)[1] == u) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int add() {
        int vertexId = 0;
        while (vertexId < vertexSize()
                && vertices.get(vertexId) == vertexId + 1) {
            vertexId++;
        }
        vertices.add(vertexId, vertexId + 1);
        return vertexId + 1;
    }

    @Override
    public int add(int u, int v) {
        checkMyVertex(u);
        checkMyVertex(v);

        for (int i = 0; i < edgeSize(); i++) {
            if (edges.get(i)[0] == u && edges.get(i)[1] == v) {
                return edgeId(u, v);
            }
        }
        int[] newEdge = new int[2];
        newEdge[0] = u;
        newEdge[1] = v;
        edges.add(newEdge);
        return edgeId(u, v);
    }

    @Override
    public void remove(int v) {
        checkMyVertex(v);

        for (int i = 0; i < vertexSize(); i++) {
            if (vertices.get(i) == v) {
                vertices.remove(i);
            } else {
                continue;
            }
        }

        ArrayList<int[]> newEdges = new ArrayList<>();
        for (int i = 0; i < edgeSize(); i++) {
            if (edges.get(i)[0] != v && edges.get(i)[1] != v) {
                newEdges.add(edges.get(i));
            } else {
                continue;
            }
        }
        edges = newEdges;
    }

    @Override
    public void remove(int u, int v) {
        checkMyVertex(u);
        checkMyVertex(v);

        for (int i = 0; i < edgeSize(); i++) {
            if (edges.get(i)[0] == u && edges.get(i)[1] == v) {
                edges.remove(i);
            } else if (!isDirected()) {
                if (edges.get(i)[1] == u && edges.get(i)[0] == v) {
                    edges.remove(i);
                }
            }
        }
    }

    @Override
    public Iteration<Integer> vertices() {
        return Iteration.iteration(vertices.iterator());
    }

    @Override
    public Iteration<Integer> successors(int v) {
        ArrayList<Integer> s = new ArrayList<Integer>();
        for (int i = 0; i < edgeSize(); i++) {
            if (edges.get(i)[0] == v) {
                s.add(edges.get(i)[1]);

            } else if (!isDirected()) {
                if (edges.get(i)[1] == v) {
                    s.add(edges.get(i)[0]);
                }
            }
        }
        return Iteration.iteration(s.iterator());
    }


    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        return Iteration.iteration(edges.iterator());
    }

    @Override
    protected void checkMyVertex(int v) {
        if (!contains(v)) {
            throw new IllegalArgumentException("vertex not from Graph");
        }
    }

    @Override
    protected int edgeId(int u, int v) {
        return (int) (Math.pow(2, u) * Math.pow(3, v));
    }


    /** Return vertices. */
    public ArrayList<Integer> getVertices() {
        return vertices;
    }

    /** Return edges. */
    public ArrayList<int[]> getEdges() {
        return edges;
    }

    /** An ArrayList containing all vertices in the graph. */
    private ArrayList<Integer> vertices;

    /** An ArrayList containing all edges in the graph. */
    private ArrayList<int[]> edges;

}
