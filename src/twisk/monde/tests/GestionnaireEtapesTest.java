package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.GestionnaireEtapes;
import twisk.monde.Guichet;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireEtapesTest {

    GestionnaireEtapes gestE;

    @BeforeEach
    void setUp() {
        this.gestE = new GestionnaireEtapes();
    }

    @Test
    void ajouter() {
        Etape E1 = new Activite("Activite1");
        Etape G1 = new Guichet("Guichet1");

        this.gestE.ajouter(E1);
        this.gestE.ajouter(G1);

        assertEquals(2,this.gestE.nbEtapes());
    }

    @Test
    void iterator() {
    }
}