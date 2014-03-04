/*
 * myKey.java
 *
 * Created on 4 novembre 2004, 21:36
 */
package tree;

/**
 *
 * @author Wurtz Jean-Marie
 */
public class myKey implements KEY {

    private int val;

    /**
     * Creates a new instance of myKey
     */
    public myKey() {
        val = 0;
    }

    public myKey(int i) {
        val = i;
    }

    public boolean less(KEY w) {
        return val < ((myKey) w).val;
    }

    public boolean equals(KEY w) {
        return val == ((myKey) w).val;
    }
    //public void read()
    //  { val = In.getInt(); }

    public void rand(int M) {
        val = (int) (M * Math.random());
    }

    public String toString() {
        return val+"";
    }
}
