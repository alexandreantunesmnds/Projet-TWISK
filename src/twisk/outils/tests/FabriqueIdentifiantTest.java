package twisk.outils.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.outils.FabriqueIdentifiant;

import static org.junit.jupiter.api.Assertions.*;

class FabriqueIdentifiantTest {

    private FabriqueIdentifiant fi;

    @BeforeEach
    void setUp() {
        this.fi = FabriqueIdentifiant.getInstance();
    }

    @Test
    void getIdentifiantEtape() {
        assertEquals("1", fi.getIdentifiantEtape());
        assertEquals("2", fi.getIdentifiantEtape());
    }
}