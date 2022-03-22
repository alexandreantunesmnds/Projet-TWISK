package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.*;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireSuccesseursTest {

    private GestionnaireSuccesseurs gestS;
    private GestionnaireSuccesseurs gestEmpty;

    @BeforeEach
    void setUp() {
        this.gestS = new GestionnaireSuccesseurs();
        this.gestEmpty =new GestionnaireSuccesseurs();
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
        Etape act = new Activite("Toboggan");
        Etape guich = new Guichet("Caisse");
        this.gestS.ajouter(act,guich);
        Iterator<Etape> it, it2;
        it = this.gestEmpty.iterator();
        it2 = this.gestS.iterator();
        assertFalse(it.hasNext());
        assertEquals(it2.next().getNom(),"TOBOGGAN");
        assertEquals(it2.next().getNom(),"CAISSE");
        assertFalse(it2.hasNext());
    }
}