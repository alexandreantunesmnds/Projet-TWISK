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
    protected Guichet guich2;

    @BeforeEach
    void setUp() {
        this.act = new Activite("Piscine",6,3);
        this.guich = new Guichet("Caisse");
        this.actRestreinte = new ActiviteRestreinte("ActRestreinte",4,2);
        this.guich2 = new Guichet("Attente",3);
    }

    @Test
    void ajouterSuccesseur() {
        guich.ajouterSuccesseur(act);
        assertEquals(1,guich.nbSuccesseur());

        assertEquals(0,act.nbSuccesseur());
    }

    @Test
    public abstract void estUneActivite();

    @Test
    abstract void estUnGuichet();

    @Test
    void getNumero(){
        FabriqueNumero.getInstance().reset();
        Etape A = new Activite("Jeux");
        Etape B = new Activite("Coloriage");
        Etape C = new Guichet("Caisse");
        Etape D = new ActiviteRestreinte("ActRestreinte");
        assertEquals(A.getNumero(),0);
        assertEquals(B.getNumero(),1);
        assertEquals(C.getNumero(),2);
        assertEquals(D.getNumero(),3);
    }
}