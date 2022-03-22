package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.ActiviteRestreinte;
import twisk.monde.Guichet;
import twisk.outils.FabriqueNumero;

import static org.junit.jupiter.api.Assertions.*;

class GuichetTest extends EtapeTest{
    @Test
    void estUnGuichet() {
        assertTrue(guich.estUnGuichet());
    }

    @Test
    public void estUneActivite() {
        assertFalse(guich.estUneActivite());
    }

    @Test
    void getNbjetons() {
        assertEquals(guich.getNbjetons(),5);
        assertEquals(guich2.getNbjetons(),3);
    }

    @Test
    void testGetNumeroSema() {
        FabriqueNumero.getInstance().reset();
        Guichet guichet = new Guichet("Guichet");
        Guichet guichet2 = new Guichet("Guichet2");
        assertEquals(guichet.getNumeroSema(),1);
        assertEquals(guichet2.getNumeroSema(),2);
    }

    @Test
    void toC() {
        FabriqueNumero.getInstance().reset();
        Guichet guichet = new Guichet("Guichet");
        ActiviteRestreinte ar = new ActiviteRestreinte("Zoo",4,2);
        guichet.ajouterSuccesseur(ar);
        String code = "\tP(ids,SEM_GUICHET);\n" +
                "\ttransfert(GUICHET,ZOO);\n"+
                "\tdelai(4,2);\n" +
                "\tV(ids,SEM_GUICHET);\n";
        assertEquals(code,guichet.toC());
    }
}