package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
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
        Activite bacASable = new Activite("Bac à sable");
        Activite toboggan = new Activite("Toboggan");
        Activite souvenirs = new Activite("Souvenirs");
        guichet.ajouterSuccesseur(ar);
        ar.ajouterSuccesseur(bacASable,toboggan);
        bacASable.ajouterSuccesseur(souvenirs);
        toboggan.ajouterSuccesseur(souvenirs);
        String code = "\tP(ids,SEM_GUICHET);\n" +
                "\ttransfert(GUICHET,ZOO);\n"+
                "\tdelai(4,2);\n" +
                "\tV(ids,SEM_GUICHET);\n"+
                "\tint nb = (int)((rand()/(float)RAND_MAX*1);\n" +
                "\tswitch(nb):\n" +
                "\tcase 0:{ //vers BAC_À_SABLE\n" +
                "\ttransfert(ZOO,BAC_À_SABLE);\n" +
                "\tdelai(5,2);\n" +
                "\ttransfert(BAC_À_SABLE,SOUVENIRS);\n" +
                "\tdelai(5,2);\n" +
                "\tbreak;\n" +
                "\t}\n" +
                "\tcase 1:{ //vers TOBOGGAN\n" +
                "\ttransfert(ZOO,TOBOGGAN);\n" +
                "\tdelai(5,2);\n" +
                "\ttransfert(TOBOGGAN,SOUVENIRS);\n" +
                "\tdelai(5,2);\n" +
                "\tbreak;\n" +
                "\t}\n";
        assertEquals(code,guichet.toC());
    }
}