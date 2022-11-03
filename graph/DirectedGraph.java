package graph;

/* See restrictions in Graph.java. */

import java.util.ArrayList;

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author Sasha Manghise
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public int inDegree(int v) {
        int num = 0;
        for (int i = 0; i < edgeSize(); i++) {
            if (getEdges().get(i)[1] == v) {
                num++;
            }
        }
        return num;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        ArrayList<Integer> s = new ArrayList<Integer>();
        for (int i = 0; i < edgeSize(); i++) {
            if (getEdges().get(i)[1] == v) {
                s.add(getEdges().get(i)[0]);

            } else if (!isDirected()) {
                if (getEdges().get(i)[0] == v) {
                    s.add(getEdges().get(i)[1]);
                }
            }
        }
        return Iteration.iteration(s.iterator());
    }

}
