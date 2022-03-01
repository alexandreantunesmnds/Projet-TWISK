package twisk.monde.tests;

import org.junit.jupiter.api.Test;
import twisk.monde.Activite;

import static org.junit.jupiter.api.Assertions.*;

class ActiviteTest extends EtapeTest{
    @Test
    void estUneActivite() {
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
    void toC(){
        String actToC = "delai(6,3);\n";
        assertEquals(act.toC(),actToC);
    }
}