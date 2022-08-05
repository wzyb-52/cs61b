/**
 *  My circular Array deque.
 *      When the array's capacity is n, its real length is n + 1,
 *  because where head points to doesn't store any data. However,
 *  actually there is no need to do that, since we have size to 
 *  tell us whether the array is full or empty.
 */
public class ArrayDeque<T> {
    /**
     *  Private members
     */
    private T[] data;
    private int size, capacity;
    private int head, rear;

    /**
     *  The constructor
     */
    public ArrayDeque() {
        capacity = 8;
        data = (T []) new Object[capacity + 1];
        size = 0;
        head = rear = 0;
    }

    /**
     *  The deque API:
     *      @apiNote addFirst(T item) : adds an item in the beginning.
     *  
     */   
    private void expand() {
        T[] temp = (T[]) new Object[2 * capacity + 1];
        for (int i = 1; i <= size; ++i) {
            temp[i] = data[(head + i) % (capacity + 1)];
        }
        data = temp;
        head = 0;
        rear = size;
        capacity = 2 * capacity;
    }

    private void shrink() {
        T[] temp = (T[]) new Object[capacity / 2 + 1];
        for (int i = 1; i <= size; ++i) {
            temp[i] = data[(head + i) % (capacity + 1)];
        }
        data = temp;
        head = 0;
        rear = size;
        capacity = capacity / 2;
    }

    public void addFirst(T item) {
        if (size == capacity) {
            expand();
        }
        data[head] = item;
        head = (head - 1 + capacity + 1) % (capacity + 1);
        ++size;
    }

    public void addLast(T item) {
        if (size == capacity) {
            expand();
        }
        rear = (rear + 1) % (capacity + 1);
        data[rear] = item;
        ++size;
    }

    public boolean isEmpty() {
        return size == 0;
        //  return head == rear;    // Another way;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (size == 0) {
            return;
        }
        for (int i = 1; i < size; ++i) {
            System.out.print(data[(head + i) % (capacity + 1)] + " ");
        }
        System.out.print(data[(head + size) % (capacity + 1)] + "\n");
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        head = (head + 1) % (capacity + 1);
        --size;
        T d = data[head];
        if (capacity >= 16 && capacity > 4 * size) {
            shrink();
        }
        return d;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T d = data[rear];
        rear = (rear - 1 + capacity + 1) % (capacity + 1);
        --size;
        if (capacity >= 16 && capacity >= 4 * size) {
            shrink();
        }
        return d;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        return data[(head + 1 + index) % (capacity + 1)];
    }
}
