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
public class BinaryTree extends Tree {

    private Node head;
    private Node parent;
    private boolean gauche;

    public BinaryTree() {
        super();
    }

    /**
     * protected recursive insertion at the top
     *
     * @param h node where insertion is done
     * @param x item to insert
     * @return return new root of the locale subtree after rotation operations
     */
    protected Node insertRT(Node h, ITEM x) {
        if (h == null) {
            Node n = new Node(x);
            insertion(n);
            return n;
        }
        progression(h);
        if (less(x.key(), h.item.key())) {
            h.l = insertRT(h.l, x);
            h = rotR(h);
        } else {
            h.r = insertRT(h.r, x);
            h = rotL(h);
        }
        h.N = Nb(h.l) + Nb(h.r) + 1;
        return h;
    }

    /**
     * protected method for join operation to insert an element at the top of a
     * subtree
     *
     * @param b node where to insert at the top
     * @param x item to insert
     * @return new root of local subtree after rotation operations
     */
    protected Node insertT(Node b, ITEM x) {
        start(b);
        return insertRT(b, x);
    }

    /**
     * public top insertion method
     *
     * @param x item to insert
     */
    public void insertT(ITEM x) {
        start(head);
        head = insertRT(head, x);
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

    public Node partR(Node h, int k) {
        int t = (h.l == null) ? 0 : h.l.N;
        if (t > k) {
            partR(h.l, k);
            h = rotR(h);
        }
        if (t < k) {
            partR(h.r, k - t - 1);
            h = rotL(h);
        }
        h.N = Nb(h.l) + Nb(h.r) + 1;
        return h;
    }

    /**
     * join operation to fuse subtree a and b after the deletion of their father
     *
     * @param a tree a
     * @param b tree b
     * @return new fused tree
     */
    protected Node joinLR(Node a, Node b) {
        if (b == null) {
            return a;
        }
        b = partR(b, 0);
        b.l = a;
        b.N = Nb(b.l) + Nb(b.r) + 1;
        return b;
    }

    /**
     * protected recursive remove operation
     *
     * @param h node a which to do the search to find the element to be removed
     * @param v the KEY of the item to be removed
     * @return
     */
    protected Node removeR(Node h, KEY v) {
        if (h == null) {
            return null;
        }
        progression(h);
        KEY w = h.item.key();
        if (less(v, w)) {
            h.l = removeR(h.l, v);
        }
        if (less(w, v)) {
            h.r = removeR(h.r, v);
        }
        if (equals(v, w)) {
            h = joinLR(h.l, h.r);
        }
        h.N = Nb(h.l) + Nb(h.r) + 1;
        return h;
    }

    /**
     * public remove operation of an item
     *
     * @param v KEY v of the ITEM to removed
     */
    @Override
    public void remove(KEY v) {
        start(head);
        head = removeR(head, v);
    }

    /**
     * protected recursive operation to realize the join of tree a and b
     *
     * @param a root of the first tree
     * @param b root of the second tree
     * @return resulting fused tree
     */
    protected Node joinR(Node a, Node b) {
        if (b == null) {
            return a;
        }
        if (a == null) {
            return b;
        }
        start(a);
        insertT(b, a.item);
        b.l = joinR(a.l, b.l);
        b.r = joinR(a.r, b.r);
        b.N = Nb(b.l) + Nb(b.r) + 1;
        return b;
    }

    /**
     * public method for the join operation
     *
     * @param b name of the second tree to make the jaoin
     */
    public void join(Tree b) {
        start(head);
        head = joinR(head, b.root());
    }

   
    public Node searchNode(KEY k) {
        parent = null;
        return searchNodeR(head, k);
    }

   
    protected Node searchNodeR(Node h, KEY v) {
        if (h == null) {
            return null;
        }
        if (equals(v, h.item.key())) {
            return h;
        }
        parent = h;
        if (less(v, h.item.key())) {
            gauche = true;
            return searchNodeR(h.l, v);
        } else {
            gauche = false;
            return searchNodeR(h.r, v);
        }
    }

    @Override
    public void leftRotation(KEY k) {
        Node n = searchNode(k);
        if (n != null) {
            fireRotateLeftEvent(n);
            Node tmp = rotG(n);
            if (n == head) {
                head = tmp;
            } else {
                if (gauche) {
                    parent.l = tmp;

                } else {
                    parent.r = tmp;
                }
            }
        }
    }

    @Override
    public void rightRotation(KEY k) {
        Node n = searchNode(k);
        
        if (n != null) {
            fireRotateRightEvent(n);
            Node tmp = rotD(n);
            if (n == head) {
                head = tmp;
            } else {
                if (gauche) {
                    parent.l = tmp;

                } else {
                    parent.r = tmp;
                }
            }
        }
    }

    private Node rotD(Node h) {
        Node x = h.l;
        h.l = x.r;
        x.r = h;
        return x;
    }

    private Node rotG(Node h) {
        Node x = h.r;
        h.r = x.l;
        x.l = h;
        return x;
    }
}
