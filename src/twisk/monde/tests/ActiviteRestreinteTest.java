package twisk.monde.tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActiviteRestreinteTest extends EtapeTest {

    @Test
    void toC() {
        actRestreinte.setNumSemaphore(1);
        String code = "delai(4,2);\n" +
                "V(ids,1);\n";
        assertEquals(code,actRestreinte.toC());
    }

    @Override
    public void estUneActivite() {
        assertTrue(actRestreinte.estUneActivite());
    }

    @Override
    void estUnGuichet() {
        assertFalse(actRestreinte.estUneActivite());
    }
}