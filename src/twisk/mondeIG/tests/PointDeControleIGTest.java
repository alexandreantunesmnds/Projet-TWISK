package twisk.mondeIG.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.mondeIG.PointDeControleIG;

import static org.junit.jupiter.api.Assertions.*;

class PointDeControleIGTest {
    PointDeControleIG pdc;

    @BeforeEach
    void setUp() {
        pdc = new PointDeControleIG(25, 5, "1", null);

    }

    @Test
    void getId() {
        assertEquals("1",pdc.getId());
    }

    @Test
    void getEtapeLiee() {
        assertNull(pdc.getEtapeLiee());
    }

    @Test
    void getPosX() {
        assertEquals(25,pdc.getPosX());
    }

    @Test
    void getPosY() {
        assertEquals(5,pdc.getPosY());
    }
}