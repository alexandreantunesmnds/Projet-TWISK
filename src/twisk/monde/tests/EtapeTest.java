package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.ActiviteRestreinte;
import twisk.monde.Etape;
import twisk.monde.Guichet;
import twisk.outils.FabriqueNumero;

import static org.junit.jupiter.api.Assertions.*;

public abstract class EtapeTest {

    protected Guichet guich;
    protected Activite act ;
    protected ActiviteRestreinte actRestreinte;

    @BeforeEach
    void setUp() {
        this.act = new Activite("Piscine",6,3);
        this.guich = new Guichet("Caisse");
        this.actRestreinte = new ActiviteRestreinte("ActRestreinte",4,2);
    }

    @Test
    void ajouterSuccesseur() {
        guich.ajouterSuccesseur(act);
        assertEquals(1,guich.nbSuccesseur());

        assertEquals(0,act.nbSuccesseur());
    }

    @Test
    abstract void estUneActivite();

    @Test
    abstract void estUnGuichet();

    @Test
    void getNumero(){
        FabriqueNumero.getInstance().reset();
        Activite A = new Activite("Jeux");
        Activite B = new Activite("Coloriage");
        Guichet G = new Guichet("Caisse");
        Guichet H = new Guichet("Caisse 2");
        ActiviteRestreinte C = new ActiviteRestreinte("ActRestreinte");
        assertEquals(A.getNumero(),0);
        assertEquals(B.getNumero(),1);
        assertEquals(G.getNumero(),2);
        assertEquals(H.getNumero(),3);
        assertEquals(C.getNumero(),4);


    }
}