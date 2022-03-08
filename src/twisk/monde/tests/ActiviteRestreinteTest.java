package twisk.monde.tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActiviteRestreinteTest extends EtapeTest {

    @Override
    public void estUneActivite() {
        assertTrue(actRestreinte.estUneActivite());
    }

    @Override
    void estUnGuichet() {
        assertFalse(actRestreinte.estUneActivite());
    }
}