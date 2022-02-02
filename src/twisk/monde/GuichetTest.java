package twisk.monde;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuichetTest {
    Activite act ;
    Guichet guich;
    @BeforeEach
    void setUp() {
        this.act = new Activite("Piscine");
        this.guich = new Guichet("Caisse");
    }

    @Test
    void testEstUnGuichet() {
        assertFalse(act.estUnGuichet());
        assertTrue(guich.estUnGuichet());
    }
}