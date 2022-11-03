package graph;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertArrayEquals;

@SuppressWarnings("unchecked")
public class TraversalTest {

    private class BreadthFirstTraversalTest extends BreadthFirstTraversal {

        BreadthFirstTraversalTest(Graph G) {
            super(G);
            visitedData = new ArrayList<Integer>();
            postVisitData = new ArrayList<Integer>();
        }

        @Override
        protected boolean visit(int v) {
            visitedData.add(v);
            return true;
        }

        @Override
        protected boolean postVisit(int v) {
            postVisitData.add(v);
            return true;
        }

        protected ArrayList<Integer> getVisitedData() {
            return visitedData;
        }

        protected ArrayList<Integer> getpostVisitData() {
            return postVisitData;
        }

        private ArrayList<Integer> visitedData;
        private ArrayList<Integer> postVisitData;
    }

    private class DepthFirstTraversalTest extends DepthFirstTraversal {

        DepthFirstTraversalTest(Graph G) {
            super(G);
            visited = new ArrayList<Integer>();
            postVisited = new ArrayList<Integer>();
        }

        @Override
        protected boolean visit(int v) {
            visited.add(v);
            return true;
        }

        @Override
        protected boolean postVisit(int v) {
            postVisited.add(v);
            return true;
        }

        protected ArrayList<Integer> getVisited() {
            return visited;
        }
        protected ArrayList<Integer> getpostVisited() {
            return postVisited;
        }

        private ArrayList<Integer> visited;
        private ArrayList<Integer> postVisited;
    }

    @Test
    public void testTraversalfirst() {
        Graph g = new DirectedGraph();

        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();

        g.add(1, 2);
        g.add(1, 5);
        g.add(1, 6);
        g.add(2, 4);
        g.add(4, 3);
        g.add(4, 5);
        g.add(6, 5);

        BreadthFirstTraversalTest bfs = new BreadthFirstTraversalTest(g);
        DepthFirstTraversalTest dfs = new DepthFirstTraversalTest(g);

        bfs.traverse(1);
        dfs.traverse(1);

        System.out.println("bfs: " + bfs.getVisitedData());
        System.out.println("dfs post-order:  " + dfs.getpostVisited());
        System.out.println("dfs pre-order: " + dfs.getVisited());



    }

    private class ShortestPathTest  extends SimpleShortestPaths {
        LabeledGraph<Integer, Integer> _G;

        ShortestPathTest(LabeledGraph G, int source, int dest) {
            super(G, source, dest);
            _G = G;
        }

        public void setWeight(int u, int v, int weight) {
            _G.setLabel(u, v, weight);
        }

        @Override
        public double getWeight(int u, int v) {
            double weight = _G.getLabel(u, v);
            return weight;
        }
    }

    @Test
        public void testSimplePath() {
        DirectedGraph dg = new DirectedGraph();
        dg.add();
        dg.add();
        dg.add();
        dg.add();

        dg.add(1, 2);
        dg.add(1, 3);
        dg.add(3, 4);
        dg.add(4, 2);

        LabeledGraph<Integer, Integer> ldg =
                    new LabeledGraph<Integer, Integer>(dg);
        ShortestPathTest test = new ShortestPathTest(ldg, 1, 2);

        test.setWeight(1, 2, 35);
        test.setWeight(1, 3, 5);
        test.setWeight(3, 4, 6);
        test.setWeight(4, 2, 3);

        test.setPaths();

        List<Integer> path = test.pathTo();

        System.out.println("Shortest path: " + path);
        assertArrayEquals(path.toArray(), new Integer[]{1, 3, 4, 2});
    }

    @Test
    public void testCyclePath() {
    }

    @Test
    public void testComplicatedPath() {
        DirectedGraph dg = new DirectedGraph();
        dg.add();
        dg.add();
        dg.add();
        dg.add();
        dg.add();
        dg.add();
        dg.add();

        dg.add(1, 2);
        dg.add(1, 7);
        dg.add(2, 3);
        dg.add(2, 6);
        dg.add(3, 4);
        dg.add(3, 6);
        dg.add(5, 4);
        dg.add(6, 5);
        dg.add(7, 2);
        dg.add(7, 6);

        LabeledGraph<Integer, Integer> ldg =
                new LabeledGraph<Integer, Integer>(dg);
        ShortestPathTest test = new ShortestPathTest(ldg, 1, 4);

        test.setWeight(1, 2, 5);
        test.setWeight(1, 7, 4);
        test.setWeight(2, 3, 5);
        test.setWeight(2, 6, 4);
        test.setWeight(3, 4, 5);
        test.setWeight(3, 6, 7);
        test.setWeight(5, 4, 1);
        test.setWeight(6, 5, 2);
        test.setWeight(7, 2, 2);
        test.setWeight(7, 6, 10);

        test.setPaths();

        List<Integer> path = test.pathTo();
        System.out.println(path);
        assertArrayEquals(path.toArray(), new Integer[]{1, 2, 6, 5, 4});
    }

}
