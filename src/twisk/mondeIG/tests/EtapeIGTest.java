package twisk.mondeIG.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.mondeIG.ActiviteIG;
import twisk.mondeIG.EtapeIG;
import twisk.outils.FabriqueIdentifiant;

import static org.junit.jupiter.api.Assertions.*;

class EtapeIGTest {

    private EtapeIG activite1;
    private EtapeIG activite2;
    private FabriqueIdentifiant fi;

    @BeforeEach
    void setUp() {
        this.fi = FabriqueIdentifiant.getInstance();
        this.fi.reset();
        this.activite1 = new ActiviteIG("Act1", fi.getIdentifiantEtape(),50,30);
        this.activite2 = new ActiviteIG("Act2", fi.getIdentifiantEtape(),50,30);
        System.out.println(activite1.toString());
    }

    @Test
    void getNom() {
        assertEquals("Act1",this.activite1.getNom());
        assertEquals("Act2",this.activite2.getNom());
    }

    @Test
    void getIdentifiant() {
        assertEquals("1",this.activite1.getIdentifiant());
        assertEquals("2",this.activite2.getIdentifiant());
    }
}