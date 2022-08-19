import edu.princeton.cs.algs4.Queue;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.SplittableRandom;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        // Your code here!
        Queue<Queue<Item>> queueList = new Queue<>();
        while (!items.isEmpty()) {
            Item temp = items.dequeue();
            Queue<Item> queueNode = new Queue<>();
            queueNode.enqueue(temp);
            queueList.enqueue(queueNode);
        }
        return queueList;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        // Your code here!
        Queue<Item> mergedQueue = new Queue<>();
        int len = q1.size() + q2.size();
        for (int i = 0; i < len; ++i) {
            mergedQueue.enqueue(getMin(q1, q2));
        }
        return mergedQueue;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Your code here!
        if (items.isEmpty()) {
            return items;
        }
        Queue<Queue<Item>> queueList = makeSingleItemQueues(items);
        /** merge the top two queue<item> and then enqueue the new queue */
        while (queueList.size() > 1) {
            queueList.enqueue(mergeSortedQueues(queueList.dequeue(), queueList.dequeue()));
        }
        return queueList.dequeue();
    }

    /** The test1 is a simple test */
    @Test
    public void test1() {
        Queue<String> nameQueue = new Queue<>();
        nameQueue.enqueue("Carolt");
        nameQueue.enqueue("Bob");
        nameQueue.enqueue("David");
        nameQueue.enqueue("Anne");
        nameQueue = mergeSort(nameQueue);
        assertEquals("Anne", nameQueue.dequeue());
        assertEquals("Bob", nameQueue.dequeue());
        assertEquals("Carolt", nameQueue.dequeue());
        assertEquals("David", nameQueue.dequeue());
    }

    /** The test2 is a little more complex, but still very easy. */
    @Test
    public void test2() {
        Queue<String> nameQueue = new Queue<>();
        for (int i = 99; i >= 0; --i) {
            nameQueue.enqueue("Carolt" + String.format("%2d", i));
        }
        nameQueue = mergeSort(nameQueue);
        for (int i = 0; i < 100; ++i) {
            assertEquals("Carolt" + String.format("%2d", i), nameQueue.dequeue());
        }
    }
}
