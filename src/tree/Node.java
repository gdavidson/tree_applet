/*
 * Node.java
 *
 * Created on 10 novembre 2004, 11:51
 */

package tree;

/**
 *
 * @author  Wurtz Jean-Marie
 */
public class Node
{ public ITEM item; public Node l, r; public int N; public boolean isRed;
  Node(ITEM x) { this(x, false); }
  Node(ITEM x, boolean color)
  { item = x; this.l = null; this.r = null; isRed=color; N=1; }
    public boolean isRed() {return isRed;}
    public boolean isBlack() {return !isRed;}
    public void setRed() {isRed=true;}
    public void setBlack() {isRed=false;}
    public String toString() {
        String s= (isRed ? "/r" : "");
        //return item.toString()+s;
        return item.key()+s+"("+N+")";
    }
  










}
