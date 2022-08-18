package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {
    /**
     *  SearchNode is the class of "move sequences".
     * */
    private class SearchNode implements Comparable {
        private final WorldState currState;
        private final int moveNum;
        private final SearchNode prevNode;
        private SearchNode(SearchNode prev, WorldState state) {
            currState = state;
            moveNum = prev == null ? 0 : prev.moveNum + 1;
            prevNode = prev;
        }

        @Override
        public int compareTo(Object o) {
            if (o == null) {
                throw new RuntimeException("Can't compare a null!");
            }
            if (o.getClass() != this.getClass()) {
                throw new RuntimeException("Can't compare a SearchNode to a different class");
            }
            SearchNode other = (SearchNode) o;
            return (this.moveNum + this.currState.estimatedDistanceToGoal())
                    - (other.moveNum + other.currState.estimatedDistanceToGoal());
        }

        /**
         * Compares the World state to all nodes of the moving sequence,
         * to figure out whether the world state have been searched.
         * You may not need to use this.
         * */
        private boolean ifSearched(WorldState s) {
            SearchNode temp = this;
            while (temp != null) {
                if (s.equals(temp.currState)) {
                    return true;
                }
                temp = temp.prevNode;
            }
            return false;
        }
    }

    /** answer is the best move sequences */
    private SearchNode answer;

    /**
     *  Constructor which solves the puzzle, computing
     *  everything necessary for moves() and solution() to
     *  not have to solve the problem again. Solves the
     *  puzzle using the A* algorithm. Assumes a solution exists.
     * */
    public Solver(WorldState initial) {
        SearchNode start = new SearchNode(null, initial);
        MinPQ<SearchNode> bfsQueue = new MinPQ<>();
        bfsQueue.insert(start);

        while (!bfsQueue.isEmpty()) {
            SearchNode parentN = bfsQueue.delMin();
            if (parentN.currState.isGoal()) {
                answer = parentN;
                return;
            }
            for (WorldState childS : parentN.currState.neighbors()) {
                /** Use equals() instead of == ! */
                if (parentN.prevNode != null && childS.equals(parentN.prevNode.currState)) {
                    continue;   // a critical optimization
                }
                SearchNode childN = new SearchNode(parentN, childS);
                bfsQueue.insert(childN);
            }
        }
    }

    /**
     *  Returns the minimum number of moves to solve the puzzle
     *  starting at the initial WorldState.
     * */
    public int moves() {
        if (answer == null) {
            throw new RuntimeException("No solution to this problem!");
        }
        return answer.moveNum;
    }

    /**
     *  Returns a sequence of WorldStates from the initial WorldState
     *  to the solution.
     * */
    public Iterable<WorldState> solution() {
        if (answer == null) {
            throw new RuntimeException("No solution to this problem!");
        }
        ArrayList<WorldState> solutionSeq = new ArrayList<>();
        SearchNode item = answer;
        while (item != null) {
            solutionSeq.add(item.currState);
            item = item.prevNode;
        }
        Collections.reverse(solutionSeq);
        return solutionSeq;
    }
}
