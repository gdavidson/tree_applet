package tree;
import tree.events.ProgressionListener;
import tree.events.StartListener;
import tree.events.RotateLeftListener;
import tree.events.RotateLeftEvent;
import tree.events.ProgressionEvent;
import tree.events.StartEvent;
import tree.events.RotateRightEvent;
import tree.events.InsertEvent;
import tree.events.RotateRightListener;
import javax.swing.event.EventListenerList;
import java.util.EventListener;
import tree.events.*;

/**
 *
 * @author  Wurtz Jean-Marie
 */
public class TreeHandleEvents extends EventListenerList {
    /** Creates a new instance of TreeListener */
    public TreeHandleEvents() {
    }
    public void register(EventListener l) {
        addProgressionListener((ProgressionListener)l);
        addStartListener((StartListener)l);
        addInsertListener((InsertListener)l);
        addRotateLeftListener((RotateLeftListener)l);
        addRotateRightListener((RotateRightListener)l);
    }
    //ProgessionEvent : progression along a branch in the tree
    protected void fireProgessionEvent(Node p) {
        // Guaranteed to return a non-null array
        Object[] listeners = getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ProgressionListener.class) {
                ProgressionEvent pe = new ProgressionEvent(p);
                ((ProgressionListener)listeners[i+1]).progressionAction(pe);
            }
        }
    }
    
    public void addProgressionListener(ProgressionListener l) {
        add(ProgressionListener.class, l);
    }
    
    public void removeProgressionListener(ProgressionListener l) {
        remove(ProgressionListener.class, l);
    }
    //StartEvent : just before we start with the root of the tree
    protected void fireStartEvent(Node p) {
        // Guaranteed to return a non-null array
        Object[] listeners = getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==StartListener.class) {
                StartEvent pe = new StartEvent(p);
                ((StartListener)listeners[i+1]).startAction(pe);
            }
        }
    }
    
    public void addStartListener(StartListener l) {
        add(StartListener.class, l);
    }
    
    public void removeStartListener(StartListener l) {
        remove(StartListener.class, l);
    }
    //RotateLeft : a left rotation is done during tree reorganisation.
    // This has nothing to do with graphics
    protected void fireRotateLeftEvent(Node p) {
        // Guaranteed to return a non-null array
        Object[] listeners = getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==StartListener.class) {
                RotateLeftEvent pe = new RotateLeftEvent(p);
                ((RotateLeftListener)listeners[i+1]).rotateLeftAction(pe);
            }
        }
    }
    
    public void addRotateLeftListener(RotateLeftListener l) {
        add(RotateLeftListener.class, l);
    }
    
    public void removeRotateLeftListener(RotateLeftListener l) {
        remove(RotateLeftListener.class, l);
    }
    //RotateRight : a Right rotation is done during tree reorganisation.
    // This has nothing to do with graphics
    
    protected void fireRotateRightEvent(Node p) {
        // Guaranteed to return a non-null array
        Object[] listeners = getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==StartListener.class) {
                RotateRightEvent pe = new RotateRightEvent(p);
                ((RotateRightListener)listeners[i+1]).rotateRightAction(pe);
            }
        }
    }
    // Insert : insert event during simple binary tree insertion
    public void addInsertListener(InsertListener l) {
        add(InsertListener.class, l);
    }
    
    public void removeInserttListener(InsertListener l) {
        remove(InsertListener.class, l);
    }
    protected void fireInsertEvent(Node p, Node father) {
        // Guaranteed to return a non-null array
        Object[] listeners = getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        
        InsertEvent pe = new InsertEvent(p, father);
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==InsertListener.class) {
                ((InsertListener)listeners[i+1]).insertAction(pe);
            }
        }
    }
    
    public void addRotateRightListener(RotateRightListener l) {
        add(RotateRightListener.class, l);
    }
    
    public void removeRotateRightListener(RotateRightListener l) {
        remove(RotateRightListener.class, l);
    }
}
