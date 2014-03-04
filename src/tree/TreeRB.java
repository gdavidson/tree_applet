/*
 * TreeRB.java
 *
 * Created on 8 novembre 2004, 22:14
 */

package tree;

/**
 * A red-black tree
 * @author Wurtz Jean-Marie
 */

public class TreeRB extends Tree {
    /** Creates a new instance of TreeRB */
    public TreeRB() {
        super();
    }
    private boolean fixUp;
    private static final boolean R = true;
    private static final boolean B = false;
    
    private static boolean red(Node x)
    { if (x == null) return false; return x.isRed(); }
    
    private static boolean black(Node x)
    { return !red(x); }
    
    private Node insertR(Node h, ITEM x, boolean sw) {
        if (h == null) {
            Node n=new Node(x, R);
            insertion(n);
            return n;
        }
        
        progression(h);
        if (red(h.l) && red(h.r))
        { h.setRed(); h.l.setBlack(); h.r.setBlack(); }
        if (less(x.key(), h.item.key())) {
            h.l = insertR(h.l, x, false);
            if (red(h) && red(h.l) && sw) h = rotR(h);
            if (red(h.l) && red(h.l.l))
            { h = rotR(h); h.setBlack(); h.r.setRed(); }
        }
        else {
            h.r = insertR(h.r, x, true);
            if (red(h) && red(h.r) && !sw) h = rotL(h);
            if (red(h.r) && red(h.r.r))
            { h = rotL(h); h.setBlack(); h.l.setRed(); }
        }
        h.N=Nb(h.l)+Nb(h.r)+1;
        return h;
    }
    /**
     * public method to insert an element in a red-black tree
     * @param x item to insert
     */
    public void insert(ITEM x) {
        start(head);
        head = insertR(head, x, false);
        head.setBlack();
    }
    /**
     * public method to remove an item with a given KEY
     * @param v KEY of the item to be removed
     */
    @Override
    public void remove(KEY v) {
        fixUp = false;
        if (head == null) return;
        start(head);
        if (equals(v, head.item.key())) {
            head=replaceNode(head, true, head);
        } else if (less(v, head.item.key())) {
            head=removeLeft(head, v);
            //if (fixUp) head=fixUpLeft(head);
        } else {
            head=removeRight(head, v);
            //if (fixUp) head=fixUpRight(head);
        }
        head.N=Nb(head.l)+Nb(head.r)+1;
    }
    
