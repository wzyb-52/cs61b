package synthesizer;
import java.util.Iterator;

public interface BoundedQueue<T> extends Iterable<T> {
    /** returns size of the buffer */
    public int capacity();

    /** return number of items currently in the buffer */
    public int fillCount();

    /** adds item x to the end */
    public void enqueue(T x);

    /** deletes and return item from the front */
    public T dequeue();

    /** returns (but do not delete) item from the front */
    public T peek();

    /** Returns a iterator */
    public Iterator<T> iterator();

    default public boolean isEmpty() {
        return fillCount() == 0;
    }

    default public boolean isFull() {
        return fillCount() == capacity();
    }
}