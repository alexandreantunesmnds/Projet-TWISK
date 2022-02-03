package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Guichet;

import static org.junit.jupiter.api.Assertions.*;

class ActiviteTest extends EtapeTest{
    @Test
    void estUneActivite() {
        assertTrue(act.estUneActivite());
    }

    @Test
    void estUnGuichet() {
        assertFalse(act.estUnGuichet());
    }
}