    /**
     * remove the element in the left subtree
     * @param p parent node of the of the left subtree to be visited
     * @param v KEY of the ITEM to be removed
     * @return new parent node. Can change due to fix ups
     */
    protected Node removeLeft(Node p, KEY v) {
        System.out.println("removeLeft p:"+p+" v = "+v);
        progression(p);
        if (equals(v, p.l.item.key())) {
            p.l=replaceNode(p.l, true, p.l);
        } else if (less(v, p.l.item.key())) {
            p.l=removeLeft(p.l, v);
        } else {
            p.l=removeRight(p.l, v);
        }
        if (fixUp) p=fixUpLeft(p);
        p.N=Nb(p.l)+Nb(p.r)+1;
        return p;
    }
    /**
     * remove the element in the right subtree
     * @param p parent node of the of the left subtree to be visited
     * @param v KEY of the ITEM to be removed
     * @return new parent node. Can change due to fix ups
     */
    protected Node removeRight(Node p, KEY v) {
        System.out.println("removeRight p:"+p+" v = "+v);
        progression(p);
        if (equals(v, p.r.item.key())) {
            p.r=replaceNode(p.r, true, p.r);
        } else if (less(v, p.r.item.key())) {
            p.r=removeLeft(p.r, v);
        } else {
            p.r=removeRight(p.r, v);
        }
        if (fixUp) p=fixUpRight(p);
        p.N=Nb(p.l)+Nb(p.r)+1;
        return p;
    }
    /**
     * find a node which can be exchanged with the element to be deleted so that we remove a leaf node
     * We are looking for the successor of the node to be deleted
     * @param p parent node
     * @param dir do we come for the right(true) or the left (false)subtree
     * @param exchg node to be deletec (so exchanged with the leaf node)
     * @return the new parent after fixups
     */
    protected Node replaceNode(Node p, boolean dir, Node exchg) {
    /* p is the parent Node of the element to be deleted
     *                P
     *             /    \
     *            L      R
     *           /  \   /  \
     *          A    B  C  D
     */
        System.out.println("replaceNode p:"+p+" "+dir+" "+exchg);
        progression(p);
        if (p.l == null && p.r == null) {
            if (p != exchg) exchange(p,exchg);
            fixUp=black(p); p=null;
        } else if (p.l==null) {//p.r !=null && p.l==null
            if (black(p.r)) throw new Error("In replaceNode I: Node should be red !!!");
            exchange(p.r,exchg); p.r=null; fixUp=false;
        } else if (p.r == null) {//p.l !=null && p.r==null
            if (black(p.l)) throw new Error("In replaceNode II: Node should be red !!!");
            exchange(p.l,exchg); p.l=null; fixUp=false;
        } else { // p.l != null && p.r != null
            if (dir) {
                p.r = replaceNode(p.r, false, exchg);
                if (fixUp) p=fixUpRight(p);
            } else {
                p.l=replaceNode(p.l, false, exchg);
                if (fixUp) p=fixUpLeft(p);
            }
        }
        if (p!=null) p.N=Nb(p.l)+Nb(p.r)+1;
        return p;
    }
    /**
     * excahnge the item of the 2 nodes without changing the references
     * @param a node a
     * @param b node b
     */
    protected void exchange(Node a, Node b) {
        ITEM tmp;
        tmp = a.item;
        a.item = b.item;
        b.item=tmp;
    }
    
