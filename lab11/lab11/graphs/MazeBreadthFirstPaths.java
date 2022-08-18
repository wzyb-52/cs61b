package lab11.graphs;

import edu.princeton.cs.algs4.In;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(s);
        while (!queue.isEmpty()) {
            int ver = queue.poll();
            marked[ver] = true;
            announce();
            if (ver == t) {
                return;
            }
            for (int w : maze.adj(ver)) {
                if (!marked[w]) {
                    distTo[w] = distTo[ver] + 1;
                    edgeTo[w] = ver;
                    queue.offer(w);
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

