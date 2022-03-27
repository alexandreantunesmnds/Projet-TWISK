package twisk.monde.tests;

import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.SasEntree;
import twisk.monde.tests.ActiviteTest;

import static org.junit.jupiter.api.Assertions.*;

class SasEntreeTest extends ActiviteTest {
    @Test
    void toC() {
        Activite sasEntree = new SasEntree();
        Activite act = new Activite("activite",5,3);
        Activite act2 = new Activite("Bifurcation",1,2);
        String sasToC = "\tentrer(ENTREE);\n" +
                "\tdelai(6,3);\n"+
                "\tcase 0:{ //vers ACTIVITE\n"+
                "\ttransfert(ENTREE,ACTIVITE);\n" +
                "\t\tdelai(5,3);\n"+
                "\tbreak;\n" +
                "\t}\n"+
                "\tcase 1:{ //vers BIFURCATION\n"+
                "\ttransfert(ENTREE,BIFURCATION);\n" +
                "\t\tdelai(1,2);\n"+
                "\tbreak;\n"+
                "\t}\n";
        sasEntree.ajouterSuccesseur(act,act2);

        assertEquals(sasToC,sasEntree.toC());
    }
}