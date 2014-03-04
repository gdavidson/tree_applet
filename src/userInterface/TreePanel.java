/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import java.awt.Color;
import tree.events.RotateLeftEvent;
import tree.events.ProgressionEvent;
import tree.events.StartEvent;
import tree.events.RotateRightEvent;
import tree.events.InsertEvent;
import java.awt.Graphics;
import javax.swing.JPanel;
import tree.Node;
import tree.Tree;
import tree.TreeListener;

/**
 *
 * @author 1gdavidson
 */
public class TreePanel extends JPanel implements TreeListener {
    private Tree tree;
    
    
    public TreePanel() {
 }
    
    @Override
     public void paintComponent(Graphics g) {
     
       if (tree!= null) {
             this.drawTree(g,tree);
         }
    }
    
    public void drawTree (Graphics g, Tree t) {
        this.removeAll();
        Node root = t.root();
        int width=this.getWidth();    //640 c bien
        drawLinks(g,root, width,1,2,20); 
        drawNodes(g,root, width,1,2,20);
    }
    
    //dessiner liens, size= taille du panel, numerateur, denominateur, y initial
    public void drawLinks(Graphics g, Node h, int size, float num, float deno, int y) {
        if (h==null) {
            return;
        }
        if (h.isRed()) {g.setColor(Color.red);}
        else {
            g.setColor(Color.black);
        }
        int posX= (int) (size*(num/deno));  //position de X
        int nextY=y+50;     // chaque niveau separé de 50 pxl
        float nextDeno=deno*2;  // denominateur*2
        float nextNumLeft=num*2-1;  //num gauche
        float nextNumRight=num*2+1; //num droit
        int nextXleft=(int)(size*(nextNumLeft/nextDeno));   //position du noeud gauche suivant
        int nextXright=(int)(size*(nextNumRight/nextDeno)); //position du noeud droit suivant
        g.drawLine(posX, y, nextXleft, nextY);  //ligne du noeud courant au noeud gauche suivant
        g.drawLine(posX, y, nextXright, nextY); //ligne du noeud courant au noeud droit suivant
        
        drawLinks(g,h.l,size,num*2-1,deno*2,nextY);     //appel recursif
        drawLinks(g,h.r,size, num*2+1,deno*2,nextY);
    }
    
    public void drawNodes(Graphics g, Node h, int size, float num, float deno, int y) {
        if (h==null) {
            return;
        }
        int NodeSize=30;
        int posX= (int) (size*(num/deno))-(NodeSize/2);
        int posY= y-(NodeSize/2);
        int nextY=y+50;
        float nextDeno=deno*2;
        float nextNumLeft=num*2-1;
        float nextNumRight=num*2+1;
        if (h.isRed()) {
            Color c= Color.RED;
            g.setColor(c);
        } else {
            Color c= Color.LIGHT_GRAY;
            g.setColor(c);
        }
        
        g.fillOval(posX, posY, NodeSize, NodeSize);
        g.setColor(Color.black);
        g.drawOval(posX, posY, NodeSize, NodeSize);
        String val=h.item.key().toString();
        g.drawString(val, posX+5, y+5);
        
        drawNodes(g, h.l, size, nextNumLeft, nextDeno, nextY);
        drawNodes(g,h.r, size, nextNumRight, nextDeno, nextY);
    }
    

    @Override
    public void progressionAction(ProgressionEvent e) {
        //repaint();

        
    }

    @Override
    public void startAction(StartEvent e) {
       // repaint();

    }

    @Override
    public void rotateLeftAction(RotateLeftEvent e) {
       repaint();
    
    }

    @Override
    public void rotateRightAction(RotateRightEvent e) {
        repaint();

    }

    @Override
    public void insertAction(InsertEvent e) {
        repaint();

    }

    /**
     * @return the tree
     */
    public Tree getTree() {
        return tree;
    }

    /**
     * @param tree the tree to set
     */
    public void setTree(Tree tree) {
        this.tree = tree;
    }
 
}
