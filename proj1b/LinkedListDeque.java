/**
 *  My two-sentinel Linked list deque.
 */
public class LinkedListDeque<T> implements Deque<T> {
    /**
     *  The node class
     */
    private class Node {
        T data;
        Node prev;
        Node next;
        public Node(T d, Node p, Node n) {
            data = d;
            prev = p;
            next = n;
        }
    }

    /**
     *  Other members
     */
    private Node head;
    private Node rear;
    private int size;

    /**
     *  The constructor
     */
    public LinkedListDeque() {
        head = new Node(null, null, null);
        rear = new Node(null, head, head);
        head.prev = head.next = rear;
        size = 0;
    }

    /**
     *  The deque API:
     *      @apiNote addFirst(T item) : adds an item in the beginning.
     *  
     */
    @Override
    public void addFirst(T item) {
        Node newNode = new Node(item, head, head.next);
        newNode.next.prev = newNode.prev.next = newNode;
        ++size;
    }

    @Override
    public void addLast(T item) {
        Node newNode = new Node(item, rear.prev, rear);
        newNode.next.prev = newNode.prev.next = newNode;
        ++size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node temp = head.next;
        for (int i = 0; i + 1 < size; ++i) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        if (temp != rear) {
            System.out.print(temp.data + "\n");
        }
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node temp = head.next;
        T d = temp.data;
        head.next = temp.next;
        temp.next.prev = temp.prev;
        temp = null;    // releases the memory of first item
        --size;
        return d;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node temp = rear.prev;
        T d = temp.data;
        temp.prev.next = temp.next;
        rear.prev = temp.prev;
        temp = null;    // releases the memory of last item
        --size;
        return d;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        Node temp = head.next;
        for (int i = 0; i < index; ++i) {
            temp = temp.next;
        }
        return temp.data;
    }

    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        }
        return getRecursive(head.next, index);
    }

    private T getRecursive(Node first, int index) {
        if (index == 0) {
            return first.data;
        }
        return getRecursive(first.next, index - 1);
    }
}
