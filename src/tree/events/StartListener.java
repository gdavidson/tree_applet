/*
 * ProgressionListener.java
 *
 * Created on 16 novembre 2004, 22:10
 */

package tree.events;
import java.util.EventListener;
/**
 *
 * @author  Wurtz Jean-Marie
 */
public interface StartListener extends TreeListener {
    void startAction(StartEvent e);
}
