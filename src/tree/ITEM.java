/*
 * ITEM.java
 *
 * Created on 4 novembre 2004, 21:29
 */

package tree;

/**
 *
 * @author  Wurtz Jean-Marie
 */
public interface ITEM {
    public KEY key();
    //void read();
    public void rand(int M);
    public String toString();
}
