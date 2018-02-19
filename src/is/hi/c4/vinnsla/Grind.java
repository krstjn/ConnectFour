package is.hi.c4.vinnsla;

import java.awt.Point;

/**
 * Grind.java
 *
 * Lógíkin fyrir Connect four leikinn. Klasinn hefur tvívítt fylki með breidd dálkum og haed línum. 
 * Hann heldur líka utan um efsta lausa stakið í hverjum dálki. 
 * Klasinn heldur utan um hver á að gera næst. 
 * Notað er enum (TegundPeds) til að tákna mismunandi peð, þ.e. hring, ferning og tómt. 
 * Klasinn notast við klasann Point til að geyma (x,y) hnit í fylkinu. 
 * 
 * Athugið að núllta línan í fylkinu á að birtast sem neðsta línan í grindinni og núllti dálkurinn á að birtast sem 
 * dálkurinn lengst til vinstri í grindinni. 
 * 
 * Skoðið sérstaklega public aðferðir
 * 
 * Skoðið hvernig hægt er að nota aðferðina finnaTegundPeds
 *
 * @author Ebba Þóra Hvannberg ebba@hi.is HBV201G Háskóli Íslands
 *
 * @version 1
 * @date Feb 10, 2017
 */
public class Grind {

    private final int haed; // hæð grindarinnar
    private final int breidd;   // breidd grindarinnar
    protected final TegundPeds[][] pedin; // grindin 
    protected final int[] efsti;  // efsta lausa stakið í grindinni

    protected TegundPeds hverErNaestur = TegundPeds.TOMT;

  
/** 
 * 
 * Hér fyrir neðan eru private aðferðir sem þið þurfið ekki að kynna ykkur sérstaklega 
 * 
 */
    /**
     * Finna fjóra eins í einvíðu fylki af stærð top
     *
     * @param g fylki sem á að skoða
     * @param top stærð fylkis
     * @return Ef fjórir eins finnast þá er skilað sætinu annars -1
     */
    private int finnaFjora(TegundPeds[] g, int top) {
        int r = 1;
        TegundPeds sidasti;
        sidasti = g[0];
        for (int j = 1; j < top; j++) {
            if (g[j] == TegundPeds.TOMT) {
                return -1;
            }
            if (g[j] == sidasti) {
                r++;
                if (r == 4) {
                    return j - 4 + 1;
                }
            } else {
                r = 1;
                sidasti = g[j];
            }
        }
        return -1;
    }

    /**
     * Skilar línu úr tvívíðu fylki
     *
     * @param pedin tvívítt fylki
     * @param linaNr línan sem á að ná í
     * @param breidd breidd fylkisins
     * @return
     */
    private TegundPeds[] lina(TegundPeds[][] pedin, int linaNr, int breidd) {
        TegundPeds[] p = new TegundPeds[breidd];
        for (int i = 0; i < breidd; i++) {
            p[i] = pedin[i][linaNr];
        }
        return p;
    }
    
    
    /***
     * 
     * Hér eru public aðferðir sem þið ættuð að kynna ykkur vel og nota í klösunum ykkar 
     * 
     */
    
    /**
     * setters og getters
     *
     */
    public TegundPeds getHverErNaestur() {
        return hverErNaestur;
    }

    public void setHverErNaestur(TegundPeds hverErNaestur) {
        this.hverErNaestur = hverErNaestur;
    }

    public enum TegundPeds {
        TOMT, HRINGUR, FERNINGUR
    };

    /**
     * Smiður sem tekur inn hæð og breidd á Connect 4 borði, frumstillir tvívítt
     * fylki af stærðinni breidd x hæð 
     *
     * @param Point p - Point hefur x og y gildi. x og y er breidd og hæð og eru jákvæð tala stærri en 0
    */
    public Grind(Point p) {
        haed = (int) p.getX();
        breidd = (int) p.getY();
        pedin = new TegundPeds[haed][breidd];
        efsti = new int[breidd];

        // núllstilli peðin 
        for (int u = 0; u < breidd; u++) {
            java.util.Arrays.fill(pedin[u], TegundPeds.TOMT);
            efsti[u] = 0;
        }
    }

    /**
     * Setur peð af tegund t í efsta slott í grindinni. Aðferðin reiknar með að
     * til sé laust slott og að i sé löglegt
     *
     * @param i er dálkurinn í grindinni
     * @param t er HRINGUR eða FERNINGUR
     *
     */
    public void setjaPedI(int i, TegundPeds t) {
        pedin[i][efsti[i]] = t;
        efsti[i] = efsti[i] + 1;
    }

    /**
     * Skilar satt ef það er laust slot í i-ta dálkinum
     *
     * @param i dálkurinn
     * @return skilar satt ef það er laust slott en annars ósatt
     */
    public boolean erLaus(int i) {
        return efsti[i] < haed;
    }

    /**
     * Skilar nr. efsta staksins í i-ta dálkinum í ped
     *
     * @param i - nr. dálks
     * @return index á efsta stakið
     */
    public int getEfsti(int i) {
        return efsti[i];

    }

    /**
     * Finna fjögur peð í röð lóðrétt
     *
     * @return skilar hniti (af klasanum Point) þar sem byrjunarpeðið er
     * en null ef ekkert fannst
     */
    public Point finnaFjoraLodrett() {
        int p;

        for (int i = 0; i < breidd; i++) {
            p = finnaFjora(pedin[i], efsti[i]);
            for (int j = 0; j < efsti[i]; j++) {
                if (p >= 0) {
                    return new Point(i, p);
                }
            }
        }
        return null;
    }

    /**
     * Finna fjögur peð í röð lárétt
     *
     * @return skilar hniti (af klasanum Point) þar sem byrjunarpeðið er 
     * en null ef ekkert fannst
     */
    public Point finnaFjoraLarett() {
        int p;
        TegundPeds[] linaIGrind;
        for (int i = 0; i < haed; i++) {
            linaIGrind = lina(pedin, i, breidd);
            p = finnaFjora(linaIGrind, breidd);

            if (p >= 0) {
                return new Point(p, i);
            }
        }
        return null;
    }

    /**
     * Skilar Tegund peðs sem hefur nafnið s
     * 
     * ATHUGIÐ að dæmi um notkun úr t.d. LausPedPanel gæti verið: 
     * 
     * // fyrst koma tilviksbreyturnar í LausPedPanel 
     *  private final Grind minGrind;
     *  private final ArrayList<Flotur> fletir = new ArrayList<>();
     * 
     * // Hér kemur dæmi um hvernig aðferðin er notuð  
     *  minGrind.finnaTegundPeds(fletir.get(nuverandi).getClass().toString()));
     * 
     * 
     * @param s
     * @return 
     */
    public TegundPeds finnaTegundPeds(String s) {
         switch (s) {
            case "class is.hi.c4.rum.Ferningur":
                return TegundPeds.FERNINGUR;
            case "class is.hi.c4.rum.Hringur":
                return TegundPeds.HRINGUR;
            default:
                return TegundPeds.TOMT;
        }
    }
}
