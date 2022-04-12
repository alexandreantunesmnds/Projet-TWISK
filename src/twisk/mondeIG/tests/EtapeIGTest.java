package twisk.mondeIG.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.mondeIG.ActiviteIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.GuichetIG;
import twisk.outils.FabriqueIdentifiant;

import static org.junit.jupiter.api.Assertions.*;

class EtapeIGTest {

    private EtapeIG activite1;
    private EtapeIG activite2;
    private EtapeIG guichet1;
    private EtapeIG guichet2;
    private FabriqueIdentifiant fi;

    @BeforeEach
    void setUp() {
        this.fi = FabriqueIdentifiant.getInstance();
        this.fi.reset();
        this.activite1 = new ActiviteIG("Act1", fi.getIdentifiantEtape(),50,30);
        this.activite2 = new ActiviteIG("Act2", fi.getIdentifiantEtape(),50,30);
        this.guichet1 = new GuichetIG("Guichet1",fi.getIdentifiantEtape(),50,30,2);
        this.guichet2 = new GuichetIG("Guichet2",fi.getIdentifiantEtape(),50,30,2);
    }

    @Test
    void getNom() {
        assertEquals("Act1",this.activite1.getNom());
        assertEquals("Act2",this.activite2.getNom());
        assertEquals("Guichet1",this.guichet1.getNom());
        assertEquals("Guichet2",this.guichet2.getNom());
    }

    @Test
    void getIdentifiant() {
        assertEquals("1",this.activite1.getIdentifiant());
        assertEquals("2",this.activite2.getIdentifiant());
        assertEquals("3",this.guichet1.getIdentifiant());
        assertEquals("4",this.guichet2.getIdentifiant());
    }
}