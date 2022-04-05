package twisk.simulation.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.simulation.Client;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    Client cl;
    Etape etape2;

    @BeforeEach
    void setUp() {
        cl = new Client(1234);
        cl.setEtape(new Activite("Activite 1"));
        cl.setRang(5);

        etape2 = new Activite("Activite 2");
    }

    @Test
    void allerA() {
        cl.allerA(etape2,1);

        assertEquals(etape2,cl.getEtape());
        assertEquals(1,cl.getRang());
        assertEquals(1234,cl.getNumeroClient());
    }
}