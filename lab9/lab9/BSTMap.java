package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Carolt Wang
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }
        if (p == null) {
            return null;
        }
        if (p.key.compareTo(key) == 0) {
            return p.value;
        } else if (p.key.compareTo(key) < 0) {
            return getHelper(key, p.right);
        } else {
            return getHelper(key, p.left);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with a null key");
        }
        if (value == null) {
            throw new IllegalArgumentException("calls put() with a null value");
        }
        ++size;
        if (p == null) {
            return new Node(key, value);
        }
        --size; // No put actually
        if (p.key.compareTo(key) == 0) {
            ;
        } else if (p.key.compareTo(key) < 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.left = putHelper(key, value, p.left);
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /** Inorder tranversal to find all the key in the set. */
    private void keySetTranversal(Set<K> set, Node p) {
        if (p == null) {
            return;
        }
        keySetTranversal(set, p.left);
        set.add(p.key);
        keySetTranversal(set, p.right);
    }
    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<K>();
        keySetTranversal(set, root);
        return set;
    }
    private Node removeHelper(K key, Node p) {
        if (key == null) {
            throw new IllegalArgumentException("calls remove() with a null key");
        }
        if (p == null) {
            return null;
        }
        if (p.key.compareTo(key) == 0) {
            --size;
            /** i. p has no more than 1 child. */
            if (p.left  == null) return p.right;
            if (p.right == null) return p.left;
            /** ii. p has two children */
            Node rep = p.right; // rep is the node to replace as new root
            while (rep.left != null) rep = rep.left;
            p.value = rep.value;
            removeHelper(rep.key, rep);
        } else if (p.key.compareTo(key) < 0) {
            p.right = removeHelper(key, p.right);
        } else {
            p.left = removeHelper(key, p.left);
        }
        return p;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        V val = get(key);
        removeHelper(key, root);
        return val;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (get(key) == value) {
            return remove(key);
        } else {
            return null;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
