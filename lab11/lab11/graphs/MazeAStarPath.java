package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    /**
     *  A Comparable class Node to used in MinPQ
     * */
    private class Node implements Comparable<Node> {
        private int ver;
        private int dist;

        public Node(int v, int d) {
            ver = v;
            dist = d + manhattan();
        }

        private int manhattan() {
            return Math.abs(maze.toX(t) - maze.toX(ver)) + Math.abs(maze.toY(t) - maze.toY(ver));
        }

        public int vertex() {
            return ver;
        }

        @Override
        public int compareTo(Node o) {
            return dist - o.dist;
        }
    }

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return -1;
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        if (s == t) {
            announce();
            return;
        }
        MinPQ<Node> pq = new MinPQ<>();
        Node sN = new Node(s, distTo[s]);
        pq.insert(sN);

        while (!pq.isEmpty()) {
            Node pN = pq.delMin();
            int p = pN.vertex();
            for (int w : maze.adj(p)) {
                if (marked[w]) {
                    continue;
                }
                distTo[w] = distTo[p] + 1;
                edgeTo[w] = p;
                marked[w] = true;
                announce();
                if (w == t) {
                    return;
                } else {
                    pq.insert(new Node(w, distTo[w]));
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

