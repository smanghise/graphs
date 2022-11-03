package graph;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author Sasha Manghise
 */

@SuppressWarnings("unchecked")
public class GraphTest {

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    /** Creates a circular undirected graph with 10 vertices. */
    public Graph createUndirected() {
        Graph g = new UndirectedGraph();
        for (int nodes = 1; nodes < 10; nodes++) {
            g.add();
        }
        for (int nodes = 1; nodes < 10; nodes++) {
            if (nodes == 9) {
                g.add(nodes, 1);
            } else {
                g.add(nodes, nodes + 1);
            }
        }
        return g;
    }

    /** Creates a circular directed graph with 10 vertices. */
    public Graph createDirected() {
        Graph g = new DirectedGraph();
        for (int nodes = 1; nodes < 10; nodes++) {
            g.add();
        }
        for (int nodes = 1; nodes < 10; nodes++) {
            if (nodes == 9) {
                g.add(nodes, 1);
            } else {
                g.add(nodes, nodes + 1);
            }
        }
        return g;
    }

    @Test
    public void checkUndirectedSuccessors() {
        Graph g = createUndirected();
        g.add(9, 5);
        g.add(7, 3);
        List<Integer> v = new ArrayList();

        Iteration<Integer> s = g.successors(3);
        while (s.hasNext()) {
            v.add(Integer.valueOf(s.next()));
        }

        assertEquals(Integer.valueOf(2), v.get(0));
        assertEquals(Integer.valueOf(4), v.get(1));
        assertEquals(Integer.valueOf(7), v.get(2));
    }

    @Test
    public void checkDirectedSuccessors() {
        Graph g = createDirected();
        List v = new ArrayList();

        Iteration<Integer> s = g.successors(2);
        while (s.hasNext()) {
            v.add(s.next());
        }

        assertEquals(v.get(0), 3);
    }

    @Test
    public void checkDirectedPredecessors() {
        Graph g = createDirected();
        List v = new ArrayList();

        Iteration<Integer> s = g.predecessors(2);
        while (s.hasNext()) {
            v.add(s.next());
        }
        assertEquals(v.get(0), 1);
    }

    @Test
    public void checkDirectedRemove() {
        Graph g = createDirected();
        g.add(9, 5);
        g.add(7, 3);
        g.remove(3);
        g.remove(5);

        List v = new ArrayList();
        Iteration<Integer> p = g.predecessors(2);
        while (p.hasNext()) {
            v.add(p.next());
        }
        assertEquals(v.get(0), 1);

        v.clear();
        Iteration<Integer> s = g.successors(2);
        while (s.hasNext()) {
            v.add(s.next());
        }
        assertEquals(v.size(), 0);

    }

    @Test
    public void checkUndirectedRemove() {
        Graph g = createUndirected();
        g.remove(3);
        g.remove(5);

        List v = new ArrayList();
        Iteration<Integer> p = g.predecessors(4);
        while (p.hasNext()) {
            v.add(p.next());
        }
        assertEquals(v.size(), 0);
    }

    @Test
    public void selfEdge() {
        Graph g = createUndirected();
        g.add(1, 1);

        List v = new ArrayList();
        Iteration<Integer> s = g.successors(1);
        while (s.hasNext()) {
            v.add(s.next());
        }

        assertEquals(g.outDegree(1), 3);
        assertEquals(v.get(0), 2);
        assertEquals(v.get(1), 9);
        assertEquals(v.get(2), 1);

    }

    @Test
    public void checkLabeled() {
        Graph g = createUndirected();
        LabeledGraph<String, String> labels =
                new LabeledGraph<String, String>(g);

        for (int nodes = 1; nodes < 10; nodes++) {
            labels.setLabel(nodes, "Node" + nodes);
        }

        for (int nodes = 1; nodes < 10; nodes++) {
            System.out.println("Node : " + nodes
                    + "  Label : " + labels.getLabel(nodes));
        }

        labels.setLabel(1, 2, "Edge 1, 2");
        System.out.println(labels.getLabel(1, 2));
    }

    @Test
    public void checkMaxVertex() {
        Graph g = new DirectedGraph();
        for (int i = 0; i < 4; i++) {
            g.add();
        }
        g.remove(1);
        g.remove(4);
        g.add();

        assertEquals(3, g.maxVertex());

        List<Integer> expected = Arrays.asList(1, 2, 3);
        Boolean correctVertices = true;
        for (Integer n : expected) {
            if (!((DirectedGraph) g).getVertices().contains(n)) {
                correctVertices  = false;
            }
        }
        assertTrue(correctVertices);
    }

    @Test
    public void addDelete() {
        Graph g = new DirectedGraph();
        for (int i = 0; i < 10; i++) {
            g.add();
        }

        g.remove(2);
        g.remove(10);
        g.add();

        assertEquals(9, g.maxVertex());

        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Boolean correctVertices = true;
        for (Integer n : expected) {
            if (!((DirectedGraph) g).getVertices().contains(n)) {
                correctVertices  = false;
            }
        }
        assertTrue(correctVertices);

    }

}
