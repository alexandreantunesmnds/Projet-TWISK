package twisk.monde.tests;

import org.junit.jupiter.api.Test;
import twisk.monde.Activite;

import static org.junit.jupiter.api.Assertions.*;

public class ActiviteTest extends EtapeTest{
    @Test
    public void estUneActivite() {
        assertTrue(act.estUneActivite());
        assertTrue(actRestreinte.estUneActivite());
    }

    @Test
    void estUnGuichet() {
        assertFalse(act.estUnGuichet());
        assertFalse(actRestreinte.estUnGuichet());
    }

    @Test
    void getTemps() {
        assertEquals(act.getTemps(),6);
        assertEquals(actRestreinte.getTemps(),4);
    }

    @Test
    void getEcartTemps() {
        assertEquals(act.getEcartTemps(),3);
        assertEquals(actRestreinte.getEcartTemps(),2);
    }

    @Test
    void toC() {
        Activite debut = new Activite("Début");
        Activite act1 = new Activite("Activite");
        Activite act2 = new Activite("Bifurcation");
        Activite act3 = new Activite("Fin");
        debut.ajouterSuccesseur(act1,act2);
        act1.ajouterSuccesseur(act3);
        act2.ajouterSuccesseur(act3);

        StringBuilder code = new StringBuilder("");
        code.append("\tdelai(5,2);\n" +
                "\tint nb = (int)((rand()/(float)RAND_MAX*2);\n" +
                "\tswitch(nb):\n" +
                "\tcase 0:{ //vers ACTIVITE\n" +
                "\ttransfert(DEBUT,ACTIVITE);\n" +
                "\t\tdelai(5,2);\n" +
                "\ttransfert(ACTIVITE,FIN);\n" +
                "\t\tdelai(5,2);\n" +
                "\tbreak;\n" +
                "\t}\n" +
                "\tcase 1:{ //vers BIFURCATION\n" +
                "\ttransfert(DEBUT,BIFURCATION);\n" +
                "\t\tdelai(5,2);\n" +
                "\ttransfert(BIFURCATION,FIN);\n" +
                "\t\tdelai(5,2);\n" +
                "\tbreak;\n" +
                "\t}\n");
        assertEquals(code.toString(),debut.toC());
    }
}