package twisk.simulation.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.simulation.Client;
import twisk.simulation.GestionnaireClients;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireClientsTest {
    GestionnaireClients gc;

    @BeforeEach
    void setUp() {
        gc = new GestionnaireClients();
    }

    @Test
    void setClients() {
        gc.setClients(1234,4567,7891,1478);

        assertEquals(4,this.gc.getNbClient());
    }

    @Test
    void allerA() {
        Etape test = new Activite("manger");
        gc.setClients(1234,4567,7891,1478);
        gc.allerA(4567,test,3);

        Client cl = gc.getClients(4567);
        assertEquals(test,cl.getEtape());
        assertEquals(3,cl.getRang());
    }

    @Test
    void nettoyer() {
        gc.setClients(1234,4567,7891,1478);
        assertEquals(4,this.gc.getNbClient());

        gc.nettoyer();

        assertEquals(0,this.gc.getNbClient());
    }
}