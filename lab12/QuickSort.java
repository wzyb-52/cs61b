import edu.princeton.cs.algs4.Queue;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     *
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /** Returns a random item from the given queue. */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param less      An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are less than the given pivot.
     * @param equal     An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are equal to the given pivot.
     * @param greater   An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {
        // Your code here!
        for (Item item : unsorted) {
            int result = item.compareTo(pivot);
            if (result < 0) {
                less.enqueue(item);
            } else if (result > 0) {
                greater.enqueue(item);
            } else {
                equal.enqueue(item);
            }
        }
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {
        // Your code here!
        if (items.size() <= 1) {
            return items;
        }
        Queue<Item> less = new Queue<>(), equal = new Queue<>(), greater = new Queue<>();
        partition(items, getRandomItem(items), less, equal, greater);
        less = quickSort(less);
        greater = quickSort(greater);
        return catenate(catenate(less, equal), greater);
    }

    /** The test1 is a simple test */
    @Test
    public void test1() {
        Queue<String> nameQueue = new Queue<>();
        nameQueue.enqueue("Carolt");
        nameQueue.enqueue("Bob");
        nameQueue.enqueue("David");
        nameQueue.enqueue("Anne");
        nameQueue = quickSort(nameQueue);
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
        nameQueue = quickSort(nameQueue);
        for (int i = 0; i < 100; ++i) {
            assertEquals("Carolt" + String.format("%2d", i), nameQueue.dequeue());
        }
    }
}
