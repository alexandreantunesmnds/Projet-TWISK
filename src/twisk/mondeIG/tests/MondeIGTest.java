package twisk.mondeIG.tests;

import org.junit.jupiter.api.Test;
import twisk.exceptions.MondeException;
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

    @Test
    void verifierMondeIG() throws MondeException {
        //1er monde : erreur, deux guichets se suivent
        //System.out.println("Monde 1");
        MondeIG monde1 = new MondeIG();
        EtapeIG guichet1 = new GuichetIG("guichet1","1",24,24,2);
        EtapeIG guichet2 = new GuichetIG("guichet2","2",24,24,2);
        EtapeIG act1 = new ActiviteIG("act1","3",24,24);

        guichet1.setEntree();
        act1.setSortie();
        guichet1.ajouterSuccesseur(guichet2);
        guichet2.ajouterSuccesseur(act1);

        monde1.ajouter(guichet1,act1,guichet2);
        //System.out.println(monde1.toString());

        assertThrows(MondeException.class,()->monde1.estValide());

        //2ème monde : erreur, un guichet possèdent deux successeurs
        //System.out.println("Monde 2");
        MondeIG monde2 = new MondeIG();
        EtapeIG guichet3 = new GuichetIG("guichet3","1",24,24,2);
        EtapeIG act2 = new ActiviteIG("act2","2",24,24);
        EtapeIG act3 = new ActiviteIG("act3","3",24,24);

        guichet3.setEntree();
        act2.setSortie();
        act3.setSortie();
        guichet3.ajouterSuccesseur(act2,act3);

        monde2.ajouter(act2,act3,guichet3);

        assertThrows(MondeException.class,()->monde2.estValide());

        //3ème monde : erreur, une activité ne possède pas de successeur
        //System.out.println("Monde 3");
        MondeIG monde3 = new MondeIG();
        EtapeIG act4 = new ActiviteIG("act4","2",24,24);
        EtapeIG act5 = new ActiviteIG("act5","3",24,24);
        EtapeIG act6 = new ActiviteIG("act6","4",24,24);
        EtapeIG act7 = new ActiviteIG("act7","1",24,24);

        act7.setEntree();
        act6.setSortie();
        act7.ajouterSuccesseur(act4,act5);
        act4.ajouterSuccesseur(act6);

        monde3.ajouter(act7,act4,act5,act6);
        assertThrows(MondeException.class,()->monde3.estValide());

        //4ème monde : erreur, pas de sortie
        //System.out.println("Monde 4");
        MondeIG monde4 = new MondeIG();
        EtapeIG act8 = new ActiviteIG("act8","1",24,24);
        EtapeIG act9 = new ActiviteIG("act9","2",24,24);

        act8.setEntree();
        act8.ajouterSuccesseur(act9);
        monde4.ajouter(act8,act9);
        assertThrows(MondeException.class,()->monde4.estValide());
    }
}