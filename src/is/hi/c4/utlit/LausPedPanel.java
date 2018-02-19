
package is.hi.c4.utlit;

import is.hi.c4.rum.Ferningur;
import is.hi.c4.rum.Flotur;
import is.hi.c4.rum.Hringur;
import is.hi.c4.vinnsla.Grind;
import java.awt.Color;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;



/**
 * Klasi sem teiknar og hreyfir peð á glugganum, 
 * @author Kristjan Þorarinsson, kth130@hi.is 
 * HBV201G 
 * Háskóli Íslands
 * 
 * @version 1
 */
public class LausPedPanel extends javax.swing.JPanel {
    private Grind minGrind;
    private ArrayList<Flotur> fletir;
    private Flotur nuverandiPed; // Peðið sem var valið
    private String gamlaPed = ""; // Hjálpar við að segja hver á að gera næst
    private int oldX, oldY; // Heldur utan um hnit hluts, fyrir færslu
    private boolean leikurUnninn; // er leikurinn búinn?

    /**
     * Creates new form LausPedPanel
     */
   
    public LausPedPanel() {
        initComponents();
       
        fletir = new ArrayList<>();
        leikurUnninn = false;
        minGrind = new Grind(new Point(4,4));
        geraPed();
        
        // Setjum handler sem bregst við músarsmelli (e. pressed)
        addMouseListener(new MouseAdapter() {
        @Override public void mousePressed(MouseEvent e) {
                // bara hægt að velja peð ef að leikurinn er ekki búinn
                if(!leikurUnninn)
                    nuverandiPed = hvadaFlotur(e.getX(),e.getY());
                
                if(nuverandiPed != null){
                    oldX = nuverandiPed.getX();
                    oldY = nuverandiPed.getY();
                }
            }
        });
        // Setjum handler sem bregst við færslu músar (e. dragged) 
        addMouseMotionListener(new MouseAdapter() {
        @Override public void mouseDragged(MouseEvent e) {
             // Passar að sami leikmaður geri ekki tvisvar
             if(nuverandiPed!= null
             && !nuverandiPed.getClass().toString().equals(gamlaPed))
                 faeraHlut(e.getX(),e.getY(), nuverandiPed);
            }
        });
        // Setjum handler sem bregst við losun músar (e. released)
        addMouseListener(new MouseAdapter() {
        @Override public void mouseReleased(MouseEvent e) {
            DalkurPanel d = ((T4)getTopLevelAncestor()).getDalkur();
            if(d != null){
                athugaPed(d, Integer.valueOf(d.getName()));
            } else if(nuverandiPed != null){
                faeraHlut(oldX ,oldY,nuverandiPed );
            }
            }
           
        });
    }
    /**
     * Aðferð sem að býr til peðin fyrir leikinn
     */
    public void geraPed(){
        for(int i = 0; i<8;i++){
            Flotur f = new Ferningur(Color.RED);
            f.setX(i*45+30);
            fletir.add(f);
        }
        for(int i = 0; i<8;i++){
            Flotur f = new Hringur(Color.YELLOW);
            f.setX(i*45+30);
            f.setY(100);
            fletir.add(f);
        }
    }
    /**
     * Núllstillir breytur og býr til nýjan leik
     */
    public void nyrLeikur(){
        minGrind = new Grind(new Point(4,4));
        gamlaPed = "";
        fletir.clear();
        geraPed();
        repaint();
        leikurUnninn = false;
        ((T4)getTopLevelAncestor()).setSkilabod("Hefjið Leik");
    }
    
    /**
     * Setur peð í dálk og í grindina ef það er pláss, færir peðið annars 
     * aftur á sinn upprunalega stað.
     * @param d
     * @param n 
     */
    public void athugaPed(DalkurPanel d, int n){
        if(nuverandiPed == null){
            return;
        }
        if(!nuverandiPed.getClass().toString().equals(gamlaPed) 
                && minGrind.erLaus(n))
        {   // setja peð í dálkinn
            d.setjaI(nuverandiPed);
            // setja peð í grind
            minGrind.setjaPedI(n,minGrind.finnaTegundPeds(
                    nuverandiPed.getClass().toString()));
            //Skilaboð um hver á að gera næst
            ((T4)getTopLevelAncestor()).setSkilabod(getNext());
            // stillum gamlaPed svo hægt sé að bera saman við hver gerir næst
            gamlaPed = nuverandiPed.getClass().toString();
            // eyða peðinu sem var notað
            fletir.remove(nuverandiPed); 

            leikurUnninn(d);// athuga hvort leikur sé unninn
            
            //sendir skilaboð ef öll peð hafa verið notuð
            if(fletir.isEmpty() && !leikurUnninn){
                ((T4)getTopLevelAncestor()).setSkilabod(
                        "Leik lokið - enginn sigurvegari");
            }
            
        } else {
            // færir peð aftur á sinn stað 
            if(nuverandiPed != null){
                faeraHlut(oldX ,oldY,nuverandiPed );}
        }
                
    }
    /**
     * Athugar hvort að komnir séu fjórir í röð og sýnir sigurvegara
     * ef svo sé.
     * @param d 
     */
    public void leikurUnninn(DalkurPanel d){
        if(minGrind.finnaFjoraLarett() != null && !leikurUnninn){
            Point p = minGrind.finnaFjoraLarett();
            // sýnir sigurvegara í grind
            for(int i = 0;i<4;i++){
                DalkurPanel f = ((T4)getTopLevelAncestor()).skilaDalk(i);
                f.linaWinner((int) p.getY()); 
            }
            // birtir sigurvegara í grind
            ((T4)getTopLevelAncestor()).setSkilabod(getSidasti() + " - lárett");
            leikurUnninn = true;
        }
        else if(minGrind.finnaFjoraLodrett() != null && !leikurUnninn){
            // sýnir sigurvegara í grind
            d.dalkurWinner(); 
            // birtir skilaboð um sigurvegara
            ((T4)getTopLevelAncestor()).setSkilabod(getSidasti() + " - lóðrétt");
            leikurUnninn = true;
        }
    }
        
    /**
     * Skilar sigurvegaranum í leiknum
     * @return 
     */
    public String getSidasti(){
         switch (gamlaPed) {
            case "class is.hi.c4.rum.Ferningur":
                return "Leik lokið - Rauður vann";
            case "class is.hi.c4.rum.Hringur":
                return "Leik lokið - Gulur vann";
            default:
                return "";
        }
    }
    
    /**
     * Athugar hver var að gera síðast og skilar upplýsingum um 
     * hver á að gera næst
     * @return 
     */
    public String getNext(){
         switch (nuverandiPed.getClass().toString()) {
            case "class is.hi.c4.rum.Ferningur":
                return "Gulur á leik";
            case "class is.hi.c4.rum.Hringur":
                return "Rauður á leik";
            default:
                return "";
        }
    }
    
   /**
    * Færir hlut, ef að hlutur var valinn 
    * @param x
    * @param y
    * @param f 
    */
    private void faeraHlut(int x, int y, Flotur f) {
        if (f != null) {
            f.moveObject(this, x, y);
        } 
    } 
    
    /**
     * Athugar hvort að einhver músarsmellur var á peði
     * @param x
     * @param y
     * @return Flotur - skilar peðinu sem var valið / eða engu
     */
    private Flotur hvadaFlotur(int x, int y) {
        for (Flotur f: fletir) {
            if(f.innan(x,y)) {
                return f;
            } }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        
        for(Flotur f: fletir) {
            f.paint(g);
        }
    }  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
