/*
 * BinaryTree.java
 *
 * Created on 4 novembre 2004, 21:09
 */
package tree;

/**
 * A standard binary tree
 *
 * @author Wurtz Jean-Marie
 */
public abstract class Tree extends TreeHandleEvents {

    static boolean less(KEY v, KEY u) {
        return v.less(u);
    }

    static boolean equals(KEY v, KEY u) {
        return v.equals(u);
    }

    static int Nb(Node h) {
        return (h == null) ? 0 : h.N;
    }
    /**
     * head of the tree
     */
    protected Node head;
    protected Node father;

    /**
     * Creates a new instance of BinaryTree
     */
    Tree() {
        head = null;
    }

    /**
     * return the root of the binary tree
     *
     * @return returns the root
     */
    public Node root() {
        return head;
    }

    /**
     * recursive insertion of an item
     *
     * @param h current insertion node
     * @param x item to be inserted
     * @return return the returns h so that we get the right value in case it
     * has changed
     */
    protected Node insertR(Node h, ITEM x) {
        if (h == null) {
            Node n = new Node(x);
            insertion(n);
            return n;
        }
        progression(h);
        if (less(x.key(), h.item.key())) {
            h.l = insertR(h.l, x);
        } else {
            h.r = insertR(h.r, x);
        }
        h.N = Nb(h.l) + Nb(h.r) + 1;
        return h;
    }

    /**
     * public method to be called for an insertion
     *
     * @param x item to be inserted
     */
    public void insert(ITEM x) {
        start(head);
        head = insertR(head, x);
    }

    /**
     * protected recursive search
     *
     * @param h node at which the search is performed
     * @param v key of the item to be searched
     * @return return the node containing the KEY v
     */
    protected ITEM searchR(Node h, KEY v) {
        if (h == null) {
            return null;
        }
        progression(h);
        if (equals(v, h.item.key())) {
            return h.item;
        }
        if (less(v, h.item.key())) {
            return searchR(h.l, v);
        } else {
            return searchR(h.r, v);
        }
    }

    /**
     * public method to do the search
     *
     * @param key node to searched contains KEY key
     * @return return the node with KEY key of null if not found
     */
    public ITEM search(KEY key) {
        start(head);
        return searchR(head, key);
    }

    /**
     * recursive count of the number of nodes in the tree
     *
     * @param h node at which to do the count
     * @return return the number of of nodes in the subtree
     */
    protected int countR(Node h) {
        if (h == null) {
            return 0;
        }
        progression(h);
        return 1 + countR(h.l) + countR(h.r);
    }

    /**
     * public methods giving the number of nodes in the tree
     *
     * @return return the number of nodes in a tree
     */
    public int count() {
        start(head);
        return countR(head);
    }
    static String Blank = "     ";

    /**
     * a private methode which prints a subtree
     *
     * @param h root of the subtree
     * @param i level of the root<B>h</B> of the subtree
     * @return a string with the tree
     */
    protected String toStringR(Node h, int i) {
        if (h == null) {
            return "";
        }
        String s = toStringR(h.r, i + 1);
        for (int j = 0; j < i; j++) {
            s += Blank;
        }
        //s += h.item.toString() + "\n";
        s += h.toString() + "\n";
        s += toStringR(h.l, i + 1);
        return s;
    }

    /**
     * public method to print a tree
     *
     * @return return a string with the whole tree
     */
    public String toString() {
        return toStringR(head, 0);
    }

    /**
     * right rotation operation on a node
     *
     * @param h node at which to do the rotation
     * @return return the new local root
     */
    protected Node rotR(Node h) {
        if (h.l == null) {
            return h;// leave things as they are
        }
        rotateR(h);
        Node x = h.l;
        h.l = x.r;
        x.r = h;
        x.r.N = Nb(x.r.l) + Nb(x.r.r) + 1;
        x.N = Nb(x.l) + Nb(x.r) + 1;
        return x;
    }

    /**
     * right rotation operation on a node
     *
     * @param h node at which to do the rotation
     * @return return the new local root
     */
    protected Node rotL(Node h) {
        if (h.r == null) {
            return h; // leave things as they are
        }
        rotateL(h);
        Node x = h.r;
        h.r = x.l;
        x.l = h;
        x.l.N = Nb(x.l.l) + Nb(x.l.r) + 1;
        x.N = Nb(x.l) + Nb(x.r) + 1;
        return x;
    }

    /**
     * protected recursive selection operation
     *
     * @param h node at which to do the search
     * @param k which element
     * @return return a node
     */
    protected ITEM selectR(Node h, int k) {
        if (h == null) {
            return null;
        }
        progression(h);
        int t = (h.l == null) ? 0 : h.l.N;
        if (t > k) {
            return selectR(h.l, k);
        }
        if (t < k) {
            return selectR(h.r, k - t - 1);
        }
        return h.item;
    }

    /**
     * public select methode
     *
     * @param k item number to select
     * @return return the item
     */
    public ITEM select(int k) {
        start(head);
        return selectR(head, k);
    }

    protected void progression(Node p) {
        father = p;
        if (p != null) {
            fireProgessionEvent(p);
        }
    }

    protected void start(Node p) {
        father = null;
        if (p != null) {
            fireStartEvent(p);
        }
    }

    protected void rotateL(Node p) {
        if (p != null) {
            fireRotateLeftEvent(p);
        }
    }

    protected void rotateR(Node p) {
        if (p != null) {
            fireRotateRightEvent(p);
        }
    }

    protected void insertion(Node p) {
        if (p != null) {
            fireInsertEvent(p, father);
        }
    }

    public abstract void remove(KEY v);

    public abstract void leftRotation(KEY v);

    public abstract void rightRotation(KEY v);


    public void preorderTraversal(Node h) {
        if (h == null) {
            return;
        }
        progression(h);
        preorderTraversal(h.l);
        preorderTraversal(h.r);
    }

    public void orderTraversal(Node h) {
        if (h == null) {
            return;
        }
        orderTraversal(h.l);
        progression(h);
        orderTraversal(h.r);
    }

    public void postorderTraversal(Node h) {
        if (h == null) {
            return;
        }
        postorderTraversal(h.l);
        postorderTraversal(h.r);
        progression(h);
    }
}