    /**
     * fix up done on the subtree with parent node p if we are in a left subtree
     * The different transformations anf fixups are documented in the code
     * @param p parent node
     * @return new parent node after fixups
     */
    protected Node fixUpLeft(Node p) {
        if (p.l==null) {
            /*
             * in case I and II the left Node L has just been deleted during the replacement
             * case I : parent is red, change black; nothing to fixUp
             * p is the parent Node of the element to be deleted;  . : null
             *                  //               //           /
             *  L has been     P                 P            P
             *  deleted     /    \             /   \        /  \\
             *             L      R      ==>  .    R  ==>   .    R
             *            / \    / \              / \           / \
             *           .   .   .  .             .  .          .  .
             *   NO FIXUP
             *
             * case II : parent is black
             * p is the parent Node of the element to be deleted;
             *                  /                   /             /
             * L has been      P                    P            P
             * deleted      /    \                /  \          /  \\
             *             L      R      ==>     .    R  ==>    .    R
             *            / \    / \                 / \            / \
             *           .   .   .   .              .   .           .  .
             *  we need to FIXUP
             */
            System.out.println("\tfixUpLeft I :"+p);
            fixUp=black(p); p.setBlack();
            if (red(p.r)) throw new Error("In fixUpLeft I: Node should be black");
            p.r.setRed();
        } else {
            /* p.l != nul;
             * case III to VII are transformations
             */
            Node s=p.r;
            if (black(s) && black(s.r) && red(s.l)) {       // case III
                System.out.println("\tfixUpLeft III t :"+p);
                /*case III
                 *     p is the parent Node of the element to be deleted;  . : null
                 *                  /                    /
                 *                 P                    S
                 *              /     \\             //    \
                 *             N       S      ==>   P      SR
                 *            / \    /   \         / \    /  \
                 *           1  2   SL   SR       N  SL   5   6
                 *                 / \  / \      / \
                 *                 3  4 5  6     1  2
                 *we need to FIXUP
                 */
                s.setRed(); s.l.setBlack(); p.r=rotR(p.r);
            }
            if (black(p)){
                if (black(p.l) && red(p.r)) {                   // case IV
                /*Case IV
                 *     p is the parent Node of the element to be deleted;  . : null
                 *                  /              /
                 *                 S      ==>     SL
                 *               //  \           /  \
                 *              SL   SR         1    S
                 *             / \  / \             / \
                 *             3  4 5  6           2  SR
                 *                                    / \
                 *                                    3 4
                 *we need to FIXUP
                 */
                    System.out.println("\tfixUpLeft IV t :"+p);
                    p.setRed(); p.r.setBlack(); p=rotL(p);
                }
            }
            s=p.r;
            if (black(p.l) && black(p.r)) {
                if (red(s.r)){                                  //case VII
                    /*  case VII
                     *     p is the parent Node of the element to be deleted;  . : null
                     *                  ?                    ?
                     *                 P      ==>           S
                     *               /  \                 /   \
                     *              N    S               P     Sr
                     *             / \  / \\            / \   / \
                     *             1  2 3  Sr          N   3  4  5
                     *                     / \        / \
                     *                     4  5       1  2
                     *we need NO FIXUP
                     */
                    System.out.println("\tfixUpLeft VII :"+p);
                    if (red(p)) s.setRed(); else s.setBlack();
                    p.setBlack(); s.r.setBlack(); p=rotL(p); fixUp=false;
                } else if (black(s.l)) {                        // case V and VI
                    /*case V
                     *     p is the parent Node of the element to be deleted;  . : null
                     *                  //                      /
                     *                 P      ==>              P
                     *               /    \                 /    \
                     *              N      S               N      S
                     *             / \   /   \            / \   /   \\
                     *             1  2 Sl   Sr          1   2  Sl  Sr
                     *                 / \   / \              / \   / \
                     *                 3 4   5 6              3 4   5 6
                     *we need NO FIXUP
                     *
                     *case VI
                     *     p is the parent Node of the element to be deleted;  . : null
                     *                  /                      /
                     *                 P      ==>              P
                     *               /    \                 /    \
                     *              N      S               N      S
                     *             / \   /   \            / \   /   \\
                     *             1  2 Sl   Sr          1   2  Sl  Sr
                     *                 / \   / \              / \   / \
                     *                 3 4   5 6              3 4   5 6
                     *we need to FIXUP
                     */
                    System.out.println("\tfixUpLeft V+VI :"+p);
                    s.setRed();fixUp=black(p); p.setBlack();
                }
            }
        }
        return p;
    }
    /**
     * fix up done on the subtree with parent node p if we are in a right subtree
     * The different transformations anf fixups are documented in the code of the left fix ups
     * forthe righ fixups the symetric operation is realized
     * @param p parent node
     * @return new parent node after fixups
     */
    protected Node fixUpRight(Node p) {
        if (p.r==null) {
            System.out.println("\tfixUpRight I :"+p);
            fixUp = black(p); p.setBlack();
            if (red(p.l)) throw new Error("In fixUpRight I: Node should be black");
            p.l.setRed();
        } else {
            /* p.r != nul;
             * case III and IV are transformations
             */
            Node s=p.l;
            if (black(s) && black(s.l) && red(s.r)) {               // case III
                System.out.println("\tfixUpRight III t :"+p);
                s.setRed(); s.r.setBlack(); p.l=rotL(p.l);
            }
            if (black(p)){
                if (black(p.r) && red(p.l)) {                   // case IV
                    System.out.println("\tfixUpRight IV t :"+p);
                    p.setRed(); p.l.setBlack(); p=rotR(p);
                }
            }
            s=p.l;
            if (black(p.r) && black(p.l)) {
                if (red(s.l)){                                      //case VII
                    if (red(p)) s.setRed(); else s.setBlack();
                    p.setBlack(); s.l.setBlack(); p=rotR(p); fixUp=false;
                    System.out.println("\tfixUpRight VII :"+p);
                } else if (black(s.r)) {                              // case V and VI
                    System.out.println("\tfixUpRight V+VI :"+p);
                    s.setRed(); fixUp=black(p); p.setBlack();
                }
            }
        }
        return p;
    }

    @Override
    public void leftRotation(KEY v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void rightRotation(KEY v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
