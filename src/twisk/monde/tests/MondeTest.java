package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Guichet;
import twisk.monde.Monde;

import static org.junit.jupiter.api.Assertions.*;

public class MondeTest {
    private Monde world;

    @BeforeEach
    void setUp() {
        this.world = new Monde();
    }

    @Test
    void aCommeEntree() {
        this.world.aCommeEntree(new Activite("A1"), new Activite("A2"), new Guichet("G1"));

        assertEquals(3, this.world.nbEntree());
    }

    @Test
    void aCommeSortie() {
        //assertEquals(0,this.world.nbSortie());
    }
}