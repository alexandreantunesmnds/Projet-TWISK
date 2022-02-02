package twisk.monde;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActiviteTest {
    Activite act ;
    Guichet guich; //guichet
    @BeforeEach
    void setUp() {
        this.act = new Activite("Piscine");
        this.guich = new Guichet("Caisse");
    }

    @Test
    void estUneActivite() {
        assertTrue(act.estUneActivite());
        assertFalse(guich.estUneActivite());
    }
}