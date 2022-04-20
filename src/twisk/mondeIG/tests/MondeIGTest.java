package twisk.mondeIG.tests;

import org.junit.jupiter.api.Test;
import twisk.exceptions.TwiskException;
import twisk.mondeIG.*;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class MondeIGTest {
    private MondeIG mondeTest;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        this.mondeTest = new MondeIG();
    }

    @org.junit.jupiter.api.Test
    void ajouter() {
        assertEquals(1,this.mondeTest.nbEtape());

        this.mondeTest.ajouter("Activité");
        assertEquals(2,this.mondeTest.nbEtape());
    }

    @org.junit.jupiter.api.Test
    void iterator() {
        //J'ajoute une activité afin d'avoir un test plus convaincant
        this.mondeTest.ajouter("Activité");

        Iterator<EtapeIG> it = this.mondeTest.iterator();

        assertTrue(it.hasNext()); //L'activité crée dans le constructeur
        it.next();
        assertTrue(it.hasNext()); //L'activité que j'ai créé pour le test
        it.next();
        assertFalse(it.hasNext());
    }

    @Test
    void verifierArc() throws TwiskException {
        EtapeIG etape1 = new ActiviteIG("etape1","1",24,24);
        EtapeIG etape2 = new ActiviteIG("etape2","2",24,24);
        PointDeControleIG pdc1 = new PointDeControleIG(0,0,"pdc1",etape1);
        PointDeControleIG pdc2 = new PointDeControleIG(0,0,"pdc2",etape1);
        PointDeControleIG pdc3 = new PointDeControleIG(0,0,"pdc3",etape1);

        assertThrows(TwiskException.class,()->this.mondeTest.verifierArc(pdc1,pdc1)); //même points

        PointDeControleIG finalPdc = pdc2;
        assertThrows(TwiskException.class,()->this.mondeTest.verifierArc(pdc1, finalPdc)); //même étape

        pdc2 = new PointDeControleIG(0,0,"pdc2",etape2);

        assertTrue(this.mondeTest.verifierArc(pdc1,pdc2)); //étape différente
        this.mondeTest.ajouterArc(pdc1,pdc2);

        assertThrows(TwiskException.class,()->this.mondeTest.verifierArc(pdc1,pdc3)); //pdc déjà saisi

    }
}