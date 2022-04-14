package twisk.outils.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.outils.TailleComposants;

import static org.junit.jupiter.api.Assertions.*;

class TailleComposantsTest {

    private TailleComposants constantes;

    @BeforeEach
    void setUp() {
        constantes = TailleComposants.getInstance();
    }

    @Test
    void getLargeurFenetre() {
        assertEquals(1000,constantes.getLargeurFenetre());
    }

    @Test
    void getHauteurFenetre() {
        assertEquals(700,constantes.getHauteurFenetre());
    }

    @Test
    void getLargeurEtape() {
        assertEquals(180,constantes.getLargeurEtape());
    }

    @Test
    void getHauteurEtape() {
        assertEquals(90,constantes.getHauteurActivite());
    }

    @Test
    void getLargeurZoneClient() {
        assertEquals(70,constantes.getLargeurZoneClient());
    }

    @Test
    void getHauteurZoneClient() {
        assertEquals(60,constantes.getHauteurZoneClient());
    }

    @Test
    void getLargeurBoutonAjouter() {
        assertEquals(40,constantes.getLargeurBoutonAjouter());
    }
}