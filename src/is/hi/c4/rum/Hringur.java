/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package is.hi.c4.rum;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Hringur sem hægt er að teikna
 * @author Ebba Þóra Hvannberg ebba@hi.is  
 * HBV201G 
 * Háskóli Íslands
 * 
 * @version 1
 */
public class Hringur extends Flotur {

    
    /**
     * Smíðar Hringur hlutur af lit c 
     * @param c litur hringsins
     */
    public Hringur(Color c) {
        litur = c;
    }
    
    public Point midja () {
        Point p;
        p = new Point();
        p.x = xPos+width/2;
        p.y = yPos + width/2;
        return p;
    }
    
    @Override
    public void prenta() {
        System.out.println ("x " + xPos + "y " + yPos + "width "+width);
    }
    
/**
     *  Teiknar hringsem er skilgreindur í xPos, yPos 
     *  með breidd width og hæð height
     *  Endurforritar aðferð sem skilgreind var í Flotur
     *  @param g Graphics hlutur sem er teiknað á
     *
     */
    @Override
    public void paint (Graphics g){
        g.setColor(litur);
        g.fillOval(xPos, yPos, width, width);
        g.setColor(Color.BLACK);
        g.drawOval(xPos, yPos, width, width);  
    }
    
    @Override
    public boolean innan(int x, int y) {
        Point p = midja();
        return (Math.pow(x-p.x,2) + Math.pow(y-p.y,2)) <= Math.pow(width,2); 
    }
}



