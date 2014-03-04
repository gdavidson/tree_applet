/*
 * myItem.java
 *
 * Created on 4 novembre 2004, 21:34
 */

package tree;

/**
 *
 * @author  Wurtz Jean-Marie
 */
public class myItem implements ITEM {
    private KEY val;
    private float info;   
    /** Creates a new instance of myItem */
    public myItem(int v) { val = new myKey(v); }
    public KEY key() { return val; }
    //void read()
    //  { val.read(); info = In.getFloat(); }
    public void rand(int M)
      { val.rand(M); info = (float) Math.random(); }
    public String toString() 
      { return "(" + key() + " " + info + ")"; }
}
