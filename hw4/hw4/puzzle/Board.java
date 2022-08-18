package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private final int[][] numBoard;
    private final int size;
    private final int distance;
    private static final int BLANK = 0;

    public Board(int[][] tiles) {
        size = tiles.length;
        numBoard = new int[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                numBoard[i][j] = tiles[i][j];
            }
        }
        distance = manhattan();
    }

    /** Check whether (i, j) access is valid. */
    private boolean ifValid(int i, int j) {
        return i >= 0 && i < size && j >= 0 && j < size;
    }

    /** Access element at (i, j). */
    public int tileAt(int i, int j) {
        if (!ifValid(i, j)) {
            throw new IndexOutOfBoundsException("Access Denied: index out of range");
        }
        return numBoard[i][j];
    }

    public int size() {
        return size;
    }

    /***************************************************************************************
     *    Title: <Josh's solution of neighbors() in hw4>
     *    Author: <Josh Hug>
     *    Date: <2022.8.18>
     *    Availability: http://joshh.ug/neighbors.html
     *
     ***************************************************************************************/
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    /** Returns the hamming distance from current board to goal. */
    public int hamming() {
        int distance = 0;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (tileAt(i, j) != i * size + j + 1) {
                    distance += 1;
                }
            }
        }
        /** Don't caculate the blank. */
        if (tileAt(size - 1, size - 1) != BLANK) {
            distance -= 1;
        }
        return distance;
    }

    /** Returns the manhattan distance between two points. */
    private int mhtHelper(int i, int j) {
        int num = tileAt(i, j);
        if (num == 0) {
            return 0;
        }
        return Math.abs(i - (num - 1) / size) + Math.abs(j - (num - 1) % size);
    }
    /** Returns the manhattan distance from current board to goal. */
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                distance += mhtHelper(i, j);
            }
        }
        return distance;
    }

    @Override
    public int estimatedDistanceToGoal(){
        return distance;
    }

    @Override
    public boolean equals(Object y) {
        if (y == null || y.getClass() != getClass()) {
            return false;
        }
        Board other = (Board) y;
        if (other.size != size) {
            return false;
        }
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (tileAt(i, j) != other.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                result = result * 31 + tileAt(i, j);
            }
        }
        return result;
    }
}
