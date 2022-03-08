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
        String sasToC = "entrer(SasEntree);\n" +
                "transfert(SasEntree,activite);\n" +
                "delai(5,3);\n";
        sasEntree.ajouterSuccesseur(act);

        assertEquals(sasToC,sasEntree.toC());
    }
}