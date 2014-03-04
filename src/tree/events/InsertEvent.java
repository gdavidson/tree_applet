/*
 * ProgressionEvent.java
 *
 * Created on 16 novembre 2004, 21:52
 */

package tree.events;
import java.util.EventObject;

/**
 *
 * @author  Wurtz Jean-Marie
 */
public class InsertEvent extends TreeEvent {
    protected Object father;
    /** Creates a new instance of ProgressionEvent */
    public InsertEvent(Object source, Object father) {
        super(source);
        this.father=father;
    }
    public Object getFather() {
        return father;
    }
    
}
