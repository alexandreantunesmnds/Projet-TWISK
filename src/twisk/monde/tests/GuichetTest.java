package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Guichet;

import static org.junit.jupiter.api.Assertions.*;

class GuichetTest extends EtapeTest{
    @Test
    void estUnGuichet() {
        assertTrue(guich.estUnGuichet());
    }

    @Test
    void estUneActivite() {
        assertFalse(guich.estUneActivite());
    }
}