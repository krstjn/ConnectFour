/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is.hi.c4.rum;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Flotur er abstract klasi og ekki hægt að búa til hlut af honum
 * Hefur tilviksbreytur (x,y), breidd og lit
 * Er yfirklasi fyrir ferning og hring 
 * @author Ebba Þóra Hvannberg, Háskóli Íslands, ebba@hi.is
 */

 
public abstract class Flotur {
    
    // Tilviksbreytur í klasanum Ferningur
    /**
     * xPos er x gildi
     */
    protected int xPos = 50;
    /**
     * yPos er y gildi
     */
    protected int yPos = 50;
    /**
     * width er breidd
     */
    protected final int width = 30;
    
    /**
     * Liturinn  - frumstilltur sem rauður
     */
    protected Color litur = Color.RED;

    public Flotur() {
    }

    // Get og set aðferðir í klasanum
    public void setX(int xPos) {
       this.xPos = xPos;
    }

    public int getX() {
        return xPos;
    }

    public void setY(int yPos) {
        this.yPos = yPos;
    }

    public int getY() {
        return yPos;
    }

    public int getWidth() {
        return width;
    }

    public void setLitur(Color c) {
        this.litur = c;
    }

    public Color getLitur() {
        return litur;
    }
    
    protected abstract void prenta();

    /**
     * Færir ferninginn í punktinn (x,y) á JPanel p 
     * Endurskilgreining á aðferðinni úr yfirklasanum Flotur
     * @param p panellinn sem á að teikna á
     * @param x x gildi á punktinum (x,y) sem á að færa ferningin í
     * @param y y gildi á punktinum (x,y) sem á að færa ferningin í
     */
    
    public void moveObject(JPanel p, int x, int y) {
        int OFFSET = 1;
        if ((getX() != x) || (getY() != y)) {
            // Endurteiknar rétthyrninginn undir gamla svæði ferningsins
            p.repaint(getX(), getY(), getWidth() + OFFSET, getWidth() + OFFSET); // Hér var getHeight í ferningnum
            // Færir ferningin í (x,y)
            setX(x);
            setY(y);
            // Endurteiknar rétthyrninginn undir nýja svæði ferningsins
            p.repaint(getX(), getY(), getWidth() + OFFSET, getWidth() + OFFSET); // Hér var getHeight í ferningnum
        }
    }
    /**
     * Teiknar flötinn 
     * Verður að endurforrita með @Override í undirklösum, t.d. Ferningur eða Hringur
     * @param g graphics hlutur sem teiknað er á
     */
    public abstract void paint( Graphics g);
    
    /**
     * Athugar hvort hnit (x,y) sé innan hlutar
     * @return true ef hnit er innan flatar annars false 
     */
    public abstract boolean innan (int x, int y);
        
}
