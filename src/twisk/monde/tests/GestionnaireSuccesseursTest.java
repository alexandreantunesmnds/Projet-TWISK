package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.*;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireSuccesseursTest {

    private GestionnaireSuccesseurs gestS;

    @BeforeEach
    void setUp() {
        this.gestS = new GestionnaireSuccesseurs();
    }

    @Test
    void ajouter() {
        Etape E1 = new Activite("Etape1");
        Etape G1 = new Guichet("Guichet1");

        this.gestS.ajouter(E1,G1);

        assertEquals(2,this.gestS.nbEtapes());
    }

    @Test
    void iterator() {
    }
}