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
 * Ferningur sem hægt er að teikna.Hefur hæð til viðbótar við (x,y) og breidd sem hann
 * fær frá Flotur 
 * @author Ebba Þóra Hvannberg ebba@hi.is  
 * HBV201G 
 * Háskóli Íslands
 * 
 * @version 1
 */
public class Ferningur extends Flotur {

     /**
     * height er hæð fernings;
     */
    private final int height;
    
    /**
     * Smíðar ferning af lit c
     * @param c litur 
     */
    public Ferningur(Color c) {
        litur = c;
        height = 30;
    }
    
/**
 * Skilar hæð fernings 
 * @return height
 */
    public int getHeight(){
        return height;
    }
    
    @Override
    public void prenta() {
        System.out.println ("x " + xPos + "y " + yPos + "width "+width+" height"+height);
    }
/**
     *  Teiknar ferning sem er skilgreindur í xPos, yPos 
     *  með breidd width og hæð height.Endurforritar aðferð sem skilgreind var í Flotur (@Overide)
     *  @param g Graphics hlutur sem er teiknað á
     *
     */
   @Override 
    public void paint(Graphics g){
       // notum tilviksbreytuna
        g.setColor(litur);
        g.fillRect(xPos,yPos,width,height);
        g.setColor(Color.BLACK);
        g.drawRect(xPos,yPos,width,height);  
    }
    
    @Override
    public boolean innan(int x, int y) {
       return ( (y-yPos-1) <= height && (x-xPos-1) <= width); 
    }
    
}



