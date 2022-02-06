package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.GestionnaireEtapes;
import twisk.monde.Guichet;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireEtapesTest {

    private GestionnaireEtapes gestE;
    private GestionnaireEtapes gestEmpty;

    @BeforeEach
    void setUp() {
        this.gestE = new GestionnaireEtapes();
        this.gestEmpty = new GestionnaireEtapes();
    }

    @Test
    void ajouter() {
        Etape E1 = new Activite("Activite1");
        Etape G1 = new Guichet("Guichet1");
        this.gestE.ajouter(E1,G1);
        assertEquals(2,this.gestE.nbEtapes());
    }

    @Test
    void iterator() {
        Etape act = new Activite("Toboggan");
        Etape guich = new Guichet("Caisse");
        this.gestE.ajouter(act,guich);
        Iterator<Etape>  it, it2;
        it = this.gestEmpty.iterator();
        it2 = this.gestE.iterator();
        assertFalse(it.hasNext());
        assertEquals(it2.next().getNom(),"Toboggan");
        assertEquals(it2.next().getNom(),"Caisse");
        assertFalse(it2.hasNext());

    }
}