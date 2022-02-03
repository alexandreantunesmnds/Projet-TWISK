package twisk.monde.tests;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import twisk.monde.Activite;
import twisk.monde.Guichet;
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
}