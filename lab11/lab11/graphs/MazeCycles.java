package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean ifCircle;

    public MazeCycles(Maze m) {
        super(m);
        ifCircle = false;
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        int start = 0;
        distTo[start] = 0;
        edgeTo[start] = start;
        dfs(start);
    }

    // Helper methods go here
    private void dfs(int s) {
        if (ifCircle) {
            return;
        }

        marked[s] = true;
        announce();
        for (int w : maze.adj(s)) {
            if (marked[w] && w == edgeTo[s]) {
                continue;
            }
            edgeTo[w] = s;
            if (marked[w]) {
                announce();
                ifCircle = true;
                return;
            }
            distTo[w] = distTo[s] + 1;
            dfs(w);
        }
    }
}

