/*
 * KEY.java
 *
 * Created on 4 novembre 2004, 21:30
 */

package tree;

/**
 *
 * @author  Wurtz Jean-Marie
 */
public interface KEY {
    public boolean less(KEY myKey);
    public boolean equals(KEY myKey);
    //void read();
    public void rand(int M);
    public String toString();
}
